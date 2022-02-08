package com.opencvtester.renderingEngine;

import com.opencvtester.baseClasses.ChainOfCommands;
import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.baseClasses.frame.FrameInterface;
import com.opencvtester.dataAccess.LayerData;
import com.opencvtester.filtersDataBase.OpacityFilter;
import com.opencvtester.renderingEngine.renderer.LayerRenderer;

public class Layer extends CompositeFilter
{
	protected FrameInterface background;
	protected OpacityFilter opacityFilter;
	protected LayerData layerData;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public Layer (LayerData layerData, OpacityFilter opacityFilter) {
		super();
		this.layerData=layerData;
		setLayerIndex(layerData.getLayerIndex());
		indexType="filter";
		chainOfFilters = new ChainOfCommands (this.indexType);	
		this.opacityFilter = opacityFilter;
		
		opacityFilter.setLayerIndex(layerIndex());
		opacityFilter.setFilterIndex(-1);
		
		renderer= new LayerRenderer(this);
	}
	/*
	 * GETTERS & SETTERS
	 */
	public OpacityFilter getOpacityFilter(){
		return opacityFilter;
	}
	
	public void setOpacity(Float opacity){
		opacityFilter.setOpacity(opacity);
	}

	public void setBackGround(FrameInterface background){
		this.background = background;
		opacityFilter.setBackGround(background);
	}
	
	public int getNumberOfFilters() {
		return chainOfFilters.getSize();
	}
	
	public Filter getLastFilter() {
		return (Filter)chainOfFilters.getCommand(getNumberOfFilters()-1);
	}

	public Boolean hasFilter() {
		return (getNumberOfFilters()!=0);
	}

	public FilterControlledByFloat getFilter(int index) {
		return (FilterControlledByFloat)chainOfFilters.getCommand(index);
	}

	
	/*
	 * FEATURES
	 */
	
	public void execute() {	
		renderer.execute(chainOfFilters.getChain());
	}

	public void dealFrames() {
		renderer.dealFrames(chainOfFilters.getChain());		
	}
	
	public Command clone() {	
		Layer newMaskedLayer= new Layer(layerData, opacityFilter);
		
		newMaskedLayer.setChain(chainOfFilters.clone());
		newMaskedLayer.setBackGround(background);
		
		return newMaskedLayer;
	}
	
	public void addFilter(Filter filter) {
		if (filter.layerIndex()==layerIndex()) {
			add(filter);
		}
	}

	public void deleteFilter(Filter filter) {
		if (filter.layerIndex()==layerIndex()) {
			delete(filter);
		}
	}
	
	public void deleteAllFilters() {
		int size=getNumberOfFilters();
		for (int i=size-1; i>=0;i--) {
			deleteFilter(getLastFilter());
		}
	}

	public LayerData getData() {
		
		return layerData;
	}
}
