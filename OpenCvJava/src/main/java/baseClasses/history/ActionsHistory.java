package baseClasses.history;

import java.util.LinkedList;
import actions.Action;


public class ActionsHistory 
{ 
	protected LinkedList<Action> actions;
	protected int currentIndex;;
	
	
	public ActionsHistory(){
	
		currentIndex=0;
	}
		
	public void store() {
		clearUndoHistory();
		actions.get(currentIndex).invert();
		actions.push(actions.get(currentIndex));
	}
	
	public void undo(){
		currentIndex--;
		actions.get(currentIndex).execute();
		actions.get(currentIndex).invert();

	} 
	
	public void redo(){		
		actions.get(currentIndex).execute();
		actions.get(currentIndex).invert();
		currentIndex++;
	} 
	 
	public void setState(Action command) {
		actions.set(currentIndex, command);
	}
	
	
	public Boolean isUndoEmpty() {
		if (currentIndex>0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public Boolean isRedoEmpty() {
		if (actions.size()-1>currentIndex) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	protected void clearUndoHistory() {
		for (int i = actions.size()-1; i> currentIndex; i--) {
			actions.pop();
		}
	}	
}