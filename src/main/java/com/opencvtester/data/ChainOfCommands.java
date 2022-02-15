package com.opencvtester.data;

import java.util.Stack;

public class ChainOfCommands extends Command 
{
	protected Stack<Command> commands;
    protected String indexType;

	/*
	 * CONSTRUCTOR & INITS
	 */
    public ChainOfCommands(String indexType, int layerIndex, int filterIndex) { 
    	super(layerIndex, filterIndex);
    	commands = new Stack<Command>();
    	this.indexType=indexType;
    }
      
    /*
	 * GETTERS & SETTERS
	 */
    public void setCommandChain(Stack<Command> commandChain) {   	
    	commands=commandChain;
    }

    public int getSize() {
        return commands.size();
    }

	public Stack<Command> getChain() {
		return commands;
	} 
	
    public Command getCommand(int index){
    	if (index>=0 && index < commands.size()) {
    		return commands.get(index);
    	}
    	else {
    		return null;
    	}
    }

    public void add(Command command) {  
    	int index = command.getIndex(indexType);
        if (commands.size() == 0) {
        	commands.push(command);
        }
        else {
        	index=Math.max(Math.min(commands.size(), index),0);
        	commands.add(index, command);     
        	updateAllId(index);
        }      
    }

    public Command delete(int index) {
        int lastCommandIndex= commands.size()-1;
        
        index=Math.min(lastCommandIndex,index);

        Command erasedCommand =commands.remove(index);
        updateAllId(index);
        return erasedCommand;
    }
    
    public void updateAllId(int index) {
    	int commandSize=commands.size();
        for (int i = index; i < commandSize; i++) {
        	commands.get(i).setIndex(indexType, i);
        }
    }
}
