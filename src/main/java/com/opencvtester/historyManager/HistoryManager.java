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

	public void invertAndExecute() {
		history.currentAction.invert();
		history.currentAction.execute();
	}
	
	public void undo() {	
		if (!isUndoEmpty()) {
			beforeUndo();
			invertAndExecute();	
			afterUndo();
			history.firstUndo=false;
			history.firstRedo=true;
		}		
	}
	

	public void redo() {	
		if (!isRedoEmpty()) {	
			beforeRedo();
			invertAndExecute();
			afterRedo();
			history.firstRedo=false;
			history.firstUndo=true;
		}			
	}
	
	public void beforeUndo() {
		if (history.firstUndo) {	
			if (history.undoList.lastElement().addOrDeleteSystem()==false) {	
				history.redoList.push(history.undoList.pop());		
			}	
		}	
		else {	
			saveCurrentInReddoList();
		}		
		
		history.currentAction=history.undoList.pop();			
	}
	
	public void afterUndo() {

	}

	public void beforeRedo() {
		if (history.firstRedo) {	
			if (history.currentAction.addOrDeleteSystem()==false) {				
				saveCurrentInUndoList();
				
				history.currentAction=history.redoList.pop();	
			}	
		}
		else {
			history.currentAction=history.redoList.pop();
			
			if (history.undoList.lastElement().addOrDeleteSystem()==true) {
				if (history.currentAction.addOrDeleteSystem()==false) {	
					saveCurrentInUndoList();
					history.currentAction=history.redoList.pop();
				}
			}
		}
	}
	

	public void saveCurrentInUndoList() {
		history.undoList.push(history.currentAction);	
	}
	
	public void saveCurrentInReddoList() {
		history.redoList.push(history.currentAction);	
	}
	
	public void afterRedo() {	
		if (history.firstRedo) {
			if (history.currentAction.addOrDeleteSystem()==true) {
				saveCurrentInUndoList(); 
			}		
		}
		else {
			saveCurrentInUndoList();
		}
	}


	public void setState(Action action) {
		history.currentAction= action;
		history.firstUndo=true;
		history.firstRedo=true;
		
		if (action.addOrDeleteSystem()==false) {
			if (history.undoList.lastElement().addOrDeleteSystem()==true) {
				store();
			}
		}
	}
	
	public Boolean isUndoEmpty() {
		if (history.firstUndo) {
			if (!history.undoList.isEmpty()) {
				if (history.undoList.lastElement().addOrDeleteSystem()==true) {
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