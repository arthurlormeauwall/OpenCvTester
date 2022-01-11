package com.opencvtester.historyManager;

import java.util.Stack;

import com.opencvtester.historyManager.action.Action;
import com.opencvtester.historyManager.action.NatureOfAction;

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
	
	public boolean undoIsEmpty(boolean firstUndo) {
		if (firstUndo) {
			if (!undoStack.isEmpty()) {
				if (nextUndo().natureOfAction()==NatureOfAction.PARAMETER_SETTING) {
					return false;
				}
				else {
					if (numberOfUndoActionsLeft()>=2) {
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
	
	public boolean redoIsEmpty() {
		return redoStack.isEmpty();
	}
	
	public int numberOfUndoActionsLeft() {
		return undoStack.size();
	}
	
	public void clearRedoHistory() {
		redoStack.clear();
	}	
}
