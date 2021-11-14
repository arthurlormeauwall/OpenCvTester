package baseClasses.filter;



import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;


import baseClasses.openCvFacade.Frame;

public abstract class FilterControlledByFrame extends FilterControlledBy<Frame>
{
	public FilterControlledByFrame() {
		initFilterControlledByFrame();
	} 
	
	public void initFilterControlledByFrame() {
		flags.filterName = new String();
		flags.defaultValues= new LinkedHashMap<String, Frame>();
		flags.zeroEffectValues= new LinkedHashMap<String, Frame>();
		flags.numberOfParameters=0;	
	}
	    
	public LinkedHashMap<String, Frame> getParameter() {
		return state;
	}
	
	public void setParameter(LinkedHashMap<String, Frame> frames) {		
		
		Boolean framesAreTheSame=true;
		state=frames;
		
		Iterator<Entry<String, Frame>> new_Iterator= frames.entrySet().iterator();
		
	    while (new_Iterator.hasNext() && framesAreTheSame == true) {
	    	HashMap.Entry<String, Frame> frameItem= (HashMap.Entry<String, Frame>) new_Iterator.next();
	        if (frameItem.getValue().compareTo(state.get(frameItem.getKey()))) {
	        	framesAreTheSame=true;
	        }
	        else {
	        	framesAreTheSame=false;
	        }
	    }
		if (framesAreTheSame) {
			isBypass=true;
		}
		activate();
	}
	
	public void addParameterFlag(String name, Frame defaultValue, Frame zeroEffectValue) {
		flags.defaultValues.put(name, defaultValue);
		flags.zeroEffectValues.put(name, zeroEffectValue);
		flags.numberOfParameters ++;
	}
	public void setZeroEffectValues(LinkedHashMap<String, Frame> parameter) {
		flags.zeroEffectValues=parameter;
	}
}