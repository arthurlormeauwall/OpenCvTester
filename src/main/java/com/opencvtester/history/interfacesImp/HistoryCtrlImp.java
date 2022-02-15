package com.opencvtester.history.interfacesImp;

import com.opencvtester.controller.interfaces.HistoryController;
import com.opencvtester.history.Action;
import com.opencvtester.history.History;
import com.opencvtester.history.NatureOfAction;

public class HistoryCtrlImp implements HistoryController
{ 
	protected boolean firstUndo;
	protected boolean firstRedo;
	
	protected History history;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public HistoryCtrlImp(){
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
	}	

	public void invertAndExecute() {
		history.currentState().invert();
		history.currentState().execute();
	}
	
	public void undo() {	
		if (undoIsNotEmpty()) {	
			if (history.nextUndo().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {	
				history.pushRedoHistory(history.popNextUndo());		
			}
			
			history.setCurrentState(history.popNextUndo());				
			invertAndExecute();	
			history.pushRedoHistory(history.currentState());
			
			firstUndo=false;
			firstRedo=true;
		}		
	}
	
	public void redo() {	
		if (history.redoIsNotEmpty()) {	
			if (history.nextRedo().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {				
				history.pushUndoHistory(history.popNextRedo());			
			}
			
			history.setCurrentState(history.popNextRedo());
			invertAndExecute();
			
	
			history.pushUndoHistory(history.currentState());
			
			firstRedo=false;
			firstUndo=true;
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

	public void clearAll() {
		history.clearRedoHistory();
		history.clearUndoHistory();
		
	}
}
