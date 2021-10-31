package baseClasses.chain;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.history.historyParameters.ChainHistoryParameter;
import baseClasses.history.imp.ChainHistory;
import baseClasses.history.imp.UndoIdHistory;


public class ChainOfCommands extends Command
{
    protected ChainHistory<ChainAction<Command>> history;
    protected Stack<Command> commands;

    public ChainOfCommands(Id id, UndoIdHistory<Id> undoIdHistory, UndoIdHistory<Id> renderAtIdHistory) { 	
    	super (id, undoIdHistory, renderAtIdHistory);
    	commands = new Stack<Command>();
        history = new ChainHistory<ChainAction<Command>>();
        history.initFactory(new ChainHistoryParameter<Command>());
        history.initState(new ChainHistoryParameter<Command>());
    }
    
    public Boolean addOrDelete(ChainAction<Command> parameter) {
    	int indexOfControlToAddOrDelete= parameter.id.get(0).get()[getDeepnessIndex()];

		if(getSize()>= indexOfControlToAddOrDelete){
			history.setState(new ChainHistoryParameter<Command>(parameter));
	        UpdateUndo();    
	        execute();
	        return true;
		}
    	   	
    	else {
    		return false;
    	}
     }

    public void execute() {
        if (history.getState().getParameter().control == ChainControl.ADD) {
            Id id = history.getState().getParameter().id.get(0);
            Command item = history.getState().getParameter().item;
            addCommand(id, item);
        }

        else if (history.getState().getParameter().control ==  ChainControl.DELETE) {
            history.getState().getParameter().item = delCommand(history.getState().getParameter().id.get(0));
        }
    }

    private void addCommand(Id id, Command command) {
        int index = getCommandIndex(id);

        int lastControl = commands.size() - 1;
        if (index > lastControl + 1) {
            index = lastControl + 1;
            if (index < 0) {
                index = 0;
            }
        }

        if (commands.size() == 0) {
        	commands.push(command);
        }

        else {
        	commands.add(index, command);     
        }
        updateAllId(index);
    }

    private Command delCommand(Id id) {
        int index = getCommandIndex(id);
        int lastControlIndex= commands.size()-1;
        if (index>lastControlIndex) {
        	index=lastControlIndex;
        }
        Command erasedCommand =commands.remove(index);
        updateAllId(index);
        return erasedCommand;
    }
    
    public void updateAllId(int index) {
        int groupDeepnessIndex = getDeepnessIndex();

        for (int i = index; i < commands.size(); i++) {
        	commands.get(i).updateId(groupDeepnessIndex, i);
        }
    }
 
    public void updateId(int groupDeepnessIndex, int newValue) {
        for (int i = 0; i <commands.size(); i++) {
        	commands.get(i).updateId(groupDeepnessIndex, newValue);
        }
    }

    public Boolean undo() {
	        int undoGroupId = undoIdHistory.getState().getParameter().getGroupId();
	        int currentGroupId = id.getGroupId();
	        int undoControlIndex = getCommandIndex(undoIdHistory);
	
	        if (undoGroupId == currentGroupId) {
	            if (!history.isUndoEmpty()) {
	                history.undo();
	                execute();
	                return true;
	            }
	            else {
	            	return false;
	            }
	        }
	        else {
	        	if (!commands.empty())
	        	{
	        		 return commands.get(undoControlIndex).undo();
	        	}  
	        	return false;
	        }		
    }
    
    public Boolean redo() {
	        int undoGroupId = undoIdHistory.getState().getParameter().getGroupId();
	        int currentGroupId = id.getGroupId();
	        int undoControlIndex = getCommandIndex(undoIdHistory);
	
	        if (undoGroupId == currentGroupId) {
	            if (!history.isRedoEmpty()) {
	                history.redo();
	                execute();
	                return true;
	            }
	            else {
	            	return false;
	            }
	        }
	        else {
	            return commands.get(undoControlIndex).redo();
	        }      
    	}

    public void store() {
    	 int undoGroupId = undoIdHistory.getState().getParameter().getGroupId();
         int currentGroupId = id.getGroupId();
         int undoControlIndex = getCommandIndex(undoIdHistory);

         if (undoGroupId == currentGroupId) {        	 
        	 history.store();
         }
         
         else if (undoControlIndex >= 0 && undoControlIndex < commands.size()){
        	 commands.get(undoControlIndex).store();
         }
    }
    
    public int getCommandIndex(Id id) {
        int groupDeepnessIndex = getDeepnessIndex();

        int controlIndex = id.get()[groupDeepnessIndex];
        return controlIndex;
    }

    public int getCommandIndex(UndoIdHistory<Id> id) {
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
    	newChainControl.setCommandChain(commands);
    	newChainControl.setHistory(history);
    	newChainControl.setBypass(isBypass);
    	
    	return newChainControl;
    }

    public Command getCommand(int index){	
    	return commands.get(index);
    }

    public Stack<Command> getCommandChain() {      
    	return commands;
    }
    
    public void setCommandChain(Stack<Command> commandChain) {   	
    	commands=commandChain;
    }
    
    public ChainHistory<ChainAction<Command>> getHistory(){   	
    	return history;
    }
    
    public void setHistory(ChainHistory<ChainAction<Command>> history) {  	
    	this.history=history;
    }

    public int getSize() {
        return commands.size();
    } 
}
