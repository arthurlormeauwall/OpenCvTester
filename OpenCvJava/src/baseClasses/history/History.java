package baseClasses.history;

import java.util.Stack;

public abstract class History<T> 
{ 
	protected HistoryParameter<T> factory;	
	protected HistoryParameter<T> state;
	protected Stack<HistoryParameter<T>> undoHistory;
	protected Stack<HistoryParameter<T>> redoHistory; 
	
	protected Boolean readyToStore;

	public History(){
		undoHistory= new Stack<HistoryParameter<T>>();      
		redoHistory = new Stack<HistoryParameter<T>>();
		readyToStore=false;
	}
		
	public void initFactory(HistoryParameter<T> p) {
		factory=p;
	}
		
	public void initState(HistoryParameter<T> p) {
		state=p;
	}
	
	public abstract void store();   
	public abstract void undo(); 
	public abstract void redo(); 
	 
	public void setState(HistoryParameter<T> t) {
		state.set(t.clone());
		readyToStore=true;
	}
	
	public HistoryParameter<T> getState() { 
		return state;    	
	}
	
	public Boolean isUndoEmpty() { 
		if (undoHistory.size() <= 0) {
		    return true;  
		}
		else  {
		    return false;
		}
	}
	
	public Boolean isRedoEmpty() {
		if (redoHistory.size() <= 0) {
		    return true;
		}
		else {
		    return false;
		}
	}
	    
	protected void clearRedoHistory() {		
		redoHistory.clear();
	}
	
	protected void clearUndoHistory() {
		undoHistory.clear();
	}	
}