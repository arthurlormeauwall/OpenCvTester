package baseClasses.history.imp;

import baseClasses.history.History;
import baseClasses.history.HistoryParameter;

public class IdHistory<T> extends History<T> 
{
	protected Boolean firstUndo; 
	protected Boolean firstRedo;
	
	public IdHistory(){		
	}
	
	public void store() {	
		 if (readyToStore) {
			HistoryParameter<T> parameter = factory.getNew();
			parameter.set(state.clone());  
			undoHistory.push(parameter);
			firstUndo = true;
			firstRedo = true;
			clearRedoHistory();
			this.readyToStore=false;
		 }
	}
	
	public void undo() {
		if (isUndoEmpty()==false && state!=null){
			if (firstUndo) {
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
		if (firstRedo == false && isRedoEmpty()==false && state!=null)
		{
			undoHistory.push(state);
			state=redoHistory.peek();
			redoHistory.pop();
		}
		else {
			firstRedo=false;
			store();
		}
		
	}
};