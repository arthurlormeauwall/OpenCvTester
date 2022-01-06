package com.opencvtester.renderingEngine;

import java.util.LinkedHashMap;
import java.util.Stack;

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
		
	public ChainOfLayers (FiltersDataBase dbControls, FrameInterface background, Id id) {
		super(dbControls, id);
		layersFactory=new LayersFactory(background, source, dest, filtersDataBase);
		groupID="layer";
		this.background = background;
		renderer=new ChainOfLayersRenderer(this, background);
	}
	
	
	public Layer createLayer(Stack<Id> filterId, Stack<String> filterNames) {
		Layer newLayer = (Layer)createLayer(filterId, filterNames);
		return newLayer;
	}
	
	public Layer addLayer(Stack<Id> filterId, Stack<String> filterNames){
		Layer newLayer = (Layer)createAndAdd(filterId, filterNames);
		checkAndActivateLayer(new Id(newLayer.getLayerIndex()-1,0));
		execute();
		return newLayer;
	}
	
	public Layer addLayer(Layer newLayer) {
		add(newLayer);
		checkAndActivateLayer(new Id(newLayer.getLayerIndex()-1,0));
		execute();
		return newLayer;
	}

//	public Layer delLayer(Layer layer){
//		Layer newLayer = (Layer)delete(layerId);
//		checkAndActivateLayer(new Id(layerId.layerIndex()-1, 0));
//		execute();
//		return newLayer;		
//	}  
//	
	public Layer delLayer(Layer layer){
		Layer newLayer = (Layer)delete(layer);
		checkAndActivateLayer(new Id(layer.getLayerIndex()-1, 0));
		execute();
		return newLayer;		
	}  
	
	public Filter createFilter(Stack<Id> filterId, String filterNames) {
		Stack<String> stackOfFilterNames = new Stack<String>();
		stackOfFilterNames.push(filterNames);		
		return  ((Layer)chainOfFilters.getCommand(filterId.get(0).layerIndex())).create(filterId, stackOfFilterNames);
	}
	
	public Filter addFilterInLayer(Filter filter) {
		((Layer)chainOfFilters.getCommand(filter.getLayerIndex())).add(filter);
		activateFilter(filter);
		execute();	
		return filter;
	}
	
	public Filter delFilterInLayer(Filter filter){
		Filter erasedFilter =((Layer)chainOfFilters.getCommand(filter.getLayerIndex())).delete(filter);
		
		checkAndActivateLayer(new Id(filter.getLayerIndex(), filter.getFilterIndex()-1));

		execute();
		return erasedFilter;
	}  
	
//	public Filter delFilterInLayer(Filter filter){
//		if (getNumberOfLayers()> filter.getLayerIndex()) {
//			
//			
//			Filter erasedFilter =((Layer)chainOfFilters.getCommand(filter.getLayerIndex())).delete(filter.getId());
//			checkAndActivateLayer(new Id(filter.getLayerIndex(), filter.getFilterIndex()-1));
//			execute();
//			return erasedFilter;
//		}
//		else {
//			return null;
//		}	
//	} 

	public FilterControlledByFloat createAndAddFilterInLayer(Stack<Id> filterId, String filterNames) {
		Stack<String> stackOfFilterNames = new Stack<String>();
		stackOfFilterNames.push(filterNames);
		
		FilterControlledByFloat newFilter =(FilterControlledByFloat) ((Layer)chainOfFilters.getCommand(filterId.get(0).layerIndex())).createAndAdd(filterId, stackOfFilterNames);
		
		activateFilter(newFilter);

		execute();	
		return newFilter;
	}

	public void setOpacity(int layerIndex, Float opacity){
		if (getNumberOfLayers() >layerIndex) {
			((Layer)chainOfFilters.getCommand(layerIndex)).setOpacity(opacity);
			checkAndActivateLayer(new Id(layerIndex, 0));
			execute();
		}
	}  
	
	public void setParameters(FilterControlledByFloat adjustControlToSet, LinkedHashMap<String,Float> parameters){
		
			adjustControlToSet.setParameter(parameters);
			activateFilter(adjustControlToSet);
			execute();
	} 
	
	public void setParameters(FilterControlledByFloat filterToSet, String name, Float value) {
			filterToSet.setParameters(name, value);
			activateFilter(filterToSet);
			execute();	
	}
	
	public void setBypass(Id id, Boolean bypass){
		
		if (areIndexLegal(id.layerIndex(), id.filterIndex())) {
	
			FilterControlledByFloat filterToBypass = ((FilterControlledByFloat)((Layer)chainOfFilters.getCommand(id.layerIndex())).get(id.filterIndex()));
			
			filterToBypass.bypass(bypass);
			checkAndActivateLayer(new Id(id.layerIndex(), id.filterIndex()));			
			execute();
		}
	}    
	
	public Boolean areIndexLegal(int layerIndex, int filterIndex) {
		return getNumberOfLayers() > layerIndex && ((Layer)chainOfFilters.getCommand(layerIndex)).getNumberOfFilters()  > filterIndex;
	}
	
	public void activateFilter(Filter filterToSet) {
		((Layer)chainOfFilters.getCommand(filterToSet.getLayerIndex())).activate();
		
		if (((Layer)chainOfFilters.getCommand(filterToSet.getLayerIndex())).getFilter(filterToSet.getFilterIndex())!=null) {
			((Layer)chainOfFilters.getCommand(filterToSet.getLayerIndex())).getFilter(filterToSet.getFilterIndex()).activate();
		}

		if (((Layer)chainOfFilters.getCommand(filterToSet.getLayerIndex()+1))!=null) {
			if  (((Layer)chainOfFilters.getCommand(filterToSet.getLayerIndex()+1)).getNumberOfFilters()>0) {
				((Layer)chainOfFilters.getCommand(filterToSet.getLayerIndex()+1)).getFilter(0).activate();
			}
		}
	}
	
	public void checkAndActivateLayer (Id id) {	
		if(getNumberOfLayers()!=0) {
			if (id.layerIndex()<=0){
				activateLayer(new Id(0,0));
			}
			else {
				activateLayer(id);
			}
		}
	}
	
	public void activateLayer(Id id) {
		Layer layerToActivate=((Layer)chainOfFilters.getCommand(id.layerIndex()));
		
		layerToActivate.activate();
		
		if (layerToActivate.hasFilter()) {
			if (layerToActivate.getNumberOfFilters()<1) {
				activateFilter(layerToActivate.getFirstFilter());	
			}
			else if (layerToActivate.getNumberOfFilters()>=1){
				FilterControlledByFloat filterToActivate=((Layer)chainOfFilters.getCommand(id.layerIndex())).getFilter(id.filterIndex());
				activateFilter(filterToActivate);	
			}
		}
	}	
	
	protected Filter create(Stack<Id> filterId, Stack<String> filterName){
		return layersFactory.createLayer(filterId, filterName);	
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