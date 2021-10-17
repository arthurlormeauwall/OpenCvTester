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

	public Control getControl(int index) {
		return chainOfControls.getControl(index);
	}
	
	public void setChainControl(ChainOfControls chain) {
		chainOfControls=chain;
	}
	
	public ChainOfControls getChainControl() {
		return chainOfControls;
	}
	
	public void addControl(Stack<Id>  id, Stack<Integer> stackOfControlIndexInDataBase) {
		Control control = createControl(id, stackOfControlIndexInDataBase);
		addOrDelete(ChainCommand.ADD, control, id);
		updateRenderAtId(id.get(0));
	}
	
	public void delControl(Stack<Id> id) {
		addOrDelete(ChainCommand.DELETE, chainOfControls.getControl(0), id);
	}
	
	public void addOrDelete (ChainCommand chainCommand, Control control, Stack<Id> id) {
		ItemAndId<Control> parameter = new ItemAndId<Control>();
		parameter.chainCommand = chainCommand;
		parameter.item = control;
		parameter.id = id;

		chainOfControls.addOrDelete(parameter);
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
}