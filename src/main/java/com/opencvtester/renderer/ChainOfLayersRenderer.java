package com.opencvtester.renderer;

import java.util.Stack;

import com.opencvtester.data.Command;
import com.opencvtester.data.CompositeFilter;
import com.opencvtester.data.Layer;
import com.opencvtester.renderer.interfaces.FrameInterface;

public class ChainOfLayersRenderer extends Renderer {

	private FrameInterface background;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public ChainOfLayersRenderer(CompositeFilter compositeFilters, FrameInterface background) {
		super(compositeFilters);
		this.background=background;
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public Command getLastFilter(){
		return chainOfFilters.get(chainOfFilters.size() - 1);
	}   
	
	public int getNumberOfFiltersPlusOpacity() {
		return chainOfFilters.size();
	}
	
	/*
	 * FEATURES
	 */
	public void execute(Stack<Command> chainOfLayers) {
		dealFrames(chainOfLayers);	
		dealFramesInLayers();
		dealBackground();	
		render();	
	}

	public void dealBackground(){
		int numberOfLayers = chainOfFilters.size();
		
		if (numberOfLayers>0) {
			((Layer)chainOfFilters.get(0)).setBackGround(background);
			for (int i = 1; i < numberOfLayers; i++) {
				((Layer)chainOfFilters.get(i)).setBackGround(((Layer)chainOfFilters.get(i - 1)).getFrameOut());
			}
		}	
	}   
	
	public void dealFramesInLayers(){
		for (int i = 0; i < chainOfFilters.size(); i++) {
			 ((Layer)chainOfFilters.get(i)).dealFrames();	
		}
	}
}
