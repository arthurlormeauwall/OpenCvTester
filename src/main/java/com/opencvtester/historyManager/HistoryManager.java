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
		history.actions=new Stack<Action>();
		history.currentIndex=0;
		history.actions.push(null);
		history.indexIsLocked = false;
		history.undoCount=0;
		history.redoCount=0;
		history.firstRedo=false;
		history.firstUndo=false;
	}
	
	/*
	 * FEATURES
	 */
	public void store() {
		history.actions.get(history.currentIndex).getHistoryReader().store(history);
	}
	
	public void undo(){
		if (history.actions.get(history.currentIndex)!=null) {
			history.actions.get(history.currentIndex).getHistoryReader().undo(history);		
		}	
	} 
	
	public void redo(){
		history.actions.get(history.currentIndex).getHistoryReader().redo(history);	
	}
	
	public void setState(Action action) {		
		action.getHistoryReader().setState(action, history);	
	}
}