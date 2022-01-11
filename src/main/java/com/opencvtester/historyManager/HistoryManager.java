package com.opencvtester.historyManager;

import java.util.Stack;

import com.opencvtester.historyManager.action.Action;

public class HistoryManager
{ 
	public Stack<Action> undoStack;
	public Stack<Action> redoStack;

	Action currentAction;
	public Stack<Action> actions;
	
	public int currentIndex;
	public boolean firstUndo;
	public boolean firstRedo;
	
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public HistoryManager(){
//		history= new History();
		
		undoStack= new Stack<Action>();
		redoStack = new Stack<Action>();
		currentAction= null;

		firstRedo=true;
		firstUndo=true;
	}
	
	/*
	 * FEATURES
	 */
	
	public void store() {
		clearRedoHistory();
		if (currentAction!=null) {
			undoStack.push(currentAction.clone());	
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
		currentAction.invert();
		currentAction.execute();
	}
	
	public Action nextUndo() {
		return undoStack.lastElement();
	}
	
	public Action nextRedo() {
		return redoStack.lastElement();
	}

	public Action currentAction() {
		return currentAction;
	}
	
	public void setCurrentAction(Action action) {
		currentAction=action;
	}
	
	public Action popNextUndo() {
		return undoStack.pop();
	}
	
	public Action popNextRedo() {
		return redoStack.pop();
	}
	
	public void putCurrentStateInUndo() {
		undoStack.push(currentAction());
	}
	
	public void putCurrentStateInRedo() {
		redoStack.push(currentAction());
	}
	
	public void putInUndoStack(Action action) {
		undoStack.push(action);
	}
	
	public void putInRedoStack(Action action) {
		redoStack.push(action);
	}
	
	public void saveCurrentInUndoList() {
		putInUndoStack(currentAction);	
	}
	
	public void saveCurrentInReddoStack() {
		putInRedoStack(currentAction);	
	}
	
	public void popNextUndoInCurrent() {
		currentAction=popNextUndo();
	}
	
	public void popNextRedoInCurrent() {
		currentAction=popNextRedo();
	}

	public void beforeUndo() {
		if (firstUndo) {	
			if (nextUndo().addOrDeleteAction()==false) {	
				putInRedoStack(popNextUndo());		
			}	
		}	
		else {	
			putInRedoStack(currentAction());
		}			
		setCurrentAction(popNextUndo());	
	}
	
	public void beforeRedo() {
		if (firstRedo) {	
			if (currentAction().addOrDeleteAction()==false) {				
				putInUndoStack(currentAction());	
				setCurrentAction(popNextRedo());
			}	
		}
		else {
			popNextRedoInCurrent();
			
			if (nextUndo().addOrDeleteAction()==true) {
				if (currentAction().addOrDeleteAction()==false) {	
					putInUndoStack(currentAction());
					setCurrentAction(popNextRedo());
				}
			}
		}
	}
	
	public void afterRedo() {	
		if (firstRedo) {
			if (currentAction().addOrDeleteAction()==true) {
				putInUndoStack(currentAction());
			}		
		}
		else {
			putInUndoStack(currentAction());
		}
	}

	public void setState(Action action) {
		setCurrentAction(action);
		firstUndo=true;
		firstRedo=true;
		
		if (action.addOrDeleteAction()==false) {
			if (nextUndo().addOrDeleteAction()==true) {
				store();
			}
		}
	}
	
	public Boolean isUndoEmpty() {
		if (firstUndo) {
			if (!undoStack.isEmpty()) {
				if (nextUndo().addOrDeleteAction()==true) {
					return false;
				}
				else {
					if (undoStack.size()>=2) {
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
			return undoStack.isEmpty();
		}		
	}
	
	public Boolean isRedoEmpty() {
		return redoStack.isEmpty();	
	}
	
	public void clearRedoHistory() {
		redoStack.clear();
	}
}

