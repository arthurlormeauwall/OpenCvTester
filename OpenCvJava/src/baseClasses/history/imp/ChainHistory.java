package baseClasses.history.imp;

import baseClasses.history.History;
import baseClasses.history.HistoryParameter;

public class ChainHistory<T> extends History<T>
{
	public ChainHistory(){
	}
	
     public void store() { 	
		 state.invert();	 
		 HistoryParameter<T> parameter = factory.getNew();
		 parameter.set(state.clone());  
		 undoHistory.push(parameter);
		 clearRedoHistory();
     }
     
     public void undo() {
         if (!empty() && state!=null) {
        	state.invert();
        	redoHistory.push(state);
         	state.set(undoHistory.peek().getParameter());
            undoHistory.pop();
         } 
     }
     
     public void redo() {
         if (!isRedoEmpty() && state!=null){
         	 HistoryParameter<T> n;
             n = redoHistory.peek();
             state = n;
             redoHistory.peek().invert();
             undoHistory.push(redoHistory.peek());
             redoHistory.pop();
         }
     }  
}