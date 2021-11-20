package com.opencvtester.renderingEngine;

import java.util.Stack;

import com.opencvtester.baseClasses.ChainOfCommands;
import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.frame.Frame;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.guiManager.GroupsId;
import com.opencvtester.renderingEngine.renderer.Renderer;

public abstract class CompositeFilter extends Filter
{
	protected Stack<Frame> frames;
	protected FiltersDataBase filtersDataBase;
	protected ChainOfCommands chainOfFilters;
	protected Renderer renderer;
	protected GroupsId groupID;
	
	public CompositeFilter(FiltersDataBase filtersDataBase, Id id) {
		super(id);
		frames = new Stack<Frame>();
		this.filtersDataBase = filtersDataBase;	
		
		Id chainId = new Id(this.id.get());
		
		chainOfFilters = new ChainOfCommands (chainId);		
	}
	
	protected abstract Filter create(Stack<Id> ids, Stack<String> filterNamesInDataBase);
	
	public Filter createAndAdd(Stack<Id>  id, Stack<String> commandsNamesInDataBase) {	
		if (!isIndexOutOfRange(id.get(0))) {
			Filter filter = create(id, commandsNamesInDataBase);
			chainOfFilters.addCommand(id.get(0), filter, indexType());
			return filter;
		}
		else {
			return null;
		}
	}	
	
	public Filter add(Filter filter) {	
		if (!isIndexOutOfRange(filter.getId())) {		
			chainOfFilters.addCommand(filter.getId(), filter, indexType());	
			return filter;
		}
		else {
			return null;
		}
	}
	
	public Filter delete(Id id) {
		if (!isIndexOutOfRange(id)) {
			return (Filter)chainOfFilters.delCommand(id,indexType());	
		}
		else {
			return null;
		}
	}
	
	public Boolean isIndexOutOfRange(Id filterId) {
		int indexOfFilterToAddOrDelete= filterId.get()[indexType()];

		if(chainOfFilters.getSize()>= indexOfFilterToAddOrDelete) {
			return false;
		}
		else {
			return true;
		}
	}

	public Command get(int index) {
		return chainOfFilters.getCommand(index);
	}
	
	public ChainOfCommands getChain() {
		return chainOfFilters;
	}
	
	public void setChain(ChainOfCommands chain) {
		chainOfFilters=chain;
	}	
	
	public FiltersDataBase getFiltersDataBase() {
		return filtersDataBase;
	}
	
	public int indexType() {
		return groupID.ordinal();
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