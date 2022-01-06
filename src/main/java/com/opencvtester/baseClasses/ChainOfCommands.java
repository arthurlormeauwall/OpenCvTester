package com.opencvtester.baseClasses;

import java.util.Stack;


public class ChainOfCommands
{
    protected Stack<Command> commands;

    public ChainOfCommands() { 
    	commands = new Stack<Command>();
    }

    public void addCommand(Command command, String indexType) {
        int index = command.getIndex(indexType);

        int lastControl = commands.size() - 1;
        
        if (commands.size() == 0) {
        	commands.push(command);
        }

        else {
        	if (index > lastControl + 1) {
                index = lastControl + 1;
                if (index < 0) {
                    index = 0;
                }              
            }
        	commands.add(index, command);     
        	 updateAllId(index, indexType);
        }      
    }

    public Command delCommand(Command command, String indexType) {
        int index = command.getIndex(indexType);
        int lastCommandIndex= commands.size()-1;
        if (index>lastCommandIndex) {
        	index=lastCommandIndex;
        }
        Command erasedCommand =commands.remove(index);
        updateAllId(index, indexType);
        return erasedCommand;
    }
    
    public void updateAllId(int index, String indexType) {
        for (int i = index; i < commands.size(); i++) {
        	commands.get(i).updateId(indexType, i);
        }
    }
 
    public void updateId(String indexType, int newValue) {
        for (int i = 0; i <commands.size(); i++) {
        	commands.get(i).updateId(indexType, newValue);
        }
    }

    public ChainOfCommands clone() {		
    	ChainOfCommands newChain = new ChainOfCommands();
    	newChain.setCommandChain(commands);	
    	return newChain;
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
