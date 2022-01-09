package com.opencvtester.historyManager;

import com.opencvtester.historyManager.action.Action;

public interface HistoryReader {
	
	public void store(History historyManager);
	
	public void undo(History historyManager);
	
	public void redo(History historyManager);
	
	public void invertAndExecute(History historyManager) ;
	
	public void moveIndexForward(History historyManager) ;
	
	public void moveIndexBackward(History historyManager);
	 
	public void setState(Action action, History historyManager) ;
	
	public Boolean isUndoEmpty(History historyManager);
	
	public Boolean isRedoEmpty(History historyManager) ;

	void clearRedoHistory(History history);
}
