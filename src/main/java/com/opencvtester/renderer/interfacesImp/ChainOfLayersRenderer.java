package com.opencvtester.renderer.interfacesImp;

import java.util.List;

import com.opencvtester.controller.interfaces.DataController;
import com.opencvtester.controller.interfaces.Renderer;
import com.opencvtester.renderer.Frame;
import com.opencvtester.renderer.Layer;
import com.opencvtester.renderer.interfaces.ChainRenderer;
import com.opencvtester.renderer.interfaces.FrameInterface;

public class ChainOfLayersRenderer extends ChainRenderer
{
	protected FrameInterface background;
	private DataController data;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public ChainOfLayersRenderer (FrameInterface frameIn, List<? extends Renderer>chainOfRenderer, DataController data) {
		super(chainOfRenderer);
		this.data=data;
		this.background = new Frame();
		frameIn.copyTo(this.frameIn);
		this.frameIn.copyTo(this.frameOut);
		this.background.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
	}
	
	public ChainOfLayersRenderer (String fileName, List<? extends Renderer>chainOfRenderer) {
		super(chainOfRenderer);
		this.background = new Frame();
		
		openImage(fileName);
		
		this.background.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
	}

	
	public void openImage(String fileName) {
		setFrameIn(fileName);
		frameIn.copyTo(frameOut);
		background.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
		
		if(chainOfRenderer.size()>0) {
			data.checkAndActivateLayer(0);
		}
		
		deleteAllIntermediateFrames();

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
		
	
	public void setFrameIn(String fileName) {
		try {
			frameIn.readFromFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
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
}