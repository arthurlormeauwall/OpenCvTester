package com.opencvtester.renderer.interfacesImp;

import java.util.Stack;

import com.opencvtester.controller.interfaces.DataController;
import com.opencvtester.controller.interfaces.RendererController;
import com.opencvtester.data.LayerData;
import com.opencvtester.renderer.ChainOfLayersRenderer;
import com.opencvtester.renderer.Frame;
import com.opencvtester.renderer.Renderer;
import com.opencvtester.renderer.entity.Layer;
import com.opencvtester.renderer.entity.LayerFactory;
import com.opencvtester.renderer.interfaces.FrameInterface;
import com.opencvtester.renderer.interfaces.IOFrame;

public class RendererImp extends IOFrame implements RendererController
{
	protected FrameInterface background;
	protected LayerFactory layersFactory;
	protected DataController data;
	protected ChainOfLayersRenderer renderer;
	protected Stack<IOFrame> chainOfLayers;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public RendererImp (FrameInterface frameIn, DataController data) {
		super();
		this.data=data;
		this.background = new Frame();
		frameIn.copyTo(this.frameIn);
		this.frameIn.copyTo(this.frameOut);
		this.background.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
		
		renderer=new ChainOfLayersRenderer(this, background);
	}
	
	public RendererImp (String fileName) {
		super();
		this.background = new Frame();
		
		openImage(fileName);
		
		this.background.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
		
		renderer=new ChainOfLayersRenderer(this, background);
	}
	
	/*
	 * RendererController implementation
	 */
	
	public void openImage(String fileName) {
		setFrameIn(fileName);
		frameIn.copyTo(frameOut);
		background.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
		
		if(data.getNumberOfLayers()>0) {
			data.checkAndActivateLayer(0);
		}
		
		deleteAllIntermediateFrames();

	}
	
	public void render() {
		renderer.execute(chainOfLayers);
	}

	public Layer getLastLayer(){
		return (Layer)chainOfLayers.get(chainOfLayers.size()- 1);
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
		renderer.deleteAllIntermediateFrames();
		for (int i=0;i<data.getNumberOfLayers();i++) {
			((Layer)chainOfLayers.get(i)).deleteAllIntermediateFrames();
		}
	}
}