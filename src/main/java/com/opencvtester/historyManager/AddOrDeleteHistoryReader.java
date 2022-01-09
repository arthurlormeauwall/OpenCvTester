package com.opencvtester.historyManager;

import com.opencvtester.historyManager.action.Action;

public class AddOrDeleteHistoryReader implements HistoryReader {

	@Override
	public void store(History history) {
		clearRedoHistory(history);
		if (history.actions.get(history.currentIndex)!=null) {
			history.actions.push(history.actions.get(history.currentIndex).clone());	
			history.currentIndex+=1;
		}
	}

	@Override
	public void undo(History history) {
		if (isUndoEmpty(history)==false) {
			if (history.firstUndo) {
				invertAndExecute(history);
				history.firstUndo=false;
			}
			else {
				
				moveIndexBackward(history);
				invertAndExecute(history);
			}		
		}
		history.firstRedo=true;
	}

	@Override
	public void redo(History history) {
		if (isRedoEmpty(history)==false) {
			if (history.firstRedo) {
				invertAndExecute(history);
				history.firstRedo=false;
			}
			else {
				
				moveIndexForward(history);
			    invertAndExecute(history);	
			}
		}
		
		history.firstUndo=true;
	}

	@Override
	public void invertAndExecute(History history) {
		history.actions.get(history.currentIndex).invert();
		history.actions.get(history.currentIndex).execute();
	}

	@Override
	public void moveIndexForward(History history) {
		history.currentIndex+=1;
	}

	@Override
	public void moveIndexBackward(History history) {
		history.currentIndex-=1;
	}

	@Override
	public void setState(Action action, History history) {
		// if the last action performed is not an action that lockIndex (typically : SetParameter and SetOpacity)
		// then we should begin by storing the current state 
		if (history.actions.get(history.currentIndex)!=null){
			if (history.actions.get(history.currentIndex).lockedSystem()==false) {
				history.actions.get(history.currentIndex).getHistoryReader().store(history);
			}
		}

		history.actions.set(history.currentIndex, action);	
	}

	@Override
	public Boolean isUndoEmpty(History history) {
		if (history.currentIndex>0) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public Boolean isRedoEmpty(History history) {
		if (history.currentIndex<history.actions.size()-2) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public void clearRedoHistory(History history) {
		for (int i = history.actions.size()-1; i>= history.currentIndex+1; i--) {
			history.actions.pop();
		}
	}
}
