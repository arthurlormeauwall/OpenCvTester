package com.opencvtester.renderer;

import java.util.Stack;

import com.opencvtester.controller.ChainOfCommands;
import com.opencvtester.entity.Filter;
import com.opencvtester.entity.interfaces.FrameInterface;

public abstract class CompositeFilter extends Filter
{
	protected Stack<FrameInterface> frames;
	protected ChainOfCommands chainOfFilters;
	protected Renderer renderer;
	protected String indexType;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public CompositeFilter() {
		super();
		frames = new Stack<FrameInterface>();
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
