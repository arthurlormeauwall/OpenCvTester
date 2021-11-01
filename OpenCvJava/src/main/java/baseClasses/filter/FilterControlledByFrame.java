package baseClasses.filter;



import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import baseClasses.history.historyParameters.FrameHistoryParameter;
import baseClasses.history.imp.ParametersHistory;
import baseClasses.openCvFacade.Frame;

public abstract class FilterControlledByFrame extends FilterControlledBy<Frame>
{
	public FilterControlledByFrame() {
		initFilterControlledByFrame();
	} 
	
	public void initFilterControlledByFrame() {
		flags.filterName = new String();
		flags.defaultValues= new HashMap<String, Frame>();
		flags.zeroEffectValues= new HashMap<String, Frame>();
		flags.numberOfParameters=0;
		history = new ParametersHistory<HashMap<String, Frame>>();
		history.initFactory(new FrameHistoryParameter());
		history.initState(new FrameHistoryParameter());
	}
	    
	public HashMap<String, Frame> getParameter() {
		return history.getState().getParameter();
	}
	
	public void setParameter(HashMap<String, Frame> frame) {		
		
		Boolean framesAreTheSame=true;
		history.setState(new FrameHistoryParameter(frame));
		
		Iterator<Entry<String, Frame>> new_Iterator= frame.entrySet().iterator();
		
	    while (new_Iterator.hasNext() && framesAreTheSame == true) {
	        HashMap.Entry<String, Frame> frameItem= (HashMap.Entry<String, Frame>) new_Iterator.next();
	        if (frameItem.getValue().compareTo(history.getState().getParameter().get(frameItem.getKey()))) {
	        	framesAreTheSame=true;
	        }
	        else {
	        	framesAreTheSame=false;
	        }
	    }
		if (framesAreTheSame) {
			isBypass=true;
		}
		UpdateRender();
		UpdateUndo();
	}
	
	public void addParameterFlag(String name, Frame defaultValue, Frame zeroEffectValue) {
		flags.defaultValues.put(name, defaultValue);
		flags.zeroEffectValues.put(name, zeroEffectValue);
		flags.numberOfParameters ++;
	}
	public void setZeroEffectValues(HashMap<String, Frame> parameter) {
		flags.zeroEffectValues=parameter;
	}
}