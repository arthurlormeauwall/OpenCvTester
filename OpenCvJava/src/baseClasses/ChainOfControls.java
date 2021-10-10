package baseClasses;

import java.util.Stack;

import baseClasses.enums_structs.ChainCommand;
import baseClasses.enums_structs.ItemAndId;
import baseClasses.history.historyParameters.ChainHistoryParameter;
import baseClasses.history.imp.ChainHistory;
import baseClasses.history.imp.UndoHistory;


public class ChainOfControls extends Control
{

    public ChainOfControls(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) { 	
    	super (id, undoIdHistory, renderAtIdHistory);
    	m_controls = new Stack<Control>();
        m_history = new ChainHistory<ItemAndId<Control>>();
        m_history.initFactory(new ChainHistoryParameter<Control>());
        m_history.initState(new ChainHistoryParameter<Control>());
    }


    public Control getControl(int index){	
    	return m_controls.get(index);
    }

    public void addControl(Id id, Control control) {
        int index = getControlIndex(id);

        int lastControl = m_controls.size() - 1;
        if (index > lastControl + 1) {
            index = lastControl + 1;
            if (index < 0) {
                index = 0;
            }
        }

        if (m_controls.size() == 0) {
            m_controls.push(control);
        }

        else {
        	m_controls.add(index, control);     
        }

        updateAllId(index);
    }

    public Control delControl(Id id) {
        int index = getControlIndex(id);
        int lastControlIndex= m_controls.size()-1;
        if (index>lastControlIndex) {
        	index=lastControlIndex;
        }
        Control erasedControl =m_controls.remove(index);
        updateAllId(index);
        return erasedControl;
    }

    public void addOrDelete(ItemAndId<Control> parameter) {
        m_history.setState(new ChainHistoryParameter<Control>(parameter));
        UpdateUndo();
        compute();
     }

    public Stack<Control> getControlsChain() {      
    	return m_controls;
    }
    
    public void setControlsChain(Stack<Control> controlChain) {   	
    	m_controls=controlChain;
    }
    
    public ChainHistory<ItemAndId<Control>> getHistory(){   	
    	return m_history;
    }
    
    public void setHistory(ChainHistory<ItemAndId<Control>> history) {  	
    	m_history=history;
    }
    
    public void compute() {
        if (m_history.getState().getParameter().m_chainCommand == ChainCommand.ADD) {
            Id id = m_history.getState().getParameter().m_id.get(0);
            Control item = m_history.getState().getParameter().m_control;
            addControl(id, item);
        }

        else if (m_history.getState().getParameter().m_chainCommand ==  ChainCommand.DELETE) {
            m_history.getState().getParameter().m_control = delControl(m_history.getState().getParameter().m_id.get(0));
        }
    }

    public int getSize() {
        return m_controls.size();
    }

    public int getControlIndex(Id id) {
        int groupDeepnessIndex = getDeepnessIndex();

        int controlIndex = id.get()[groupDeepnessIndex];
        return controlIndex;
    }

    public int getControlIndex(UndoHistory<Id> id) {
        int groupDeepnessIndex = getDeepnessIndex();
        int controlIndex = id.getState().getParameter().get()[groupDeepnessIndex];
        return controlIndex;
    }
    
    public int getDeepnessIndex() {
        int currentGroupId = m_id.getGroupId();
        int groupDeepnessIndex = (currentGroupId - 1) / 2;
        
        /* if we are in a "masked-layer chain" (groupId at '1') we should get layer index 
        but if we are in a "control-chain" (groupId at '3')
        we should get control index */
        
        return groupDeepnessIndex;
    }

    public void updateAllId(int index) {
        int groupDeepnessIndex = getDeepnessIndex();

        for (int i = index; i < m_controls.size(); i++) {
            m_controls.get(i).updateId(groupDeepnessIndex, i);
        }
    }
    
    public void updateId(int groupDeepnessIndex, int newValue) {
        for (int i = 0; i < m_controls.size(); i++) {
            m_controls.get(i).updateId(groupDeepnessIndex, newValue);
        }
    }


    public Boolean undo() {
	        int undoGroupId = m_undoIdHistory.getState().getParameter().getGroupId();
	        int currentGroupId = m_id.getGroupId();
	        int undoControlIndex = getControlIndex(m_undoIdHistory);
	
	        if (undoGroupId == currentGroupId) {
	            if (!m_history.isUndoEmpty()) {
	                m_history.undo();
	                compute();
	                return true;
	            }
	            else {
	            	return false;
	            }
	        }
	        else {
	            return m_controls.get(undoControlIndex).undo();
	        } 	
    }
    
    public Boolean redo() {
	        int undoGroupId = m_undoIdHistory.getState().getParameter().getGroupId();
	        int currentGroupId = m_id.getGroupId();
	        int undoControlIndex = getControlIndex(m_undoIdHistory);
	
	        if (undoGroupId == currentGroupId) {
	            if (!m_history.isRedoEmpty()) {
	                m_history.redo();
	                compute();
	                return true;
	            }
	            else {
	            	return false;
	            }
	        }
	        else {
	            return m_controls.get(undoControlIndex).redo();
	        }      
    	}
   

    public void store() {
    	 int undoGroupId = m_undoIdHistory.getState().getParameter().getGroupId();
         int currentGroupId = m_id.getGroupId();
         int undoControlIndex = getControlIndex(m_undoIdHistory);

         if (undoGroupId == currentGroupId) {
        	 
        	 m_history.store();
         }
         
         else {
             m_controls.get(undoControlIndex).store();
         }
    }
    
    public ChainOfControls clone() {	
    	Id newId= new Id();
    	
    	ChainOfControls newChainControl = new ChainOfControls(newId, m_undoIdHistory, m_renderAtIdHistory);
    	
    	newChainControl.setId(m_id);
    	newChainControl.setControlsChain(m_controls);
    	newChainControl.setHistory(m_history);
    	newChainControl.setBypass(m_isBypass);
    	
    	return newChainControl;
    }

    protected ChainHistory<ItemAndId<Control>> m_history;
    protected Stack<Control> m_controls;
};
