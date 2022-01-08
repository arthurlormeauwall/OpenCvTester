package com.opencvtester.renderingEngine;

import com.opencvtester.baseClasses.ChainOfCommands;
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
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public Layer (FiltersDataBase filtersDatabase, Id id) {
		super(filtersDatabase, id);
		indexType="filter";
		chainOfFilters = new ChainOfCommands (this.indexType);	
		opacityFilter = filtersDatabase.getAlphaFilter();
		renderer= new LayerRenderer(this);
	}
	
	protected void init(FrameInterface background, FrameInterface source, FrameInterface dest) {
		setFrameIn(source);
		setFrameOut(dest);

		opacityFilter.setFrameIn(source);
		opacityFilter.setFrameOut(dest);
		opacityFilter.init(background);
		opacityFilter.setLayerIndex(id.layerIndex());
		opacityFilter.setFilterIndex(1);
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

	public FilterControlledByFloat getFilter(int index) {
		return (FilterControlledByFloat)chainOfFilters.getCommand(index);
	}

	
	/*
	 * FEATURES
	 */
	protected Filter getFilterFromDatabase(Id id, String filterNamesInDataBase){
		Filter newFilter = (Filter) filtersDataBase.getFilter(filterNamesInDataBase);
		newFilter.setId(id.clone());
		return newFilter;
	}
	
	public Filter createAndAdd(Id  id,String commandsNamesInDataBase) {	
		if (!isIndexOutOfRange(id)) {
			Filter filter = getFilterFromDatabase(id, commandsNamesInDataBase);
			chainOfFilters.addCommand(filter);
			return filter;
		}
		else {
			return null;
		}
	}
	
	public void execute() {	
		renderer.execute(chainOfFilters.getChain());
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

}
