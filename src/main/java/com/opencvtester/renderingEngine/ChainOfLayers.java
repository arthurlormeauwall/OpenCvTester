package com.opencvtester.renderingEngine;

import java.util.LinkedHashMap;

import com.opencvtester.baseClasses.ChainOfCommands;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.baseClasses.frame.Frame;
import com.opencvtester.baseClasses.frame.FrameInterface;
import com.opencvtester.dataAccess.LayerFactory;
import com.opencvtester.renderingEngine.renderer.ChainOfLayersRenderer;

public class ChainOfLayers extends CompositeFilter
{
	protected FrameInterface background;
	protected LayerFactory layersFactory;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public ChainOfLayers (FrameInterface frameIn) {
		super();
		this.background = new Frame();
		frameIn.copyTo(this.frameIn);
		this.frameIn.copyTo(this.frameOut);
		this.background.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
		
		indexType="layer";
		chainOfFilters = new ChainOfCommands (this.indexType);	
		renderer=new ChainOfLayersRenderer(this, background);
	}
	
	public ChainOfLayers (String fileName) {
		super();
		this.background = new Frame();
		
		openImage(fileName);
		
		this.background.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
		
		indexType="layer";
		chainOfFilters = new ChainOfCommands (this.indexType);	
		renderer=new ChainOfLayersRenderer(this, background);
	}
	
	/*
	 * GETTERS AND SETTERS
	 */	
	public Layer getLastLayer(){
		return (Layer)chainOfFilters.getCommand(chainOfFilters.getSize() - 1);
	}   
	
	public Layer getLayer(int layerIndex) {
		return (Layer)chainOfFilters.getCommand(layerIndex);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
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
			
	
	
	public void deleteAllIntermediateFrames() {
		renderer.deleteAllIntermediateFrames();
		for (int i=0;i<getNumberOfLayers();i++) {
			getLayer(i).deleteAllIntermediateFrames();
		}
	}

	/*
	 * FEATURES
	 */	
	
	public void  addLayer(Layer newLayer) {
		add(newLayer);
		
		checkAndActivateLayer(newLayer);
		execute();
	}

	public void deleteLayer(Layer layer){
		delete(layer);
		
		if (layer.layerIndex()>0) {
			checkAndActivateLayer(this.getLayer(layer.layerIndex()-1));
		}
		
		execute();		
	}  
	
	public void addFilter(Filter filter) {
		if (areIndexLegal(filter.layerIndex(), filter.filterIndex())) {
			((Layer)chainOfFilters.getCommand(filter.layerIndex())).addFilter(filter);
			
			checkAndActivateFilter(filter);
			execute();	
		}
	}
	
	public void deleteFilter(Filter filter){
		((Layer)chainOfFilters.getCommand(filter.layerIndex())).deleteFilter(filter);
		
		if (this.getLayer(filter.layerIndex()).getNumberOfFilters()==0 || filter.filterIndex()==0) {
			checkAndActivateLayer(getLayer(filter.layerIndex()));
		}
		else {
			checkAndActivateFilter(getLayer(filter.layerIndex()).getFilter(filter.filterIndex()-1));
		}

		execute();
	}  

	public void setOpacity(Filter opacityFilter, Float opacity){
		if (getNumberOfLayers() >opacityFilter.layerIndex()) {
			((Layer)chainOfFilters.getCommand(opacityFilter.layerIndex())).setOpacity(opacity);
			
			checkAndActivateLayer(getLayer(opacityFilter.layerIndex()));
			execute();
		}
	}  
	
	public void setAllFilterParameters(FilterControlledByFloat adjustControlToSet, LinkedHashMap<String,Float> parameters){
		
			adjustControlToSet.setAllParameters(parameters);
			
			checkAndActivateFilter(adjustControlToSet);
			execute();
	} 
	
	public void setOneParameter(FilterControlledByFloat filterToSet, String name, Float value) {
			filterToSet.setParameter(name, value);
			checkAndActivateFilter(filterToSet);
			execute();	
	}
	  
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass){
		
		if (areIndexLegal(layerIndex, filterIndex)) {
	
			FilterControlledByFloat filterToBypass = ((FilterControlledByFloat)((Layer)chainOfFilters.getCommand(layerIndex)).getFilter(filterIndex));
			
			filterToBypass.bypass(bypass);
			checkAndActivateFilter(filterToBypass);			
			execute();
		}
	}  
	
	public Boolean areIndexLegal(int layerIndex, int filterIndex) {
		return getNumberOfLayers() > layerIndex && ((Layer)chainOfFilters.getCommand(layerIndex)).getNumberOfFilters()  >= filterIndex;
	}

	public void checkAndActivateLayer (Layer layer) {		
		layer.activate();
		if (layer.getNumberOfFilters()>0) {
			layer.getFirstFilter().activate();
		}
		for (int i= layer.layerIndex(); i<getNumberOfLayers() ; i++) {
			getLayer(i).activate();
			if (getLayer(i).getNumberOfFilters()>0) {
				getLayer(i).getFirstFilter().activate();
			}
		}
	}
	
	public void checkAndActivateFilter (Filter newFilter) {	
		getLayer(newFilter.layerIndex()).activate();
		newFilter.activate();
		for (int i=newFilter.layerIndex()+1; i<getNumberOfLayers() ; i++) {
			checkAndActivateLayer(getLayer(i));
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

}