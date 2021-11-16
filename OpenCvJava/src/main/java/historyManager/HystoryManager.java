package historyManager;

import java.util.Stack;

import historyManager.action.Action;

public class HystoryManager 
{ 
	protected Stack<Action> actions;
	protected int currentIndex;;
	
	
	public HystoryManager(){
		actions=new Stack<Action>();
		currentIndex=0;
		actions.push(null);
	}
		
	public void store() {
		clearUndoHistory();
		if (actions.isEmpty()) {
			actions.push(null);
		}
		else {
			actions.get(currentIndex).invert();
			actions.push(actions.get(currentIndex).clone());
			currentIndex+=1;
		}
	}
	
	public void undo(){
		if (!isUndoEmpty()) {
			currentIndex-=1;
			actions.get(currentIndex).execute();
			actions.get(currentIndex).invert();		
		}
	} 
	
	public void redo(){		
		if (!isRedoEmpty()) {
			currentIndex+=1;
			actions.get(currentIndex).execute();
			actions.get(currentIndex).invert();	
		}
	} 
	 
	public void setState(Action command) {		
		if(actions.size()==0) {
			actions.push(command);
		}
		else if (currentIndex > actions.size()) {
			currentIndex++;
			actions.push(command);
		}
		else {
			actions.set(currentIndex, command);
		}	
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