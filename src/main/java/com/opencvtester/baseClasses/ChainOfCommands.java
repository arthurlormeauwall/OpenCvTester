package com.opencvtester.baseClasses;

import java.util.Stack;


public class ChainOfCommands
{
    protected Stack<Command> commands;
    Id id;

    public ChainOfCommands(Id id) { 
    	this.id=id;
    	commands = new Stack<Command>();
    }

    public void addCommand(Id id, Command filter, int groupDeepnessIndex) {
        int index = getCommandIndex(id, groupDeepnessIndex);

        int lastControl = commands.size() - 1;
        
        if (commands.size() == 0) {
        	commands.push(filter);
        }

        else {
        	if (index > lastControl + 1) {
                index = lastControl + 1;
                if (index < 0) {
                    index = 0;
                }              
            }
        	commands.add(index, filter);     
        	 updateAllId(index, groupDeepnessIndex);
        }      
    }

    public Command delCommand(Id id, int groupDeepnessIndex) {
        int index = getCommandIndex(id, groupDeepnessIndex);
        int lastControlIndex= commands.size()-1;
        if (index>lastControlIndex) {
        	index=lastControlIndex;
        }
        Command erasedCommand =commands.remove(index);
        updateAllId(index, groupDeepnessIndex);
        return erasedCommand;
    }
    
    public void updateAllId(int index, int groupDeepnessIndex) {
        for (int i = index; i < commands.size(); i++) {
        	commands.get(i).updateId(groupDeepnessIndex, i);
        }
    }
 
    public void updateId(int groupDeepnessIndex, int newValue) {
        for (int i = 0; i <commands.size(); i++) {
        	commands.get(i).updateId(groupDeepnessIndex, newValue);
        }
    }
    
    public int getCommandIndex(Id id,  int groupDeepnessIndex) {
        int controlIndex = id.get()[groupDeepnessIndex];
        return controlIndex;
    }

    public ChainOfCommands clone() {		
    	ChainOfCommands newChainControl = new ChainOfCommands(id.clone());
    	newChainControl.setCommandChain(commands);	
    	return newChainControl;
    }

    public Command getCommand(int index){
    	if (index>=0 && index < commands.size()) {
    		return commands.get(index);
    	}
    	else {
    		return null;
    	}
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

	public Stack<Command> getChain() {
		return commands;
	} 
}
