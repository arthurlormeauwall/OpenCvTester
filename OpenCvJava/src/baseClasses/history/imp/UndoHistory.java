package baseClasses.history.imp;

import baseClasses.history.History;
import baseClasses.history.HistoryParameter;

public class UndoHistory<T> extends History<T> 
{
	protected Boolean firstUndo; 
	
	public UndoHistory(){		
	}
	
	public void store() {	
		HistoryParameter<T> parameter = factory.getNew();
		parameter.set(state.getParameter());  
		undoHistory.push(parameter);
		firstUndo = true;
	}
	
	public void undo() {
		if (isUndoEmpty() && state!=null)
			{
			if (firstUndo) {
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
		if (isRedoEmpty()==false && state!=null)
		{
			undoHistory.push(state);
			state=redoHistory.peek();
			redoHistory.pop();   	
		}
	}
};