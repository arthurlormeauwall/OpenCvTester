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
	protected LayerFactory layersFactory;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public ChainOfLayers (FiltersDataBase filterDataBase, FrameInterface background, Id id) {
		super(filterDataBase, id);
		
		this.background = background;
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
		if (!isIndexOutOfRange(filterId.get(0).layerIndex())) {
			Layer layer = LayerFactory.createLayer(filterId, filterNames, filtersDataBase);	
			return layer;
		}
		else {
			return null;
		}
	}	
	public Layer createEmptyLayer(int layerIndex) {	
		if (!isIndexOutOfRange(layerIndex)) {
			Layer layer = LayerFactory.createEmptyLayer(layerIndex,filtersDataBase);	
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

	public void deleteLayer(Layer layer){
		delete(layer);
		
		if (layer.layerIndex()>0) {
			checkAndActivateLayer(this.getLayer(layer.layerIndex()-1));
		}
		
		execute();		
	}  
	
	public Filter createFilter(Id filterId, String filterName) {		
		return  ((Layer)chainOfFilters.getCommand(filterId.layerIndex())).createFilter(filterId, filterName);
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
			checkAndActivateLayer(this.getLayer(filter.layerIndex()));
		}
		else {
			checkAndActivateFilter(this.getLayer(filter.layerIndex()).getFilter(filter.filterIndex()-1));
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
	
	public void setBypass(Id id, Boolean bypass){
		
		if (areIndexLegal(id.layerIndex(), id.filterIndex())) {
	
			FilterControlledByFloat filterToBypass = ((FilterControlledByFloat)((Layer)chainOfFilters.getCommand(id.layerIndex())).getFilter(id.filterIndex()));
			
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
}