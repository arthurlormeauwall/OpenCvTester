package com.opencvtester.historyManager;

import java.util.Stack;

import com.opencvtester.historyManager.action.Action;

public class HistoryManager
{ 
	public boolean firstUndo;
	public boolean firstRedo;
	
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
	
	public void store() {
		clearRedoHistory();
		if (history.currentAction()!=null) {
			history.putInUndoStack(history.currentAction().clone());
		}
	}
	
	public void undo() {	
		if (!isUndoEmpty()) {
			beforeUndo();
			invertAndExecute();	
			firstUndo=false;
			firstRedo=true;
		}		
	}

	public void redo() {	
		if (!isRedoEmpty()) {	
			beforeRedo();
			invertAndExecute();
			afterRedo();
			firstRedo=false;
			firstUndo=true;
		}			
	}

	public void invertAndExecute() {
		history.currentAction().invert();
		history.currentAction().execute();
	}
	
	

	public void beforeUndo() {
		if (firstUndo) {	
			if (history.nextUndo().addOrDeleteAction()==false) {	
				history.putInRedoStack(history.popNextUndo());		
			}	
		}	
		else {	
			history.putInRedoStack(history.currentAction());
		}			
		history.setCurrentAction(history.popNextUndo());	
	}
	
	public void beforeRedo() {
		if (firstRedo) {	
			if (history.currentAction().addOrDeleteAction()==false) {				
				history.putInUndoStack(history.currentAction());	
				history.setCurrentAction(history.popNextRedo());
			}	
		}
		else {
			history.popNextRedoInCurrent();
			
			if (history.nextUndo().addOrDeleteAction()==true) {
				if (history.currentAction().addOrDeleteAction()==false) {	
					history.putInUndoStack(history.currentAction());
					history.setCurrentAction(history.popNextRedo());
				}
			}
		}
	}
	
	public void afterRedo() {	
		if (firstRedo) {
			if (history.currentAction().addOrDeleteAction()==true) {
				history.putInUndoStack(history.currentAction());
			}		
		}
		else {
			history.putInUndoStack(history.currentAction());
		}
	}

	public void setState(Action action) {
		history.setCurrentAction(action);
		firstUndo=true;
		firstRedo=true;
		
		if (action.addOrDeleteAction()==false) {
			if (history.nextUndo().addOrDeleteAction()==true) {
				store();
			}
		}
	}
	
	public Boolean isUndoEmpty() {
		if (firstUndo) {
			if (!history.undoStack.isEmpty()) {
				if (history.nextUndo().addOrDeleteAction()==true) {
					return false;
				}
				else {
					if (history.undoStack.size()>=2) {
						return false;
					}
					else {
						return true;
					}
				}	
			}
			else {
				return true;
			}	
		}
		else {
			return history.undoStack.isEmpty();
		}		
	}
	
	public Boolean isRedoEmpty() {
		return history.redoStack.isEmpty();	
	}
	
	public void clearRedoHistory() {
		history.redoStack.clear();
	}
}

