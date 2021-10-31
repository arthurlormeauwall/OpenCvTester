package baseClasses.history.imp;

import baseClasses.history.History;
import baseClasses.history.HistoryParameter;

public class ParametersHistory<T> extends History<T>
{	
	protected Boolean firstUndo; 
	protected Boolean firstRedo;
	
	public ParametersHistory(){	
	}
	
	public void store() {
		 if (readyToStore) {
			 HistoryParameter<T> parameter = factory.getNew();
			 parameter.set(state.clone());
			 undoHistory.push(parameter);
			 firstUndo = true;
			 firstRedo = true;
			 clearRedoHistory();
			 readyToStore=false;
		 }
	}
     
	public void undo() {
	    if (!isUndoEmpty() && state!=null){
			if (firstUndo) {
				redoHistory.push(undoHistory.peek());
				undoHistory.pop();
				state=undoHistory.peek();
				undoHistory.pop();
				firstUndo=false; 
				firstRedo=true;
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
	    	if (firstRedo == true) {
	    		undoHistory.push(state);
	    	}
		    	state=redoHistory.peek();
		    	redoHistory.pop(); 
		    	HistoryParameter<T> parameter = factory.getNew();
	    		parameter.set(state.clone());
	    		undoHistory.push(parameter);
		    	firstRedo=false;
	    }
	} 
	
	public Boolean isUndoEmpty() { 
		if(firstUndo) {
			if (undoHistory.size() <= 1) {
				return true;  
			}
			else {
				return false;
			}
		}
		else {
			if (undoHistory.size() <= 0) {
				return true;  
			}
			else {
				return false;
			}
		}
	}
}