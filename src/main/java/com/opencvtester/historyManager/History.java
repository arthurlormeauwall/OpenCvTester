package com.opencvtester.historyManager;

import java.util.Stack;

import com.opencvtester.historyManager.action.Action;

public class History {
	public Stack<Action> undoStack;
	public Stack<Action> redoStack;

	Action currentAction;
	public Stack<Action> actions;
	
	public boolean firstUndo;
	public boolean firstRedo;
	
	public History() {
		undoStack= new Stack<Action>();
		redoStack = new Stack<Action>();
		currentAction= null;

		firstRedo=true;
		firstUndo=true;
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
}
