package com.opencvtester.renderer.interfacesImp;

import java.util.Stack;

import com.opencvtester.controller.interfaces.DataIndexProvider;
import com.opencvtester.controller.interfaces.Renderer;
import com.opencvtester.renderer.Layer;
import com.opencvtester.renderer.interfaces.ChainRenderer;
import com.opencvtester.renderer.interfaces.FrameInterface;

public class ChainOfLayersRenderer extends ChainRenderer
{

	public ChainOfLayersRenderer (String fileName, Stack<? extends Renderer>chainOfRenderer) {
		super(chainOfRenderer);
		
		openImage(fileName);
		
		this.background.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
	}
	
	public void render() {
		dealFrames();	
		dealFramesInLayers();
		dealBackground();	
		execute();	
	}
	
	public void dealBackground(){
		int numberOfLayers = chainOfRenderer.size();
		
		if (numberOfLayers>0) {
			((Layer)chainOfRenderer.get(0)).setBackground(background);
			for (int i = 1; i < numberOfLayers; i++) {
				((Layer)chainOfRenderer.get(i)).setBackground(((Layer)chainOfRenderer.get(i - 1)).getFrameOut());
			}
		}	
	}   
	
	public void dealFramesInLayers(){
		for (int i = 0; i < chainOfRenderer.size(); i++) {
			 ((Layer)chainOfRenderer.get(i)).dealFrames();	
		}
	}

	public Layer getLast(){
		return (Layer)chainOfRenderer.get(chainOfRenderer.size()- 1);
	}   
		

	
	public void deleteAllIntermediateFrames() {
		for (int i=0;i<chainOfRenderer.size();i++) {
			((Layer)chainOfRenderer.get(i)).deleteAllIntermediateFrames();
		}
	}

	@Override
	public int getNumberOfFiltersPlusOpacity() {
		return chainOfRenderer.size();
	}

	@Override
	public DataIndexProvider getData() {
		return null;
	}

}