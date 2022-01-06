package com.opencvtester.baseClasses;

import java.util.Stack;

public class ChainOfCommands
{
    protected Stack<Command> commands;
    protected String indexType;

    public ChainOfCommands(String indexType) { 
    	commands = new Stack<Command>();
    	this.indexType=indexType;
    }

    public void addCommand(Command command, int index) {

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
        	 updateAllId(index);
        }      
    }

    public Command delCommand(int index) {
//        int index = command.getIndex(indexType);
        int lastCommandIndex= commands.size()-1;
        if (index>lastCommandIndex) {
        	index=lastCommandIndex;
        }
        Command erasedCommand =commands.remove(index);
        updateAllId(index);
        return erasedCommand;
    }
    
    public void updateAllId(int index) {
        for (int i = index; i < commands.size(); i++) {
        	commands.get(i).setIndex(indexType, i);
        }
    }
 
    public void updateId(int newValue) {
        for (int i = 0; i <commands.size(); i++) {
        	commands.get(i).setIndex(indexType, newValue);
        }
    }

    public ChainOfCommands clone() {		
    	ChainOfCommands newChain = new ChainOfCommands(indexType);
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
