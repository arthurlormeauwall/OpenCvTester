package com.opencvtester.renderingEngine;

import java.util.Stack;

import com.opencvtester.baseClasses.ChainOfCommands;
import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.frame.FrameInterface;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.renderingEngine.renderer.Renderer;

public abstract class CompositeFilter extends Filter
{
	protected Stack<FrameInterface> frames;
	protected FiltersDataBase filtersDataBase;
	protected ChainOfCommands chainOfFilters;
	protected Renderer renderer;
	protected String indexType;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public CompositeFilter(FiltersDataBase filtersDataBase, Id id) {
		super(id);
		frames = new Stack<FrameInterface>();
		this.filtersDataBase = filtersDataBase;	
	}
	public CompositeFilter(FiltersDataBase filtersDataBase) {
		frames = new Stack<FrameInterface>();
		this.filtersDataBase = filtersDataBase;	
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public ChainOfCommands getChain() {
		return chainOfFilters;
	}
	
	public void setChain(ChainOfCommands chain) {
		chainOfFilters=chain;
	}	
	
	public FiltersDataBase getFiltersDataBase() {
		return filtersDataBase;
	}
	
	public String indexType() {
		return indexType;
	}
	
	/*
	 * FEATURES
	 */
	protected void add(Filter filter) {	
		chainOfFilters.addCommand(filter);	
	}
	
	public void delete(Filter filter) {
		chainOfFilters.delCommand(filter.getIndex(indexType));	
	}
	
	public Boolean isIndexOutOfRange(int index) {
		if(chainOfFilters.getSize()>= index) {
			return false;
		}
		else {
			return true;
		}
	}

	public Filter getFirstFilter() {
		if (chainOfFilters.getSize()==0) {
			return this;
		}
		else{
			return (Filter)chainOfFilters.getCommand(0);
		}
	}
}
