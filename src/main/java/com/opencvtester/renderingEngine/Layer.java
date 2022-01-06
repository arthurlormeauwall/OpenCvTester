package com.opencvtester.renderingEngine;

import java.util.LinkedHashMap;
import java.util.Stack;

import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.baseClasses.frame.FrameInterface;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.filtersDataBase.OpacityFilter;
import com.opencvtester.renderingEngine.renderer.LayerRenderer;

public class Layer extends CompositeFilter
{
	protected FrameInterface background;
	protected OpacityFilter opacityFilter;	
	
	public Layer (FiltersDataBase filtersDatabase, Id id) {
		super(filtersDatabase, id);
		groupID="filter";
		opacityFilter = filtersDatabase.getAlphaFilter();
		renderer= new LayerRenderer(this);
	}
	
	protected void init(FrameInterface background, FrameInterface source, FrameInterface dest) {
		setSource(source);
		setDest(dest);

		opacityFilter.setSource(source);
		opacityFilter.setDest(dest);
		opacityFilter.init(background);
		opacityFilter.setLayerIndex(id.layerIndex());
		opacityFilter.setFilterIndex(1);
	}

	protected Filter create(Id id, Stack<String> commandsInDataBase){
		Filter newFilter = (Filter) filtersDataBase.getFilter(commandsInDataBase.get(0));
		newFilter.setId(id.clone());
		return newFilter;
	}
	
	public Filter createAndAdd(Id  id, Stack<String> commandsNamesInDataBase) {	
		if (!isIndexOutOfRange(id)) {
			Filter filter = create(id, commandsNamesInDataBase);
			chainOfFilters.addCommand(filter, indexType());
			return filter;
		}
		else {
			return null;
		}
	}
	
	public void setFloatParameters(int controlIndex, LinkedHashMap<String,Float> parameters){	
		((FilterControlledByFloat)chainOfFilters.getCommand(controlIndex)).setParameter(parameters);
	}
	
	public void execute() {	
		renderer.execute(chainOfFilters.getChain());
	}
	
	public void updateId(String indexType, int newValue){
		chainOfFilters.updateId(indexType, newValue);
	}

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

	public FilterControlledByFloat getFilter(int index) {
		return (FilterControlledByFloat)chainOfFilters.getCommand(index);
	}

	public void dealFrames() {
		renderer.dealFrames(chainOfFilters.getChain());		
	}
	
	public Command clone() {	
		Layer newMaskedLayer= new Layer(filtersDataBase, id);
		
		newMaskedLayer.setChain(chainOfFilters.clone());
		newMaskedLayer.setOpacity(opacityFilter.getOpacity());
		newMaskedLayer.setBackGround(background);
		
		return newMaskedLayer;
	}

	public Filter getLastLayer() {
		return (Filter)chainOfFilters.getCommand(getNumberOfFilters()-1);
	}

	public Boolean hasFilter() {
		if (getNumberOfFilters()==0) {
			return false;
		}
		else {
			return true;
		}
	}
}
