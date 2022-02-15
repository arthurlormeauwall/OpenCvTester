package com.opencvtester.history;

import java.util.Stack;

public class History {
	private Stack<Action> undoStack;
	private Stack<Action> redoStack;
	private Action currentAction;
	
	public History() {
		undoStack= new Stack<Action>();
		redoStack = new Stack<Action>();
		currentAction= null;
	}

	public Action nextUndo() {
		return undoStack.lastElement();
	}
	
	public Action nextRedo() {
		return redoStack.lastElement();
	}

	public Action currentState() {
		return currentAction;
	}
	
	public void setCurrentState(Action action) {
		currentAction=action;
	}
	
	public Action popNextUndo() {
		return undoStack.pop();
	}
	
	public Action popNextRedo() {
		return redoStack.pop();
	}
	
	public void putCurrentStateInUndo() {
		undoStack.push(currentState());
	}
	
	public void putCurrentStateInRedo() {
		redoStack.push(currentState());
	}
	
	public void pushUndoHistory(Action action) {
		undoStack.push(action);
	}
	
	public void pushRedoHistory(Action action) {
		redoStack.push(action);
	}
	
	public void saveCurrentInUndoStack() {
		pushUndoHistory(currentAction);	
	}
	
	public void saveCurrentInReddoStack() {
		pushRedoHistory(currentAction);	
	}
	
	public void popNextUndoInCurrent() {
		currentAction=popNextUndo();
	}
	
	public void popNextRedoInCurrent() {
		currentAction=popNextRedo();
	}
	
	public boolean undoIsNotEmpty() {
		return !undoStack.isEmpty();
	}
	
	public boolean redoIsNotEmpty() {
		return !redoStack.isEmpty();
	}
	
	public int numberOfUndoActionsLeft() {
		return undoStack.size();
	}
	
	public void clearRedoHistory() {
		redoStack.clear();
	}

	public void clearUndoHistory() {
		undoStack.clear();
		
	}	
}
