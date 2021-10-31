package baseClasses.chain;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.history.historyParameters.IdHistoryParameter;
import baseClasses.history.imp.UndoIdHistory;

import java.util.Stack;

public abstract class ChainOfCommandsManager extends Command
{
	protected ChainOfCommands chainOfControls;
	
	public ChainOfCommandsManager(Id id, UndoIdHistory<Id> undoIdHistory, UndoIdHistory<Id> renderAtIdHistory){
		
		super (id, undoIdHistory, renderAtIdHistory);
		
		Id chainId = new Id(this.id.get());
		chainId.setGroupId(this.id.getGroupId() + 1);
		chainOfControls = new ChainOfCommands (chainId, this.undoIdHistory, this.renderAtIdHistory);
	}
	
	public abstract void render();
	protected abstract Command createControl(Stack<Id> ids, Stack<Integer> stackOfControlIndexInDataBase);
	
	public Boolean addControl(Stack<Id>  id, Stack<Integer> stackOfControlIndexInDataBase) {
		
		if (!isIndexOutOfRange(id)) {
			Command control = createControl(id, stackOfControlIndexInDataBase);
			updateRenderAtId(id.get(0));
			return addOrDelete(ChainControl.ADD, control, id);	
		}
		else { 
			return false;
		}
	}	
	public Boolean delControl(Stack<Id> id) {
		if (!isIndexOutOfRange(id)) {
			return addOrDelete(ChainControl.DELETE, chainOfControls.getControl(0), id);
		}
		else {
			return false;
		}
	}

	public Boolean addOrDelete (ChainControl chainCommand, Command control, Stack<Id> id) {
		ItemAndId<Command> parameter = new ItemAndId<Command>();
		parameter.chainCommand = chainCommand;
		parameter.item = control;
		parameter.id = id;
	
		return chainOfControls.addOrDelete(parameter);
	}
	
	public Boolean isIndexOutOfRange(Stack<Id> controlId) {
		int indexOfControlToAddOrDelete= controlId.get(0).get()[chainOfControls.getDeepnessIndex()];

		if(chainOfControls.getSize()>= indexOfControlToAddOrDelete) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void updateRenderAtId(Id id) {
		Id tempId= new Id();
		int tempControlId= id.get()[1]-1;
		int tempLayerId= id.get()[0];
		if ( tempControlId < 0) { 
			tempControlId = 0; 
			tempLayerId--;
			if (tempLayerId <0) { tempLayerId=0;}
		}
		tempId.set(tempLayerId, tempControlId, id.getGroupId());
	
		IdHistoryParameter tempHistoryParameter= new IdHistoryParameter();
		tempHistoryParameter.set(tempId);

		renderAtIdHistory.setState(tempHistoryParameter);
	}

	public Command getControl(int index) {
		return chainOfControls.getControl(index);
	}
	
	public ChainOfCommands getChainControl() {
		return chainOfControls;
	}
	
	public void setChainControl(ChainOfCommands chain) {
		chainOfControls=chain;
	}	
}