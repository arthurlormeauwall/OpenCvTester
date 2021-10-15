package baseClasses.history;
//Test
import java.util.Stack;



public abstract class History<T>
{ 
	protected HistoryParameter<T> m_factory;	
	protected HistoryParameter<T> m_state;
	protected Stack<HistoryParameter<T>> m_undoHistory;
	protected Stack<HistoryParameter<T>> m_redoHistory; 

	public History(){
		m_undoHistory= new Stack<HistoryParameter<T>>();      
		m_redoHistory = new Stack<HistoryParameter<T>>();
	}
		
	public void initFactory(HistoryParameter<T> p) {
		m_factory=p;
	}
		
	public void initState(HistoryParameter<T> p) {
		m_state=p;
	}
	
	public abstract void store();   
	public abstract void undo(); 
	public abstract void redo(); 
	 
	public void setState(HistoryParameter<T> t) {
		m_state.set(t.getParameter());
	}
	
	public Boolean isUndoEmpty() { 
		if (m_undoHistory.size() <= 0) {
		    return true;  
		}
		else if (m_undoHistory.size() > 0) {
		    return false;
		}
			return null;
		}
	
	public Boolean isRedoEmpty() {
		if (m_redoHistory.size() <= 0) {
		    return true;
		}
		else if (m_redoHistory.size() > 0) {
		    return false;
		}
	return null;
	}
	  
	    
	public HistoryParameter<T> getState() { 
	 
		return m_state;    	
	}
	    
	public void clearRedoHistory() {		
		m_redoHistory.clear();
	}
	
	public void clearUndoHistory() {
		m_undoHistory.clear();
	}
};