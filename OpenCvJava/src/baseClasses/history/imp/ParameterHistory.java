package baseClasses.history.imp;

import baseClasses.history.History;
import baseClasses.history.HistoryParameter;

public class ParameterHistory<T> extends History<T>
{	
	protected Boolean firstUndo; 
	
	public ParameterHistory(){	
	}
	
	public void store() {
		
		HistoryParameter<T> parameter = factory.getNew();
		parameter.set(state.clone());
		undoHistory.push(parameter);
		firstUndo = true;
		clearRedoHistory();
	}
     
	public void undo() {
	    if (!isUndoEmpty() && state!=null){
		   	if (firstUndo) {
			   		 redoHistory.push(undoHistory.peek());
			   		 undoHistory.pop();
			   		 state=undoHistory.peek();
			   		 undoHistory.pop();
			   		 firstUndo=false;  
		   	}
			else {
					 redoHistory.push(state);
					 state=undoHistory.peek();
					 undoHistory.pop();
				 }
			} 
	}
	
	public void redo() {
	    if (!isRedoEmpty() && state!=null)
	    {
	    	undoHistory.push(state);
	    	state=redoHistory.peek();
	    	redoHistory.pop();   	
	    }
	} 
	
	public Boolean isUndoEmpty() { 
		if(firstUndo) {
			if (undoHistory.size() <= 1) {
				return true;  
			}
			else if (undoHistory.size() > 1) {
				return false;
			}
		}
		else {
			if (undoHistory.size() <= 0) {
				return true;  
			}
			else if (undoHistory.size() > 0) {
				return false;
			}
		}
		return null;
	}
}