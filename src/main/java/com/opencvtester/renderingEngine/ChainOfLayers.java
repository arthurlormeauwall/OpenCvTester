package com.opencvtester.renderingEngine;

import java.util.LinkedHashMap;
import java.util.Stack;

import com.opencvtester.baseClasses.ChainOfCommands;
import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.baseClasses.frame.FrameInterface;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.renderingEngine.renderer.ChainOfLayersRenderer;

public class ChainOfLayers extends CompositeFilter
{
	protected FrameInterface background;
	protected LayersFactory layersFactory;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public ChainOfLayers (FiltersDataBase filterDataBase, FrameInterface background, Id id) {
		super(filterDataBase, id);
		
		this.background = background;
		layersFactory=new LayersFactory(filtersDataBase);
		indexType="layer";
		chainOfFilters = new ChainOfCommands (this.indexType);	
		renderer=new ChainOfLayersRenderer(this, background);
	}
	
	/*
	 * GETTERS AND SETTERS
	 */	
	public FiltersDataBase getFilterDataBase() {
		return filtersDataBase;
	}
	
	public Layer getLastLayer(){
		return (Layer)chainOfFilters.getCommand(chainOfFilters.getSize() - 1);
	}   
	
	public Layer getLayer(int layerIndex) {
		return (Layer)chainOfFilters.getCommand(layerIndex);
	}
	
	public int getNumberOfLayers() {
		return chainOfFilters.getSize();
	}	
	
	/*
	 * FEATURES
	 */
	public Layer createLayer(Stack<Id> filterId, Stack<String> filterNames) {	
		if (!isIndexOutOfRange(filterId.get(0))) {
			Layer layer = layersFactory.createLayer(filterId, filterNames);	
			return layer;
		}
		else {
			return null;
		}
	}	
	
	public void  addLayer(Layer newLayer) {
		add(newLayer);
		
		checkAndActivateLayer(newLayer);
		execute();
	}

	public void delLayer(Layer layer){
		delete(layer);
		
		if (layer.getLayerIndex()>0) {
			checkAndActivateLayer(this.getLayer(layer.getLayerIndex()-1));
		}
		
		execute();		
	}  
	
	public Filter createFilter(Id filterId, String filterName) {		
		return  ((Layer)chainOfFilters.getCommand(filterId.layerIndex())).createFilter(filterId, filterName);
	}
	
	public void addFilter(Filter filter) {
		if (areIndexLegal(filter.getLayerIndex(), filter.getFilterIndex())) {
			((Layer)chainOfFilters.getCommand(filter.getLayerIndex())).add(filter);
			
			checkAndActivateFilter(filter);
			execute();	
		}
	}
	
	public void delFilter(Filter filter){
		((Layer)chainOfFilters.getCommand(filter.getLayerIndex())).delete(filter);
		
		if (this.getLayer(filter.getLayerIndex()).getNumberOfFilters()==0 || filter.getFilterIndex()==0) {
			checkAndActivateLayer(this.getLayer(filter.getLayerIndex()));
		}
		else {
			checkAndActivateFilter(this.getLayer(filter.getLayerIndex()).getFilter(filter.getFilterIndex()-1));
		}

		execute();
	}  

	public void setOpacity(Filter opacityFilter, Float opacity){
		if (getNumberOfLayers() >opacityFilter.getLayerIndex()) {
			((Layer)chainOfFilters.getCommand(opacityFilter.getLayerIndex())).setOpacity(opacity);
			
			checkAndActivateLayer(getLayer(opacityFilter.getLayerIndex()));
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
	
	public void setBypass(Id id, Boolean bypass){
		
		if (areIndexLegal(id.layerIndex(), id.filterIndex())) {
	
			FilterControlledByFloat filterToBypass = ((FilterControlledByFloat)((Layer)chainOfFilters.getCommand(id.layerIndex())).get(id.filterIndex()));
			
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
		for (int i= layer.getLayerIndex(); i<getNumberOfLayers() ; i++) {
			getLayer(i).activate();
			if (getLayer(i).getNumberOfFilters()>0) {
				getLayer(i).getFirstFilter().activate();
			}
		}
	}
	
	public void checkAndActivateFilter (Filter newFilter) {	
		getLayer(newFilter.getLayerIndex()).activate();
		newFilter.activate();
		for (int i=newFilter.getLayerIndex(); i<getNumberOfLayers() ; i++) {
			checkAndActivateLayer(getLayer(i));
		}
	}

	public void execute() {
		renderer.execute(chainOfFilters.getChain());
	}
}