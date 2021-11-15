package renderingEngine;

import filtersDataBase.FiltersDataBase;
import guiManager.GroupsId;
import openCvAdapter.CvFrame;
import renderingEngine.renderer.Renderer;

import java.util.Stack;

import baseClasses.ChainOfCommands;
import baseClasses.Command;
import baseClasses.Id;
import baseClasses.filter.Filter;

public abstract class CompositeFilter extends Filter
{
	protected Stack<CvFrame> frames;
	protected FiltersDataBase filtersDataBase;
	protected ChainOfCommands chainOfFilters;
	protected Renderer renderer;
	protected GroupsId groupID;
	
	public CompositeFilter(FiltersDataBase filtersDataBase, Id id) {
		super(id);
		frames = new Stack<CvFrame>();
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
	
	public Boolean isIndexOutOfRange(Id controlId) {
		int indexOfFilterToAddOrDelete= controlId.get()[indexType()];

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

}
