package com.opencvtester.renderingEngine.renderer;

import java.util.Stack;

import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.frame.FrameInterface;
import com.opencvtester.renderingEngine.CompositeFilter;
import com.opencvtester.renderingEngine.Layer;

public class ChainOfLayersRenderer extends Renderer {

	private FrameInterface background;
	
	public ChainOfLayersRenderer(CompositeFilter compositeFilters, FrameInterface background) {
		super(compositeFilters);
		this.background=background;
	}

	public void execute(Stack<Command> chainOfFilters) {
		dealFrames(chainOfFilters);	
		dealFramesInLayers();
		dealBackground();	
		render();	
	}

	public void dealBackground(){
		int numberOfMaskedLayers = chainOfFilters.size();
		
		if (numberOfMaskedLayers>0) {
			((Layer)chainOfFilters.get(0)).setBackGround(background);
			for (int i = 1; i < numberOfMaskedLayers; i++) {
				((Layer)chainOfFilters.get(i)).setBackGround(((Layer)chainOfFilters.get(i - 1)).getDest());
			}
		}	
	}   
	
	public void dealFramesInLayers(){
		for (int i = 0; i < chainOfFilters.size(); i++) {
			 ((Layer)chainOfFilters.get(i)).dealFrames();	
		}
	}
	
	public Command getLastFilter(){
		return chainOfFilters.get(chainOfFilters.size() - 1);
	}   
	
	public int getNumberOfFiltersPlusOpacity() {
		return chainOfFilters.size();
	}
	
	
}
