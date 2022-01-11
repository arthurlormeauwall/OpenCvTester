package com.opencvtester.historyManager;

import com.opencvtester.historyManager.action.Action;

public class AddOrDeleteHistoryReader implements HistoryReader {

	@Override
	public void store(History history) {
		clearRedoHistory(history);
		if (history.currentAction!=null) {
			history.undoList.push(history.currentAction.clone());	
		}
	}

	@Override
	public void undo(History history) {	
		if (history.firstUndo) {
			if (!isUndoEmpty(history)) {	
				if (history.undoList.lastElement().addOrDeleteSystem()==false) {
					history.redoList.push(history.undoList.pop());
				}
				
				history.currentAction=history.undoList.pop();
				invertAndExecute(history);
				history.redoList.push(history.currentAction);	
			}
		}	
		else {
			if (!isUndoEmpty(history)) {
				history.currentAction=history.undoList.pop();	
				invertAndExecute(history);
				history.redoList.push(history.currentAction);	
				
			}
		}
		history.firstUndo=false;
	}

	@Override
	public void redo(History history) {	
		if (!isRedoEmpty(history)) {	
			
			history.currentAction=history.redoList.pop();
			
			invertAndExecute(history);
			
			history.undoList.push(history.currentAction);
			
			history.firstUndo=false;
		}	
	}

	@Override
	public void invertAndExecute(History history) {
		history.currentAction.invert();
		history.currentAction.execute();
	}


	@Override
	public void setState(Action action, History history) {
		if (action.addOrDeleteSystem()==false) {
			if (history.undoList.lastElement().addOrDeleteSystem()==true) {
				store(history);
			}
		}
		
		history.currentAction= action;
		history.firstUndo=false;
	}

	@Override
	public Boolean isUndoEmpty(History history) {
		if (history.firstUndo) {
			if (!history.undoList.isEmpty()) {
				if (history.undoList.lastElement().addOrDeleteSystem()==true) {
					return false;
				}
				else {
					if (history.undoList.size()>=2) {
						return true;
					}
					else {
						return false;
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

	@Override
	public Boolean isRedoEmpty(History history) {
		return history.redoList.isEmpty();	
	}

	@Override
	public void clearRedoHistory(History history) {
		history.redoList.clear();
	}

	@Override
	public void moveIndexForward(History historyManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveIndexBackward(History historyManager) {
		// TODO Auto-generated method stub
		
	}
}
