package renderingEngine;

import baseClasses.history.historyParameters.IdHistoryParameter;
import baseClasses.history.imp.IdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.IoFrame;
import baseClasses.chain.ChainAction;
import baseClasses.chain.ChainControl;
import baseClasses.chain.ChainOfFilters;
import baseClasses.filter.Filter;

public abstract class CompositeFilters extends Filter
{
	protected Stack<Frame> frames;
	protected FiltersDataBase dbControls;
	protected ChainOfFilters chainOfFilters;
	
	public CompositeFilters(FiltersDataBase dbControls, Id id, IdHistory<Id>  renderAtIdHistory) {
		super(id, renderAtIdHistory);
		frames = new Stack<Frame>();
		this.dbControls = dbControls;
		
		
		Id chainId = new Id(this.id.get());
		chainId.setGroupId(this.id.getGroupId() + 1);
		chainOfFilters = new ChainOfFilters (chainId, this.renderAtIdHistory);		
		
	}
	
	public abstract Command getLastFilter();
	public abstract int getNumberOfFilters();
	protected abstract Filter createFilter(Stack<Id> ids, Stack<String> filterNamesInDataBase);
	
	public Filter addFilter(Stack<Id>  id, Stack<String> commandsNamesInDataBase) {
		
		if (!isIndexOutOfRange(id.get(0))) {
			Filter filter = createFilter(id, commandsNamesInDataBase);
			updateRenderAtId(id.get(0));
			addOrDelete(ChainControl.ADD, filter, id);	
			return filter;
		}
		else {
			return null;
		}
	}	
	
public Filter addFilter(Filter filter) {
		
		if (!isIndexOutOfRange(filter.getId())) {
			
			updateRenderAtId(filter.getId());
			Stack<Id> id= new Stack<Id>();
			id.push(filter.getId());
			addOrDelete(ChainControl.ADD, filter, id);	
			return filter;
		}
		else {
			return null;
		}
	}
	
	public Filter delFilter(Stack<Id> id) {
		if (!isIndexOutOfRange(id.get(0))) {
			addOrDelete(ChainControl.DELETE, chainOfFilters.getFilter(0), id);
			return chainOfFilters.getFilter(id.get(0).get()[chainOfFilters.getDeepnessIndex()]);
		}
		else {
			return null;
		}
	}

	public Filter addOrDelete (ChainControl chainCommand, Filter filter, Stack<Id> id) {
		ChainAction<Filter> parameter = new ChainAction<Filter>();
		parameter.control = chainCommand;
		parameter.item = filter;
		parameter.id = id;
	
		return chainOfFilters.addOrDelete(parameter);
	}
	
	public Boolean isIndexOutOfRange(Id controlId) {
		int indexOfControlToAddOrDelete= controlId.get()[chainOfFilters.getDeepnessIndex()];

		if(chainOfFilters.getSize()>= indexOfControlToAddOrDelete) {
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

	public Command getFilter(int index) {
		return chainOfFilters.getFilter(index);
	}
	
	public ChainOfFilters getChain() {
		return chainOfFilters;
	}
	
	public void setChain(ChainOfFilters chain) {
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

				((IoFrame)chainOfFilters.getFilter(0)).setSource(source);
				((IoFrame)chainOfFilters.getFilter(0)).setDest(frames.get(0));

				for (int j = 1; j < numberOfControls - 1; j++) {
					((IoFrame)chainOfFilters.getFilter(j)).setSource(frames.get(j - 1));
					((IoFrame)chainOfFilters.getFilter(j)).setDest(frames.get(j));
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
			chainOfFilters.getFilter(i).execute();
		}	
	}

}
