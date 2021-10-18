package baseClasses.chain;

import java.util.Stack;

import baseClasses.Control;
import baseClasses.Id;
import baseClasses.history.historyParameters.ChainHistoryParameter;
import baseClasses.history.imp.ChainHistory;
import baseClasses.history.imp.UndoHistory;


public class ChainOfControls extends Control
{
    protected ChainHistory<ItemAndId<Control>> history;
    protected Stack<Control> controls;

    public ChainOfControls(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) { 	
    	super (id, undoIdHistory, renderAtIdHistory);
    	controls = new Stack<Control>();
        history = new ChainHistory<ItemAndId<Control>>();
        history.initFactory(new ChainHistoryParameter<Control>());
        history.initState(new ChainHistoryParameter<Control>());
    }


    public Control getControl(int index){	
    	return controls.get(index);
    }

    private void addControl(Id id, Control control) {
        int index = getControlIndex(id);

        int lastControl = controls.size() - 1;
        if (index > lastControl + 1) {
            index = lastControl + 1;
            if (index < 0) {
                index = 0;
            }
        }

        if (controls.size() == 0) {
            controls.push(control);
        }

        else {
        	controls.add(index, control);     
        }

        updateAllId(index);
    }

    private Control delControl(Id id) {
        int index = getControlIndex(id);
        int lastControlIndex= controls.size()-1;
        if (index>lastControlIndex) {
        	index=lastControlIndex;
        }
        Control erasedControl =controls.remove(index);
        updateAllId(index);
        return erasedControl;
    }

    public void addOrDelete(ItemAndId<Control> parameter) {
        history.setState(new ChainHistoryParameter<Control>(parameter));
        UpdateUndo();    
        compute();
     }

    public Stack<Control> getControlsChain() {      
    	return controls;
    }
    
    public void setControlsChain(Stack<Control> controlChain) {   	
    	controls=controlChain;
    }
    
    public ChainHistory<ItemAndId<Control>> getHistory(){   	
    	return history;
    }
    
    public void setHistory(ChainHistory<ItemAndId<Control>> history) {  	
    	this.history=history;
    }
    
    public void compute() {
        if (history.getState().getParameter().chainCommand == ChainCommand.ADD) {
            Id id = history.getState().getParameter().id.get(0);
            Control item = history.getState().getParameter().item;
            addControl(id, item);
        }

        else if (history.getState().getParameter().chainCommand ==  ChainCommand.DELETE) {
            history.getState().getParameter().item = delControl(history.getState().getParameter().id.get(0));
        }
    }

    public int getSize() {
        return controls.size();
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
        int currentGroupId = id.getGroupId();
        int groupDeepnessIndex = (currentGroupId - 1) / 2;
        
        /* if we are in a "masked-layer chain" (groupId at '1') we should get layer index 
        but if we are in a "control-chain" (groupId at '3')
        we should get control index */
        
        return groupDeepnessIndex;
    }

    public void updateAllId(int index) {
        int groupDeepnessIndex = getDeepnessIndex();

        for (int i = index; i < controls.size(); i++) {
            controls.get(i).updateId(groupDeepnessIndex, i);
        }
    }
    
    public void updateId(int groupDeepnessIndex, int newValue) {
        for (int i = 0; i < controls.size(); i++) {
            controls.get(i).updateId(groupDeepnessIndex, newValue);
        }
    }


    public Boolean undo() {
	        int undoGroupId = undoIdHistory.getState().getParameter().getGroupId();
	        int currentGroupId = id.getGroupId();
	        int undoControlIndex = getControlIndex(undoIdHistory);
	
	        if (undoGroupId == currentGroupId) {
	            if (!history.isUndoEmpty()) {
	                history.undo();
	                compute();
	                return true;
	            }
	            else {
	            	return false;
	            }
	        }
	        else {
	        	if (!controls.empty())
	        	{
	        		 return controls.get(undoControlIndex).undo();
	        	}  
	        	return false;
	        }
				
    }
    
    public Boolean redo() {
	        int undoGroupId = undoIdHistory.getState().getParameter().getGroupId();
	        int currentGroupId = id.getGroupId();
	        int undoControlIndex = getControlIndex(undoIdHistory);
	
	        if (undoGroupId == currentGroupId) {
	            if (!history.isRedoEmpty()) {
	                history.redo();
	                compute();
	                return true;
	            }
	            else {
	            	return false;
	            }
	        }
	        else {
	            return controls.get(undoControlIndex).redo();
	        }      
    	}
   

    public void store() {
    	 int undoGroupId = undoIdHistory.getState().getParameter().getGroupId();
         int currentGroupId = id.getGroupId();
         int undoControlIndex = getControlIndex(undoIdHistory);

         if (undoGroupId == currentGroupId) {
        	 
        	 history.store();
         }
         
         else {
             controls.get(undoControlIndex).store();
         }
    }
    
    public ChainOfControls clone() {	
    	Id newId= new Id();
    	
    	ChainOfControls newChainControl = new ChainOfControls(newId, undoIdHistory, renderAtIdHistory);
    	
    	newChainControl.setId(id);
    	newChainControl.setControlsChain(controls);
    	newChainControl.setHistory(history);
    	newChainControl.setBypass(isBypass);
    	
    	return newChainControl;
    }
}
