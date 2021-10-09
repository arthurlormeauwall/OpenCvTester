package baseClasses;

import java.util.Stack;

import baseClasses.enums_structs.ChainCommand;
import baseClasses.enums_structs.ItemAndId;
import baseClasses.history.historyParameters.ChainHistoryParameter;
import baseClasses.history.imp.ChainHistory;
import baseClasses.history.imp.UndoHistory;


public class ChainControl extends Control
{

    public ChainControl(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) {
    	super (id, undoIdHistory, renderAtIdHistory);
    	m_controls = new Stack<Control>();
        m_history = new ChainHistory<ItemAndId<Control>>();
        m_history.setFactory(new ChainHistoryParameter<Control>());
        m_history.setState(new ChainHistoryParameter<Control>());
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

    public void setChain(ItemAndId<Control> p) {

        m_history.setLast(new ChainHistoryParameter<Control>(p));
        UpdateUndo();
        compute();
     }

    public Stack<Control> getControlsChain() {
        return m_controls;
    }
    
    public void setControlsChain(Stack<Control> p) {
    	m_controls=p;
    }
    public ChainHistory<ItemAndId<Control>> getHistory(){
    	return m_history;
    }
    public void setHistory(ChainHistory<ItemAndId<Control>> p) {
    	m_history=p;
    }
    
    public void compute() {
    	
        if (m_history.getLast().getParameter().s_chainCommand == ChainCommand.ADD) {
            Id id = m_history.getLast().getParameter().s_id.get(0);
            Control item = m_history.getLast().getParameter().s_control;
            addControl(id, item);
        }

        else if (m_history.getLast().getParameter().s_chainCommand ==  ChainCommand.DELETE) {
            m_history.getLast().getParameter().s_control = delControl(m_history.getLast().getParameter().s_id.get(0));
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
        int controlIndex = id.getLast().getParameter().get()[groupDeepnessIndex];
        return controlIndex;
    }
    
    public int getDeepnessIndex() {
        int currentGroupId = m_id.getGroupId();
        int groupDeepnessIndex = (currentGroupId - 1) / 2;
        
        /* if we are in a "masked-layer chain" (groupId at '1') we should get "layer"
        so (*m_undoIdHistory).getLast()->get()[0] (with (1-1)/2 = 0) but if we are in a "control-chain" (groupId at '3')
        we should get "control" so (*m_undoIdHistory).getLast()->get()[1] (with (3-1)/2 = 1)
        and so on with deeper layer of nested "Layer/chain/control"  */
        
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


	        int undoGroupId = m_undoIdHistory.getLast().getParameter().getGroupId();
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

	        int undoGroupId = m_undoIdHistory.getLast().getParameter().getGroupId();
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
    	 int undoGroupId = m_undoIdHistory.getLast().getParameter().getGroupId();
         int currentGroupId = m_id.getGroupId();
         int undoControlIndex = getControlIndex(m_undoIdHistory);

         if (undoGroupId == currentGroupId) {
        	 
        	 m_history.store();
         }
         
         else {
             m_controls.get(undoControlIndex).store();
         }
    }
    
    public ChainControl clone() {
    	
    	Id tempId= new Id();
    	
    	ChainControl temp = new ChainControl(tempId, m_undoIdHistory, m_renderAtIdHistory);
    	
    	temp.setId(m_id);
    	temp.setControlsChain(m_controls);
    	temp.setHistory(m_history);
    	temp.setBypass(bypass);
    	
    	return temp;
    }

    protected ChainHistory<ItemAndId<Control>> m_history;
    protected Stack<Control> m_controls;
};
