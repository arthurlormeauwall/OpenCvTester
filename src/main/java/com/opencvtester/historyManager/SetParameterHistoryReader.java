package com.opencvtester.historyManager;

import com.opencvtester.historyManager.action.Action;

public class SetParameterHistoryReader implements HistoryReader {

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
			moveIndexBackward(history);
			invertAndExecute(history);		
			history.firstRedo=true;
			if (history.firstUndo) {
		    	history.firstUndo=false;
		    }
		}
	}

	@Override
	public void redo(History history) {
		if (isRedoEmpty(history)==false) {
			moveIndexForward(history);
		    invertAndExecute(history);
		    history.firstUndo=true;
		    if (history.firstRedo) {
		    	history.firstRedo=false;
		    }
		}
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
		if (history.currentIndex<history.actions.size()-1) {
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
