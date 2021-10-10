package baseClasses.history.imp;

import baseClasses.history.History;
import baseClasses.history.HistoryParameter;

public class ChainHistory<T> extends History<T>
{

	public ChainHistory(){
	}
	
     public void store() { 	
		 m_state.invert();	 
		 HistoryParameter<T> parameter = m_factory.getNew();
		 parameter.set(m_state.getParameter());  
		 m_undoHistory.push(parameter);
     }
     
     public void undo() {
         if (!isUndoEmpty() && m_state!=null) {
        	m_state.invert();
        	m_redoHistory.push(m_state);
         	m_state.set(m_undoHistory.peek().getParameter());
            m_undoHistory.pop();
         } 
     }
     
     public void redo() {
         if (!isRedoEmpty() && m_state!=null){
         	 HistoryParameter<T> n;
             n = m_redoHistory.peek();
             m_state = n;
             m_redoHistory.peek().invert();
             m_undoHistory.push(m_redoHistory.peek());
             m_redoHistory.pop();
         }
     }  
};