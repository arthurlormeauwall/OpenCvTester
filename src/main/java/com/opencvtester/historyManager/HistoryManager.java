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
		
		if (history.currentState().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {
			if (history.nextUndo().natureOfAction()==NatureOfAction.ADD_OR_DELETE) {
				storeCurrentStateInHistory();
			}
		}
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
				othersUndo();
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
	}
	
	public void othersUndo() {
		history.pushRedoHistory(history.currentState());
		
		history.setCurrentState(history.popNextUndo());		
		invertAndExecute();
	}
	
	public void redo() {	
	if (history.redoIsNotEmpty()) {	
		if (firstRedo) {	
			firstRedo();
		}
		else {
			othersRedo();
		}
		
		firstRedo=false;
		firstUndo=true;
	}			
}
	public void firstRedo() {	
		if (history.currentState().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {				
			history.pushUndoHistory(history.currentState());	
			history.setCurrentState(history.popNextRedo());
		}	
		
		invertAndExecute();
		
		if (history.currentState().natureOfAction()==NatureOfAction.ADD_OR_DELETE) {	
			history.pushUndoHistory(history.currentState());
		}
	}
	
	public void othersRedo() {
		history.setCurrentState(history.popNextRedo());
		
		if (history.nextUndo().natureOfAction()==NatureOfAction.ADD_OR_DELETE) {
			if (history.currentState().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {	
				history.pushUndoHistory(history.currentState());
				history.setCurrentState(history.popNextRedo());
			}
		}
		
		invertAndExecute();
		
		history.pushUndoHistory(history.currentState());
	}
}

