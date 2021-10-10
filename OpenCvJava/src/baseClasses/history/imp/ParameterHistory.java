package baseClasses.history.imp;

import baseClasses.history.History;
import baseClasses.history.HistoryParameter;

public class ParameterHistory<T> extends History<T>
{

	public ParameterHistory(){
		
	}
	
	public void store() {
		
		HistoryParameter<T> parameter = m_factory.getNew();
		parameter.set(m_state.getParameter());  
		m_history.push(parameter);
		firstUndo = true;
	}
     
	public void undo() {
	    if (!isUndoEmpty() && m_state!=null)
	    {
	   	 if (firstUndo) {
	   		 m_redoHistory.push(m_history.peek());
	   		 m_history.pop();
	   		 m_state=m_history.peek();
	   		 m_history.pop();
	   		 firstUndo=false;
	   		 
	   	 }
	   	 else {
	   		 m_redoHistory.push(m_state);
	   		 m_state=m_history.peek();
	   		 m_history.pop();
	   	 }
	    } 
	}
	
	public void redo() {
	    if (!isRedoEmpty() && m_state!=null)
	    {
	    	m_history.push(m_state);
	    	m_state=m_redoHistory.peek();
	    	m_redoHistory.pop();   	
	    }
	} 
	
	public Boolean isUndoEmpty() { 
		 if(firstUndo) {
			   if (m_history.size() <= 1) {
		             return true;  
		         }
		         else if (m_history.size() > 1) {
		             return false;
		         }
		 }
		 else {
		   if (m_history.size() <= 0) {
	            return true;  
	        }
	        else if (m_history.size() > 0) {
	            return false;
	        }
		 }
		 return null;
	}
	
	protected Boolean firstUndo; 
};