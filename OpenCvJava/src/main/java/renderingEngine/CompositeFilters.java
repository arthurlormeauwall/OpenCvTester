package renderingEngine;

import baseClasses.history.historyParameters.IdHistoryParameter;
import baseClasses.history.imp.UndoIdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.IoFrame;
import baseClasses.chain.ChainAction;
import baseClasses.chain.ChainControl;
import baseClasses.chain.ChainOfCommands;
import baseClasses.filter.Filter;

public abstract class CompositeFilters extends Filter
{
	protected Stack<Frame> frames;
	protected FiltersDataBase dbControls;
	protected ChainOfCommands chainOfFilters;
	
	public CompositeFilters(FiltersDataBase dbControls, Id id, UndoIdHistory<Id> undoIdHistory, UndoIdHistory<Id>  renderAtIdHistory) {
		super(id, undoIdHistory, renderAtIdHistory);
		frames = new Stack<Frame>();
		this.dbControls = dbControls;
		
		
		Id chainId = new Id(this.id.get());
		chainId.setGroupId(this.id.getGroupId() + 1);
		chainOfFilters = new ChainOfCommands (chainId, this.undoIdHistory, this.renderAtIdHistory);		
		
	}
	
	public abstract Command getLastControl();
	public abstract int getNumberOfControl();
	protected abstract Filter createFilter(Stack<Id> ids, Stack<String> filterNamesInDataBase);
	
	public Boolean addFilter(Stack<Id>  id, Stack<String> commandsNamesInDataBase) {
		
		if (!isIndexOutOfRange(id)) {
			Command command = createFilter(id, commandsNamesInDataBase);
			updateRenderAtId(id.get(0));
			return addOrDelete(ChainControl.ADD, command, id);	
		}
		else { 
			return false;
		}
	}	
	
	public Boolean delFilter(Stack<Id> id) {
		if (!isIndexOutOfRange(id)) {
			return addOrDelete(ChainControl.DELETE, chainOfFilters.getCommand(0), id);
		}
		else {
			return false;
		}
	}

	public Boolean addOrDelete (ChainControl chainCommand, Command control, Stack<Id> id) {
		ChainAction<Command> parameter = new ChainAction<Command>();
		parameter.control = chainCommand;
		parameter.item = control;
		parameter.id = id;
	
		return chainOfFilters.addOrDelete(parameter);
	}
	
	public Boolean isIndexOutOfRange(Stack<Id> controlId) {
		int indexOfControlToAddOrDelete= controlId.get(0).get()[chainOfFilters.getDeepnessIndex()];

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
		return chainOfFilters.getCommand(index);
	}
	
	public ChainOfCommands getChain() {
		return chainOfFilters;
	}
	
	public void setChain(ChainOfCommands chain) {
		chainOfFilters=chain;
	}	
	
	protected void updateNumberOfFrames() {

		int numberOfControls = getNumberOfControl();
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
		
		int numberOfControls = getNumberOfControl();
		updateNumberOfFrames();

		int lastFrameIndex = frames.size() - 1;

		if (numberOfControls>0) {
			Command lastControl =  getLastControl();

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
			dest.setFrame(source.getFrame());
		}
	}
	
	public void render() {
		int size = chainOfFilters.getSize();
		int firstControl = chainOfFilters.getCommandIndex(renderAtIdHistory);

		for (int i = firstControl; i < size; i++) {
			chainOfFilters.getCommand(i).execute();
		}	
	}

}
