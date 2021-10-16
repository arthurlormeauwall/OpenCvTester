package baseClasses.history;
//Test
import java.util.Stack;



public abstract class History<T>
{ 
	protected HistoryParameter<T> factory;	
	protected HistoryParameter<T> state;
	protected Stack<HistoryParameter<T>> undoHistory;
	protected Stack<HistoryParameter<T>> redoHistory; 

	public History(){
		undoHistory= new Stack<HistoryParameter<T>>();      
		redoHistory = new Stack<HistoryParameter<T>>();
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
		state.set(t.getParameter());
	}
	
	public Boolean isUndoEmpty() { 
		if (undoHistory.size() <= 0) {
		    return true;  
		}
		else if (undoHistory.size() > 0) {
		    return false;
		}
			return null;
		}
	
	public Boolean isRedoEmpty() {
		if (redoHistory.size() <= 0) {
		    return true;
		}
		else if (redoHistory.size() > 0) {
		    return false;
		}
	return null;
	}
	  
	    
	public HistoryParameter<T> getState() { 
	 
		return state;    	
	}
	    
	public void clearRedoHistory() {		
		redoHistory.clear();
	}
	
	public void clearUndoHistory() {
		undoHistory.clear();
	}
}