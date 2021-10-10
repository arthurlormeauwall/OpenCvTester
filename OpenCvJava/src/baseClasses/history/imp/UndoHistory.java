package baseClasses.history.imp;

import baseClasses.history.History;
import baseClasses.history.HistoryParameter;

public class UndoHistory<T> extends History<T>
{

	public UndoHistory(){		
	}
	
	public void store() {	
		HistoryParameter<T> parameter = m_factory.getNew();
		parameter.set(m_state.getParameter());  
		m_undoHistory.push(parameter);
		firstUndo = true;
	}
	
	public void undo() {
		if (isUndoEmpty() && m_state!=null)
			{
			if (firstUndo) {
				m_state=m_undoHistory.peek();
				m_undoHistory.pop();
				firstUndo=false;	 
			}
			else {
				m_redoHistory.push(m_state);
				m_state=m_undoHistory.peek();
				m_undoHistory.pop();
			}
		} 
	}
	
	public void redo() {
		if (isRedoEmpty()==false && m_state!=null)
		{
			m_undoHistory.push(m_state);
			m_state=m_redoHistory.peek();
			m_redoHistory.pop();   	
		}
	}
	
	protected Boolean firstUndo; 
};