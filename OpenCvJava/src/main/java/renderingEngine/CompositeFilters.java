package renderingEngine;

import baseClasses.history.IdHistory;
import baseClasses.history.IdHistoryParameter;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;
import renderingEngine.renderer.Renderer;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.chain.ChainOfCommands;
import baseClasses.filter.Filter;

public abstract class CompositeFilters extends Filter
{
	protected Stack<Frame> frames;
	protected FiltersDataBase filtersDataBase;
	protected ChainOfCommands chainOfFilters;
	protected Renderer renderer;
	
	public CompositeFilters(FiltersDataBase filtersDataBase, Id id, IdHistory<Id>  renderAtIdHistory) {
		super(id, renderAtIdHistory);
		frames = new Stack<Frame>();
		this.filtersDataBase = filtersDataBase;
		
		
		Id chainId = new Id(this.id.get());
		
		chainOfFilters = new ChainOfCommands (chainId);		
	}
	
	protected abstract Filter create(Stack<Id> ids, Stack<String> filterNamesInDataBase);
	public abstract int groupDeepnessIndex() ;
	
	public Filter createAndAdd(Stack<Id>  id, Stack<String> commandsNamesInDataBase) {	
		if (!isIndexOutOfRange(id.get(0))) {
			Filter filter = create(id, commandsNamesInDataBase);
			updateRenderAtId(id.get(0));
			chainOfFilters.addCommand(id.get(0), filter, groupDeepnessIndex());
			return filter;
		}
		else {
			return null;
		}
	}	
	
	public Filter add(Filter filter) {
		
		if (!isIndexOutOfRange(filter.getId())) {		
			updateRenderAtId(filter.getId());
			chainOfFilters.addCommand(filter.getId(), filter, groupDeepnessIndex());	
			return filter;
		}
		else {
			return null;
		}
	}
	
	public Filter delete(Id id) {
		if (!isIndexOutOfRange(id)) {
			return (Filter)chainOfFilters.delCommand(id,groupDeepnessIndex());	
		}
		else {
			return null;
		}
	}
	
	public Boolean isIndexOutOfRange(Id controlId) {
		int indexOfFilterToAddOrDelete= controlId.get()[groupDeepnessIndex()];

		if(chainOfFilters.getSize()>= indexOfFilterToAddOrDelete) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void updateRenderAtId(Id id) {
		Id tempId= new Id();
		
		int filterId= id.get()[1]-1;
		int layerId= id.get()[0];
		
		if ( filterId < 0) { 
			filterId = 0; 
			layerId--;
			if (layerId <0) { layerId=0;}
		}
		tempId.set(layerId, filterId);
	
		IdHistoryParameter idHistoryParameter= new IdHistoryParameter();
		idHistoryParameter.set(tempId);

		renderAtIdHistory.setState(idHistoryParameter);
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

}
