package baseClasses.chain;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.history.imp.IdHistory;


public class ChainOfCommands
{
    protected Stack<Command> commands;
    Id id;

    public ChainOfCommands(Id id) { 
    	this.id=id;
    	commands = new Stack<Command>();
    }
    
    

    public void addCommand(Id id, Command filter) {
        int index = getCommandIndex(id);

        int lastControl = commands.size() - 1;
        if (index > lastControl + 1) {
            index = lastControl + 1;
            if (index < 0) {
                index = 0;
            }
        }

        if (commands.size() == 0) {
        	commands.push(filter);
        }

        else {
        	commands.add(index, filter);     
        }
        updateAllId(index);
    }

    public Command delCommand(Id id) {
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
    
    public int getCommandIndex(Id id) {
        int groupDeepnessIndex = getDeepnessIndex();

        int controlIndex = id.get()[groupDeepnessIndex];
        return controlIndex;
    }

    public int getCommandIndex(IdHistory<Id> id) {
        int groupDeepnessIndex = getDeepnessIndex();
        int controlIndex = id.getState().getParameter().get()[groupDeepnessIndex];
        return controlIndex;
    }
    
    public int getDeepnessIndex() {
        int currentGroupId = id.getGroupId();
        int groupDeepnessIndex = (currentGroupId - 1) / 2;
        
        /* if we are in a "layer chain" (groupId at '1') we should get layer index 
        but if we are in a "filter chain" (groupId at '3')
        we should get filter index */
        
        return groupDeepnessIndex;
    }


    public ChainOfCommands clone() {	
    	
    	ChainOfCommands newChainControl = new ChainOfCommands(id.clone());
    	newChainControl.setCommandChain(commands);	
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


    public int getSize() {
        return commands.size();
    } 
}
