package baseClasses;

import baseClasses.chain.ChainCommand;
import baseClasses.chain.ChainOfControls;
import baseClasses.chain.ItemAndId;
import baseClasses.history.historyParameters.IdHistoryParameter;
import baseClasses.history.imp.UndoHistory;

import java.util.Stack;

public abstract class Layer extends Control
{
	protected ChainOfControls m_chainOfControls;
	
	public Layer(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory){
		
		super (id, undoIdHistory, renderAtIdHistory);
		int chainGroup = m_id.getGroupId() + 1;
	
		Id chainId = new Id();
		chainId.setGroupId(chainGroup);
		m_chainOfControls = new ChainOfControls (chainId, m_undoIdHistory, m_renderAtIdHistory);
	
		m_chainOfControls.getId().set(m_id);
		m_chainOfControls.getId().setGroupId(m_id.getGroupId() + 1);
	}
	
	public abstract void render();
	public abstract Control createControl(Stack<Id> ids, Stack<Integer> stackOfControlIndexInDataBase);

	public Control getControl(int index) {
		return m_chainOfControls.getControl(index);
	}
	
	public void setChainControl(ChainOfControls chain) {
		m_chainOfControls=chain;
	}
	
	public ChainOfControls getChainControl() {
		return m_chainOfControls;
	}
	
	public void addControl(Stack<Id>  id, Stack<Integer> stackOfControlIndexInDataBase) {
		Control control = createControl(id, stackOfControlIndexInDataBase);
		addOrDelete(ChainCommand.ADD, control, id);
		updateRenderAtId(id.get(0));
	}
	
	public void delControl(Stack<Id> id) {
		addOrDelete(ChainCommand.DELETE, m_chainOfControls.getControl(0), id);
	}
	
	public void addOrDelete (ChainCommand chainCommand, Control control, Stack<Id> id) {
		ItemAndId<Control> parameter = new ItemAndId<Control>();
		parameter.m_chainCommand = chainCommand;
		parameter.m_control = control;
		parameter.m_id = id;

		m_chainOfControls.addOrDelete(parameter);
	}
	
	public void updateRenderAtId(Id id) {
		Id tempId= new Id();
		int tempControlId= id.get()[1]-1;
		int tempLayerId= id.get()[0];
		if ( tempControlId < 0) { tempControlId = 0; }
		tempId.set(tempLayerId, tempControlId, id.getGroupId());
	
		IdHistoryParameter tempHistoryParameter= new IdHistoryParameter();
		tempHistoryParameter.set(tempId);

		m_renderAtIdHistory.setState(tempHistoryParameter);
	}
};