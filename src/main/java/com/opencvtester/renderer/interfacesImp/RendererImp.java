package com.opencvtester.renderer.interfacesImp;

import java.util.LinkedHashMap;

import com.opencvtester.controller.interfaces.RendererController;
import com.opencvtester.data.ChainOfCommands;
import com.opencvtester.data.CompositeFilter;
import com.opencvtester.data.Layer;
import com.opencvtester.filterController.ControlledFilter;
import com.opencvtester.renderer.ChainOfLayersRenderer;
import com.opencvtester.renderer.Frame;
import com.opencvtester.renderer.LayerFactory;
import com.opencvtester.renderer.interfaces.FrameInterface;

public class RendererImp extends CompositeFilter implements RendererController
{
	protected FrameInterface background;
	protected LayerFactory layersFactory;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public RendererImp (FrameInterface frameIn) {
		super();
		this.background = new Frame();
		frameIn.copyTo(this.frameIn);
		this.frameIn.copyTo(this.frameOut);
		this.background.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
		
		indexType="layer";
		chainOfFilters = new ChainOfCommands (this.indexType);	
		renderer=new ChainOfLayersRenderer(this, background);
	}
	
	public RendererImp (String fileName) {
		super();
		this.background = new Frame();
		
		openImage(fileName);
		
		this.background.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
		
		indexType="layer";
		chainOfFilters = new ChainOfCommands (this.indexType);	
		renderer=new ChainOfLayersRenderer(this, background);
	}
	
	/*
	 * RendererController implementation
	 */
	
	
	
	public Layer getLayer(int layerIndex) {
		return (Layer)chainOfFilters.getCommand(layerIndex);
	}
	
	public void openImage(String fileName) {
		setFrameIn(fileName);
		frameIn.copyTo(frameOut);
		background.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
		if (renderer!=null && chainOfFilters!=null) {
			if(getNumberOfLayers()>0) {
				checkAndActivateLayer(getLayer(0));
			}
			deleteAllIntermediateFrames();
		}
	}
	
	public void execute() {
		renderer.execute(chainOfFilters.getChain());
	}

	public void clearAll() {
	
		for (int i=getNumberOfLayers()-1;i>=0;i--) {
			Layer layer= (Layer) chainOfFilters.getCommand(i);
			delete(layer);
			
			if (layer.layerIndex()>0) {
				checkAndActivateLayer(this.getLayer(layer.layerIndex()-1));
			}
		}		
		execute();	
	}

	public Layer getLastLayer(){
		return (Layer)chainOfFilters.getCommand(chainOfFilters.getSize() - 1);
	}   
	
	public int getNumberOfLayers() {
		if (chainOfFilters!=null) {
			return chainOfFilters.getSize();
		}else {
			return 0;
		}
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
		for (int i=0;i<getNumberOfLayers();i++) {
			getLayer(i).deleteAllIntermediateFrames();
		}
	}	
	
		
	
}