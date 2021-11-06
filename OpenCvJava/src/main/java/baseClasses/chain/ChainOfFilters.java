package baseClasses.chain;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.filter.Filter;
import baseClasses.history.imp.IdHistory;


public class ChainOfFilters extends Command
{
    protected ChainAction<Filter> state;
    protected Stack<Filter> filters;

    public ChainOfFilters(Id id, IdHistory<Id> renderAtIdHistory) { 	
    	super (id, renderAtIdHistory);
    	filters = new Stack<Filter>();
    }
    
    public Filter addOrDelete(ChainAction<Filter> parameter) {
    	int indexOfControlToAddOrDelete= parameter.id.get(0).get()[getDeepnessIndex()];

		if(getSize()>= indexOfControlToAddOrDelete){
			state=parameter;
	        UpdateUndo();    
	        execute();
	        return parameter.item;
		}
    	   	
    	else {
    		return null;
    	}
     }

    public void execute() {
        if (state.control == ChainControl.ADD) {
            Id id = state.id.get(0);
            Filter item = state.item;
            addCommand(id, item);
        }

        else if (state.control ==  ChainControl.DELETE) {
            state.item = delCommand(state.id.get(0));
        }
    }

    private void addCommand(Id id, Filter filter) {
        int index = getCommandIndex(id);

        int lastControl = filters.size() - 1;
        if (index > lastControl + 1) {
            index = lastControl + 1;
            if (index < 0) {
                index = 0;
            }
        }

        if (filters.size() == 0) {
        	filters.push(filter);
        }

        else {
        	filters.add(index, filter);     
        }
        updateAllId(index);
    }

    private Filter delCommand(Id id) {
        int index = getCommandIndex(id);
        int lastControlIndex= filters.size()-1;
        if (index>lastControlIndex) {
        	index=lastControlIndex;
        }
        Filter erasedFilter =filters.remove(index);
        updateAllId(index);
        return erasedFilter;
    }
    
    public void updateAllId(int index) {
        int groupDeepnessIndex = getDeepnessIndex();

        for (int i = index; i < filters.size(); i++) {
        	filters.get(i).updateId(groupDeepnessIndex, i);
        }
    }
 
    public void updateId(int groupDeepnessIndex, int newValue) {
        for (int i = 0; i <filters.size(); i++) {
        	filters.get(i).updateId(groupDeepnessIndex, newValue);
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

    public ChainOfFilters clone() {	
    	Id newId= new Id();
    	
    	ChainOfFilters newChainControl = new ChainOfFilters(newId, renderAtIdHistory);
    	
    	newChainControl.setId(id);
    	newChainControl.setCommandChain(filters);
    	newChainControl.setState(state);
    	newChainControl.setBypass(isBypass);
    	
    	return newChainControl;
    }

    public Filter getFilter(int index){	
    	return filters.get(index);
    }

    public Stack<Filter> getCommandChain() {      
    	return filters;
    }
    
    public void setCommandChain(Stack<Filter> commandChain) {   	
    	filters=commandChain;
    }
    
    public ChainAction<Filter> getState(){   	
    	return state;
    }
    
    public void setState(ChainAction<Filter> newState) {  	
    	this.state=newState;
    }

    public int getSize() {
        return filters.size();
    } 
}
