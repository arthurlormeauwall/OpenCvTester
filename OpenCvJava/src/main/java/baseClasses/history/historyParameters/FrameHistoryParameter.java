package baseClasses.history.historyParameters;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import baseClasses.history.HistoryParameter;
import baseClasses.openCvFacade.Frame;

public class FrameHistoryParameter implements HistoryParameter<HashMap<String, Frame>> 
{
	protected HashMap<String, Frame> frame;
	
    public FrameHistoryParameter() {
    	frame=new HashMap<String, Frame>();
    }
    
    public FrameHistoryParameter(HashMap<String, Frame> frame) {
    	this.frame=frame;
    }
 
	public FrameHistoryParameter getNew() {
		return new FrameHistoryParameter();
	}
	
	public void invert() {   
	}
	
	public HashMap<String, Frame> getParameter() {
		return frame;
	}
    
	public void set(HashMap<String, Frame> parameter) {
		frame=parameter;		
	}
	
	public HashMap<String, Frame> clone() {
	
		
		@SuppressWarnings("unchecked")
		HashMap<String, Frame> newFrames =(HashMap<String, Frame>)frame.clone();
		
		
		Iterator<Entry<String, Frame>> new_Iterator= newFrames.entrySet().iterator();

		// Iterating every set of entry in the HashMap
	    while (new_Iterator.hasNext()) {
	        HashMap.Entry<String, Frame> new_Map = (HashMap.Entry<String, Frame>) new_Iterator.next();
	        frame.get(new_Map.getKey()).copyTo(new_Map.getValue());
	    }
			return newFrames;
	}
	public Boolean isEmptyObject() {
		if (frame == null) {
			return true;
		}
		else {
			return false;
		}
	}
	public void setToEmptyObject() {
		frame=null;
	}

};