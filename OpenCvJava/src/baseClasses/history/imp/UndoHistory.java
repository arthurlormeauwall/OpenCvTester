package baseClasses.history.imp;

import baseClasses.history.History;
import baseClasses.history.HistoryParameter;

public class UndoHistory<T> extends History<T> 
{
	protected Boolean firstUndo; 
	protected Boolean firstRedo;
	
	public UndoHistory(){		
	}
	
	public void store() {	
		HistoryParameter<T> parameter = factory.getNew();
		parameter.set(state.getParameter());  
		undoHistory.push(parameter);
		firstUndo = true;
		firstRedo = true;
	}
	
	public void undo() {
		if (isUndoEmpty()==false && state!=null)
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
		if (firstRedo == false && isRedoEmpty()==false && state!=null)
		{
			undoHistory.push(state);
			state=redoHistory.peek();
			redoHistory.pop();
		}
		firstRedo=false;
	}
};