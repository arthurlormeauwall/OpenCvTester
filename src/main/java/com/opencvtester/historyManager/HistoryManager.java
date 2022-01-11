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
			history.putInUndoHistory(history.currentState().clone());
		}
	}
	
//	public void undo() {	
//		if (history.isThereUndoActionsLeft(firstUndo)) {
//			
//			if (firstUndo) {	
//				if (history.nextUndo().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {	
//					history.putInRedoStack(history.popNextUndo());		
//				}	
//			}	
//			else {	
//				history.putInRedoStack(history.currentState());
//			}	
//			
//			history.setCurrentState(history.popNextUndo());				
//			invertAndExecute();	
//			
//			firstUndo=false;
//			firstRedo=true;
//		}		
//	}

//	public void redo() {	
//		if (history.isThereRedoActionsLeft()) {	
//			if (firstRedo) {	
//				if (history.currentState().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {				
//					history.putInUndoHistory(history.currentState());	
//					history.setCurrentState(history.popNextRedo());
//				}	
//			}
//			else {
//				history.popNextRedoInCurrent();
//				
//				if (history.nextUndo().natureOfAction()==NatureOfAction.ADD_OR_DELETE) {
//					if (history.currentState().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {	
//						history.putInUndoHistory(history.currentState());
//						history.setCurrentState(history.popNextRedo());
//					}
//				}
//			}
//			
//			invertAndExecute();
//			
//			if (firstRedo) {
//				if (history.currentState().natureOfAction()==NatureOfAction.ADD_OR_DELETE) {
//					history.putInUndoHistory(history.currentState());
//				}		
//			}
//			else {
//				history.putInUndoHistory(history.currentState());
//			}
//			firstRedo=false;
//			firstUndo=true;
//		}			
//	}
	
	
	/////////////////// TEST 
	
	public void redo() {	
	if (history.isThereRedoActionsLeft()) {	
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
			history.putInUndoHistory(history.currentState());	
			history.setCurrentState(history.popNextRedo());
		}	
		
		invertAndExecute();
		
		if (history.currentState().natureOfAction()==NatureOfAction.ADD_OR_DELETE) {	
			history.putInUndoHistory(history.currentState());
		}
	}
	
	public void othersRedo() {
		

		history.setCurrentState(history.popNextRedo());
		
		if (history.nextUndo().natureOfAction()==NatureOfAction.ADD_OR_DELETE) {
			if (history.currentState().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {	
				history.putInUndoHistory(history.currentState());
				history.setCurrentState(history.popNextRedo());
			}
		}
		
		invertAndExecute();
		history.putInUndoHistory(history.currentState());
	}
	
	public void undo() {	
		if (history.isThereUndoActionsLeft(firstUndo)) {	
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
	
	public void firstUndo() {
		if (history.nextUndo().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {	
			history.putInRedoStack(history.popNextUndo());		
		}
		
		history.setCurrentState(history.popNextUndo());				
		invertAndExecute();	
	}
	
	public void othersUndo() {
		history.putInRedoStack(history.currentState());
		
		history.setCurrentState(history.popNextUndo());		
		invertAndExecute();
	}
	
	///////////////////////////

	public void invertAndExecute() {
		history.currentState().invert();
		history.currentState().execute();
	}

	public void setState(Action action) {
		history.setCurrentState(action);
		firstUndo=true;
		firstRedo=true;
		
		if (action.natureOfAction()==NatureOfAction.PARAMETER_SETTING) {
			if (history.nextUndo().natureOfAction()==NatureOfAction.ADD_OR_DELETE) {
				storeCurrentStateInHistory();
			}
		}
	}
}

