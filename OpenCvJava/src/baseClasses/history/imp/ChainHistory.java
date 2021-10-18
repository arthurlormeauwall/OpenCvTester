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
         if (!isUndoEmpty() && state!=null) {
        	
         	state.set(undoHistory.peek().getParameter());
            undoHistory.pop();
            
            HistoryParameter<T> parameter = factory.getNew();
            parameter.set(state.clone());  
            parameter.invert();
        	redoHistory.push(parameter);
         } 
     }
     
     public void redo() {
         if (!isRedoEmpty() && state!=null){

          	 state.set(redoHistory.peek().getParameter());
             redoHistory.pop();
             
             HistoryParameter<T> parameter = factory.getNew();
             parameter.set(state.clone());  
             parameter.invert();
         	 undoHistory.push(parameter);	
         }
     }  
}