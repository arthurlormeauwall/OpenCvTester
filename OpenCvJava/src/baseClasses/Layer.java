package baseClasses;

import baseClasses.chain.ChainCommand;
import baseClasses.chain.ChainOfControls;
import baseClasses.chain.ItemAndId;
import baseClasses.history.historyParameters.IdHistoryParameter;
import baseClasses.history.imp.UndoHistory;

import java.util.Stack;

public abstract class Layer extends Control
{
	protected ChainOfControls chainOfControls;
	
	public Layer(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory){
		
		super (id, undoIdHistory, renderAtIdHistory);
		
		Id chainId = new Id(this.id.get());
		chainId.setGroupId(this.id.getGroupId() + 1);
		chainOfControls = new ChainOfControls (chainId, this.undoIdHistory, this.renderAtIdHistory);
	}
	
	public abstract void render();
	protected abstract Control createControl(Stack<Id> ids, Stack<Integer> stackOfControlIndexInDataBase);
	
	public Boolean addControl(Stack<Id>  id, Stack<Integer> stackOfControlIndexInDataBase) {
		
		if (!isIndexOutOfRange(id)) {
			Control control = createControl(id, stackOfControlIndexInDataBase);
			updateRenderAtId(id.get(0));
			return addOrDelete(ChainCommand.ADD, control, id);	
		}
		else { 
			return false;
		}
	}
	
	public Boolean delControl(Stack<Id> id) {
		if (!isIndexOutOfRange(id)) {
			return addOrDelete(ChainCommand.DELETE, chainOfControls.getControl(0), id);
		}
		else {
			return false;
		}
	}

	public Boolean addOrDelete (ChainCommand chainCommand, Control control, Stack<Id> id) {
		ItemAndId<Control> parameter = new ItemAndId<Control>();
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

	public Control getControl(int index) {
		return chainOfControls.getControl(index);
	}
	
	public ChainOfControls getChainControl() {
		return chainOfControls;
	}
	
	public void setChainControl(ChainOfControls chain) {
		chainOfControls=chain;
	}
	
	
	
	
}