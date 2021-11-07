package renderingEngine;

import baseClasses.history.IdHistory;
import baseClasses.history.IdHistoryParameter;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Executable;
import baseClasses.Id;
import baseClasses.IoFrame;
import baseClasses.chain.ChainOfCommands;
import baseClasses.filter.Filter;

public abstract class CompositeFilters extends Filter
{
	protected Stack<Frame> frames;
	protected FiltersDataBase filtersDataBase;
	protected ChainOfCommands chainOfFilters;
	
	public CompositeFilters(FiltersDataBase filtersDataBase, Id id, IdHistory<Id>  renderAtIdHistory) {
		super(id, renderAtIdHistory);
		frames = new Stack<Frame>();
		this.filtersDataBase = filtersDataBase;
		
		
		Id chainId = new Id(this.id.get());
		chainId.setGroupId(this.id.getGroupId() + 1);
		chainOfFilters = new ChainOfCommands (chainId);		
		
	}
	
	public abstract Command getLastFilter();
	public abstract int getNumberOfFilters();
	protected abstract Filter create(Stack<Id> ids, Stack<String> filterNamesInDataBase);
	
	public Filter createAndAdd(Stack<Id>  id, Stack<String> commandsNamesInDataBase) {	
		if (!isIndexOutOfRange(id.get(0))) {
			Filter filter = create(id, commandsNamesInDataBase);
			updateRenderAtId(id.get(0));
			chainOfFilters.addCommand(id.get(0), filter);
			return filter;
		}
		else {
			return null;
		}
	}	
	
	public Filter add(Filter filter) {
		
		if (!isIndexOutOfRange(filter.getId())) {		
			updateRenderAtId(filter.getId());
			chainOfFilters.addCommand(filter.getId(), filter);	
			return filter;
		}
		else {
			return null;
		}
	}
	
	public Filter delete(Id id) {
		if (!isIndexOutOfRange(id)) {
			chainOfFilters.delCommand(id);	
			return (Filter)chainOfFilters.getCommand(id.get()[chainOfFilters.getDeepnessIndex()]);
		}
		else {
			return null;
		}
	}
	
	public Boolean isIndexOutOfRange(Id controlId) {
		int indexOfFilterToAddOrDelete= controlId.get()[chainOfFilters.getDeepnessIndex()];

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
		tempId.set(layerId, filterId, id.getGroupId());
	
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
	
	protected void updateNumberOfFrames() {

		int numberOfControls = getNumberOfFilters();
		int numberOfFrames = frames.size();
		int lastFrame = frames.size() - 1;

		if (numberOfControls >= 1) {
			if (numberOfFrames < numberOfControls - 1) {
				for (int i = numberOfFrames; i < numberOfControls - 1; i++)
				{
					frames.push(new Frame());
					source.copyTo(frames.get(i));
				}
			}
			else if (numberOfFrames > numberOfControls - 1) {
				for (int i = lastFrame; i >= numberOfControls - 1; i--)
				{
					frames.pop();
				}
			}
		}
	}
	
	protected void dealFrames() {
		
		int numberOfControls = getNumberOfFilters();
		updateNumberOfFrames();

		int lastFrameIndex = frames.size() - 1;

		if (numberOfControls>0) {
			Command lastControl =  getLastFilter();

			if (numberOfControls == 1) {
				((IoFrame)lastControl).setSource(source);
				((IoFrame)lastControl).setDest(dest);
			}
			else if (numberOfControls >= 2) {

				((IoFrame)chainOfFilters.getCommand(0)).setSource(source);
				((IoFrame)chainOfFilters.getCommand(0)).setDest(frames.get(0));

				for (int j = 1; j < numberOfControls - 1; j++) {
					((IoFrame)chainOfFilters.getCommand(j)).setSource(frames.get(j - 1));
					((IoFrame)chainOfFilters.getCommand(j)).setDest(frames.get(j));
				}
				((IoFrame)lastControl).setSource(frames.get(lastFrameIndex));
				((IoFrame)lastControl).setDest(dest);
			}
		}
		else {
			dest.setMat(source.getMat());
		}
	}
	
	public void render() {
		int size = chainOfFilters.getSize();
		int firstControl = chainOfFilters.getCommandIndex(renderAtIdHistory);

		for (int i = firstControl; i < size; i++) {
			((Executable)chainOfFilters.getCommand(i)).execute();
		}	
	}
	
	public FiltersDataBase getFiltersDataBase() {
		return filtersDataBase;
	}

}
