package com.opencvtester.renderer;

import java.util.Stack;

import com.opencvtester.renderer.entity.Layer;
import com.opencvtester.renderer.interfaces.FrameInterface;
import com.opencvtester.renderer.interfaces.IOFrame;

public class ChainOfLayersRenderer extends Renderer {

	private FrameInterface background;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public ChainOfLayersRenderer(IOFrame mainFilter, FrameInterface background) {
		super(mainFilter);
		this.background=background;
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public IOFrame getLastFilter(){
		return chainOfIOFrame.get(chainOfIOFrame.size() - 1);
	}   
	
	public int getNumberOfFiltersPlusOpacity() {
		return chainOfIOFrame.size();
	}
	
	/*
	 * FEATURES
	 */
	public void execute(Stack<IOFrame> chainOfLayers) {
		dealFrames(chainOfLayers);	
		dealFramesInLayers();
		dealBackground();	
		render();	
	}

	public void dealBackground(){
		int numberOfLayers = chainOfIOFrame.size();
		
		if (numberOfLayers>0) {
			((Layer)chainOfIOFrame.get(0)).setBackground(background);
			for (int i = 1; i < numberOfLayers; i++) {
				((Layer)chainOfIOFrame.get(i)).setBackground(((Layer)chainOfIOFrame.get(i - 1)).getFrameOut());
			}
		}	
	}   
	
	public void dealFramesInLayers(){
		for (int i = 0; i < chainOfIOFrame.size(); i++) {
			 ((Layer)chainOfIOFrame.get(i)).dealFrames();	
		}
	}
}
