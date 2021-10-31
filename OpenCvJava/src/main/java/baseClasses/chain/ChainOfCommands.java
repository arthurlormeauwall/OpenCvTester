package baseClasses.chain;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.history.historyParameters.ChainHistoryParameter;
import baseClasses.history.imp.ChainHistory;
import baseClasses.history.imp.UndoIdHistory;


public class ChainOfCommands extends Command
{
    protected ChainHistory<ItemAndId<Command>> history;
    protected Stack<Command> controls;

    public ChainOfCommands(Id id, UndoIdHistory<Id> undoIdHistory, UndoIdHistory<Id> renderAtIdHistory) { 	
    	super (id, undoIdHistory, renderAtIdHistory);
    	controls = new Stack<Command>();
        history = new ChainHistory<ItemAndId<Command>>();
        history.initFactory(new ChainHistoryParameter<Command>());
        history.initState(new ChainHistoryParameter<Command>());
    }
    
    public Boolean addOrDelete(ItemAndId<Command> parameter) {
    	int indexOfControlToAddOrDelete= parameter.id.get(0).get()[getDeepnessIndex()];

		if(getSize()>= indexOfControlToAddOrDelete){
			history.setState(new ChainHistoryParameter<Command>(parameter));
	        UpdateUndo();    
	        compute();
	        return true;
		}
    	   	
    	else {
    		return false;
    	}
     }

    public void compute() {
        if (history.getState().getParameter().chainCommand == ChainControl.ADD) {
            Id id = history.getState().getParameter().id.get(0);
            Command item = history.getState().getParameter().item;
            addControl(id, item);
        }

        else if (history.getState().getParameter().chainCommand ==  ChainControl.DELETE) {
            history.getState().getParameter().item = delControl(history.getState().getParameter().id.get(0));
        }
    }

    private void addControl(Id id, Command control) {
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

    private Command delControl(Id id) {
        int index = getControlIndex(id);
        int lastControlIndex= controls.size()-1;
        if (index>lastControlIndex) {
        	index=lastControlIndex;
        }
        Command erasedControl =controls.remove(index);
        updateAllId(index);
        return erasedControl;
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
         
         else if (undoControlIndex >= 0 && undoControlIndex < controls.size()){
             controls.get(undoControlIndex).store();
         }
    }
    
    public int getControlIndex(Id id) {
        int groupDeepnessIndex = getDeepnessIndex();

        int controlIndex = id.get()[groupDeepnessIndex];
        return controlIndex;
    }

    public int getControlIndex(UndoIdHistory<Id> id) {
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

    public ChainOfCommands clone() {	
    	Id newId= new Id();
    	
    	ChainOfCommands newChainControl = new ChainOfCommands(newId, undoIdHistory, renderAtIdHistory);
    	
    	newChainControl.setId(id);
    	newChainControl.setControlsChain(controls);
    	newChainControl.setHistory(history);
    	newChainControl.setBypass(isBypass);
    	
    	return newChainControl;
    }

    public Command getControl(int index){	
    	return controls.get(index);
    }

    public Stack<Command> getControlsChain() {      
    	return controls;
    }
    
    public void setControlsChain(Stack<Command> controlChain) {   	
    	controls=controlChain;
    }
    
    public ChainHistory<ItemAndId<Command>> getHistory(){   	
    	return history;
    }
    
    public void setHistory(ChainHistory<ItemAndId<Command>> history) {  	
    	this.history=history;
    }

    public int getSize() {
        return controls.size();
    } 
}
