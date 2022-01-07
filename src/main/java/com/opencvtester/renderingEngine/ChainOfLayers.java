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
	public ChainOfLayers (FiltersDataBase dbControls, FrameInterface background, Id id) {
		super(dbControls, id);
		
		this.background = background;
		layersFactory=new LayersFactory(background, frameIn, frameOut, filtersDataBase);
		indexType="layer";
		chainOfFilters = new ChainOfCommands (this.indexType);	
		renderer=new ChainOfLayersRenderer(this, background);
	}
	
	/*
	 * FEATURES
	 */
	public Layer createAndAddLayer(Stack<Id> filterId, Stack<String> filterNames){
		Layer newLayer = createLayer(filterId, filterNames);
		return addLayer(newLayer);
	}
	
	public Layer createLayer(Stack<Id> filterId, Stack<String> filterNames) {	
		if (!isIndexOutOfRange(filterId.get(0))) {
			Layer layer = layersFactory.createLayer(filterId, filterNames);				
			return layer;
		}
		else {
			return null;
		}
	}	
	
	public Layer addLayer(Layer newLayer) {
		add(newLayer);
		
		checkAndActivateLayer(newLayer);
		execute();
		return newLayer;
	}

	public Layer delLayer(Layer layer){
		Layer newLayer = (Layer)delete(layer);
		
		if (layer.getLayerIndex()>0) {
			checkAndActivateLayer(this.getLayer(newLayer.getLayerIndex()-1));
		}
		
		execute();
		return newLayer;		
	}  
	
	public Filter createFilter(Id filterId, String filterName) {		
		return  ((Layer)chainOfFilters.getCommand(filterId.layerIndex())).getFilterFromDatabase(filterId, filterName);
	}
	
	public Filter addFilterInLayer(Filter filter) {
		if (areIndexLegal(filter.getLayerIndex(), filter.getFilterIndex())) {
			((Layer)chainOfFilters.getCommand(filter.getLayerIndex())).add(filter);
			
			checkAndActivateFilter(filter);
			execute();	
			return filter;
		}
		else {
			return null;
		}
	}
	
	public Filter delFilterInLayer(Filter filter){
		Filter erasedFilter =((Layer)chainOfFilters.getCommand(filter.getLayerIndex())).delete(filter);
		
		if (this.getLayer(filter.getLayerIndex()).getNumberOfFilters()==0) {
			checkAndActivateLayer(this.getLayer(filter.getLayerIndex()));
		}
		else if (this.getLayer(filter.getLayerIndex()).getNumberOfFilters()==1) {
			checkAndActivateFilter(filter);
		}
		else {
			checkAndActivateFilter(this.getLayer(filter.getLayerIndex()).getFilter(filter.getFilterIndex()-1));
		}

		execute();
		return erasedFilter;
	}  

	public FilterControlledByFloat createAndAddFilterInLayer(Id filterId, String filterName) {
		FilterControlledByFloat newFilter =(FilterControlledByFloat) ((Layer)chainOfFilters.getCommand(filterId.layerIndex())).createAndAdd(filterId, filterName);
		
		checkAndActivateFilter(newFilter);

		execute();	
		return newFilter;
	}

	public void setOpacity(Filter opacityFilter, Float opacity){
		if (getNumberOfLayers() >opacityFilter.getLayerIndex()) {
			((Layer)chainOfFilters.getCommand(opacityFilter.getLayerIndex())).setOpacity(opacity);
			
			checkAndActivateLayer(getLayer(opacityFilter.getLayerIndex()));
			execute();
		}
	}  
	
	public void setParameters(FilterControlledByFloat adjustControlToSet, LinkedHashMap<String,Float> parameters){
		
			adjustControlToSet.setParameter(parameters);
			
			checkAndActivateFilter(adjustControlToSet);
			execute();
	} 
	
	public void setParameters(FilterControlledByFloat filterToSet, String name, Float value) {
			filterToSet.setParameters(name, value);
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
		return getNumberOfLayers() > layerIndex && ((Layer)chainOfFilters.getCommand(layerIndex)).getNumberOfFilters()  > filterIndex;
	}
	

	public void checkAndActivateLayer (Layer newLayer) {		
		newLayer.activate();
		if (newLayer.getNumberOfFilters()>0) {
			newLayer.getFirstFilter().activate();
		}
		for (int i= newLayer.getLayerIndex(); i<getNumberOfLayers() ; i++) {
			getLayer(i).activate();
			if (getLayer(i).getNumberOfFilters()>0) {
				getLayer(i).getFirstFilter().activate();
			}
		}
	}
	
	public void checkAndActivateFilter (Filter newFilter) {	
		this.getLayer(newFilter.getLayerIndex()).activate();
		newFilter.activate();
		for (int i=newFilter.getLayerIndex(); i<getNumberOfLayers() ; i++) {
			getLayer(i).activate();
			if (getLayer(i).getNumberOfFilters()>0) {
				getLayer(i).getFirstFilter().activate();
			}
		}
	}

	public void execute() {
		renderer.execute(chainOfFilters.getChain());
	}

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
}