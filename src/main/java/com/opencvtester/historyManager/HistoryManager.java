package com.opencvtester.historyManager;

import java.util.Stack;

import com.opencvtester.historyManager.action.Action;

public class HistoryManager 
{ 
	protected History history;
	/*
	 * CONSTRUCTOR & INITS
	 */
	public HistoryManager(){
		history= new History();
		
		history.undoList= new Stack<Action>();
		history.redoList = new Stack<Action>();
		history.currentAction= null;

		history.firstRedo=true;
		history.firstUndo=true;
	}
	
	/*
	 * FEATURES
	 */
	
	public void store() {
		clearRedoHistory();
		if (history.currentAction!=null) {
			history.undoList.push(history.currentAction.clone());	
		}
	}

	
	public void undo() {	
		if (!isUndoEmpty()) {
			if (history.firstUndo) {	
				if (history.undoList.lastElement().lockedSystem()==false) {
					history.redoList.push(history.undoList.pop());	
					history.currentAction=history.undoList.pop();
					invertAndExecute();
				}		
				else {
					history.currentAction=history.undoList.pop();
					invertAndExecute();
//					history.redoList.push(history.currentAction);
				}	
			}	
			else {	
				history.redoList.push(history.currentAction);	
				history.currentAction=history.undoList.pop();			
				invertAndExecute();	
			
			}
		}
		history.firstUndo=false;
		history.firstRedo=true;
	}


	public void redo() {	
		if (!isRedoEmpty()) {	
			if (history.firstRedo) {
				if (history.currentAction.lockedSystem()==false) {
					history.undoList.push(history.currentAction);
					history.currentAction=history.redoList.pop();
					invertAndExecute();
			
					history.firstRedo=false;
				}
				else {
					invertAndExecute();
					history.undoList.push(history.currentAction);
					history.firstRedo=false;
				}
			}
			else {
				history.currentAction=history.redoList.pop();
				if (history.undoList.lastElement().lockedSystem()==true) {
					if (history.currentAction.lockedSystem()==false) {
						history.undoList.push(history.currentAction);
						history.currentAction=history.redoList.pop();
					}
				}
				invertAndExecute();
				history.undoList.push(history.currentAction);
			}
			history.firstUndo=true;
		}	
	}

	
	public void invertAndExecute() {
		history.currentAction.invert();
		history.currentAction.execute();
	}


	public void setState(Action action) {
		history.currentAction= action;
		history.firstUndo=true;
		history.firstRedo=true;
		if (action.lockedSystem()==false) {
			if (history.undoList.lastElement().lockedSystem()==true) {
				store();
			}
		}
	}

	
	public Boolean isUndoEmpty() {
		if (history.firstUndo) {
			if (!history.undoList.isEmpty()) {
				if (history.undoList.lastElement().lockedSystem()==true) {
					return false;
				}
				else {
					if (history.undoList.size()>=2) {
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
			return history.undoList.isEmpty();
		}
			
	}

	
	public Boolean isRedoEmpty() {
		return history.redoList.isEmpty();	
	}

	
	public void clearRedoHistory() {
		history.redoList.clear();
	}


}