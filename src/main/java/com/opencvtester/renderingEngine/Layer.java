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
	protected FilterFactory filterFactory;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public Layer (FiltersDataBase filtersDatabase, Id id) {
		super(filtersDatabase, id);
		indexType="filter";
		chainOfFilters = new ChainOfCommands (this.indexType);	
		opacityFilter = filtersDatabase.getOpacityFilter();
		opacityFilter.setLayerIndex(id.layerIndex());
		opacityFilter.setFilterIndex(-1);
		renderer= new LayerRenderer(this);
		filterFactory= new FilterFactory(filtersDatabase);
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
	public Filter createFilter(Id  id,String filterNamesInDataBase) {
		return filterFactory.createFilter(id, filterNamesInDataBase);
	}
	
	public void createAndAdd(Id  id,String filterNamesInDataBase) {	
		if (!isIndexOutOfRange(id)) {
			Filter filter = createFilter(id, filterNamesInDataBase);		
			add(filter);
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
