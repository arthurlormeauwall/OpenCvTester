package baseClasses;

import baseClasses.enums_structs.ChainCommand;
import baseClasses.enums_structs.ItemAndId;
import baseClasses.history.IdHistoryParameter;
import baseClasses.history.imp.UndoHistory;

import java.util.Stack;

public abstract class Layer extends Control
{

	public Layer(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory){
		
		super (id, undoIdHistory, renderAtIdHistory);
		int chainGroup = m_id.getGroupId() + 1;

		Id chainId = new Id();
		chainId.setGroupId(chainGroup);
		m_controls = new ChainControl (chainId, m_undoIdHistory, m_renderAtIdHistory);

		m_controls.getId().set(m_id);
		m_controls.getId().setGroupId(m_id.getGroupId() + 1);
	}
	
	public abstract void render();
	public abstract Control createControl(Stack<Id> ids, Stack<Integer> controlNumber);

	public Control getControl(int index) {

		return m_controls.getControl(index);
	}
	
	public void setChainControl(ChainControl p)
	{
		m_controls=p;
	}
	public ChainControl getChainControl() {
		return m_controls;
	}
	
	public void addControl(Stack<Id>  id, Stack<Integer> controlNumber) {

		Control control = createControl(id, controlNumber);
		setChain(ChainCommand.ADD, control, id);
		updateRenderAtId(id.get(0));
	}
	public void delControl(Stack<Id> id) {

		setChain(ChainCommand.DELETE, m_controls.getControl(0), id);
	}
	
	public void setChain (ChainCommand chainCommand, Control control, Stack<Id> id) {
		ItemAndId<Control> parameter = new ItemAndId<Control>();
		parameter.s_chainCommand = chainCommand;
		parameter.s_control = control;
		parameter.s_id = id;

		m_controls.setChain(parameter);
	}
	
	public void updateRenderAtId(Id id) {

		Id tempId= new Id();
		int tempControlId= id.get()[1]-1;
		int tempLayerId= id.get()[0];
		if ( tempControlId < 0) { tempControlId = 0; }
		tempId.set(tempLayerId, tempControlId, id.getGroupId());
	
		IdHistoryParameter temp= new IdHistoryParameter();
		temp.set(tempId);

		m_renderAtIdHistory.setLast(temp);
	}



	protected ChainControl m_controls;
	
	};