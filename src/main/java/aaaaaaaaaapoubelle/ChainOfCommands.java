package aaaaaaaaaapoubelle;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.opencvtester.data.interfaces.IndexInterface;

public class ChainOfCommands 
{
	protected List<IndexInterface> commands;
    protected String indexType;

	/*
	 * CONSTRUCTOR & INITS
	 */
    public ChainOfCommands(String indexType) { 
    	commands = new ArrayList<IndexInterface>();
    	this.indexType=indexType;
    }
      
    /*
	 * GETTERS & SETTERS
	 */
    public void setCommandChain(Stack<IndexInterface> commandChain) {   	
    	commands=commandChain;
    }

    public int getSize() {
        return commands.size();
    }

	public List<IndexInterface> getChain() {
		return commands;
	} 
	
    public IndexInterface get(int index){
    	if (index>=0 && index < commands.size()) {
    		return commands.get(index);
    	}
    	else {
    		return null;
    	}
    }

    public void add(IndexInterface command) {  
    	int index = command.getIndex(indexType);
    	index=Math.max(Math.min(commands.size(), index),0);
    	commands.add(index, command);     
    	updateAllId(index);
            
    }

    public IndexInterface delete(int index) {
        int lastCommandIndex= commands.size()-1;
        
        index=Math.min(lastCommandIndex,index);

        IndexInterface erasedCommand =commands.remove(index);
        updateAllId(index);
        return erasedCommand;
    }
    
    public void updateAllId(int index) {
    	int commandSize=commands.size();
        for (int i = index; i < commandSize; i++) {
        	commands.get(i).setIndex(indexType, i);
        }
    }
    
    public List<IndexInterface> get(){
    	return commands;
    }

	
}
