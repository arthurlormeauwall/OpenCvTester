package baseClasses.history;
//Test
import java.util.Stack;



public abstract class History<T>
{

	public History(){
		 m_history= new Stack<HistoryParameter<T>>();      
		 m_redoHistory = new Stack<HistoryParameter<T>>();
	
	}
	
	public void setFactory(HistoryParameter<T> p) {
		m_factory=p;
	}
	public void setState(HistoryParameter<T> p) {
		m_state=p;
	}
     public abstract void store();   
     public abstract void undo(); 
     public abstract void redo(); 
     
     public void setLast(HistoryParameter<T> t) {
    		m_state.set(t.getParameter());
    }

    public Boolean isUndoEmpty() { 
        if (m_history.size() <= 0) {
            return true;  
        }
        else if (m_history.size() > 0) {
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
   
    
    public HistoryParameter<T> getLast() { 
 
        return m_state; 
    	
    }
    
    public void clearRedoHistory() {
  	  int lastRedoHistory = m_redoHistory.size() - 1;
  	    for (int i = lastRedoHistory; i >= 0; i--) {
  	
  	        m_redoHistory.pop();
  	    }
  }
  public void clearUndoHistory() {
  	 int lastRedoHistory = m_history.size() - 1;
	    for (int i = lastRedoHistory; i >= 0; i--) {
	
	        m_history.pop();
	    }
  }
  
   protected HistoryParameter<T> m_factory;	
   protected HistoryParameter<T> m_state;
   protected Stack<HistoryParameter<T>> m_history;
   protected Stack<HistoryParameter<T>> m_redoHistory; 

};