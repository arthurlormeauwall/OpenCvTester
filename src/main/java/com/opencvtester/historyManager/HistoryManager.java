package com.opencvtester.historyManager;


import com.opencvtester.historyManager.action.Action;
import com.opencvtester.historyManager.action.NatureOfAction;

public class HistoryManager
{ 
	protected boolean firstUndo;
	protected boolean firstRedo;
	
	protected History history;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public HistoryManager(){
		history= new History();

		firstRedo=true;
		firstUndo=true;
	}
	
	/*
	 * FEATURES
	 */
	
	public void storeCurrentStateInHistory() {
		history.clearRedoHistory();
		if (history.currentState()!=null) {
			history.pushUndoHistory(history.currentState().clone());
		}
	}
	
	public void setState(Action action) {
		history.setCurrentState(action);
		
		firstUndo=true;
		firstRedo=true;
		
//		if (history.currentState().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {
//			if (history.nextUndo().natureOfAction()==NatureOfAction.ADD_OR_DELETE) {
//				storeCurrentStateInHistory();
//			}
//		}
	}
	

	public void invertAndExecute() {
		history.currentState().invert();
		history.currentState().execute();
	}
	
	public void undo() {	
		if (undoIsNotEmpty()) {	
			if (firstUndo) {	
				firstUndo();	
			}	
			else {	
				firstUndo();	
			}	
			
			firstUndo=false;
			firstRedo=true;
		}		
	}
	
	public boolean undoIsNotEmpty() {
		if (firstUndo) {
			if (!history.undoIsNotEmpty()) {
				return false;
			}
			else {
				if (history.nextUndo().natureOfAction()==NatureOfAction.ADD_OR_DELETE) {
					return true;
				}
				else {
					if (history.numberOfUndoActionsLeft()>=2) {
						return true;
					}
					else {
						return false;
					}
				}	
			}
		}
		else {
			return history.undoIsNotEmpty();
		}
	}
	
	public void firstUndo() {
		if (history.nextUndo().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {	
			history.pushRedoHistory(history.popNextUndo());		
		}
		
		history.setCurrentState(history.popNextUndo());				
		invertAndExecute();	
		history.pushRedoHistory(history.currentState());
	}
	
	public void othersUndo() {
		history.setCurrentState(history.popNextUndo());	
	
		invertAndExecute();
		history.pushRedoHistory(history.currentState());
	}
	
	public void redo() {	
	if (history.redoIsNotEmpty()) {	
		if (firstRedo) {	
			firstRedo();
		}
		else {
			firstRedo();
		}
		
		firstRedo=false;
		firstUndo=true;
	}			
}
	public void firstRedo() {	
		if (history.nextRedo().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {				
			history.pushUndoHistory(history.popNextRedo());			
		}
		
		history.setCurrentState(history.popNextRedo());
		invertAndExecute();
		

		history.pushUndoHistory(history.currentState());

	}
	
	public void othersRedo() {
		history.setCurrentState(history.popNextRedo());
		invertAndExecute();
		

		history.pushUndoHistory(history.currentState());
	}
}

