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

    public void addCommand(Command command, String indexType) {
        int index = command.getFilterOrLayerIndex(indexType);

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
        int index = command.getFilterOrLayerIndex(indexType);
        int lastControlIndex= commands.size()-1;
        if (index>lastControlIndex) {
        	index=lastControlIndex;
        }
        Command erasedCommand =commands.remove(index);
        updateAllId(index, indexType);
        return erasedCommand;
    }
    
    public void updateAllId(int index, String groupDeepnessIndex) {
        for (int i = index; i < commands.size(); i++) {
        	commands.get(i).updateId(groupDeepnessIndex, i);
        }
    }
 
    public void updateId(String groupDeepnessIndex, int newValue) {
        for (int i = 0; i <commands.size(); i++) {
        	commands.get(i).updateId(groupDeepnessIndex, newValue);
        }
    }
    
//    public int getCommandIndex(Id id,  String groupDeepnessIndex) {
//        int commandIndex = id.get(groupDeepnessIndex);
//        return commandIndex;
//    }

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
