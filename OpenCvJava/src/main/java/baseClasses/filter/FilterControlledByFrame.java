package baseClasses.filter;



import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import baseClasses.frame.FrameCv;

public abstract class FilterControlledByFrame extends FilterControlledBy<FrameCv>
{
	public FilterControlledByFrame() {
		initFilterControlledByFrame();
	} 
	
	public void initFilterControlledByFrame() {
		flags.filterName = new String();
		flags.defaultValues= new LinkedHashMap<String, FrameCv>();
		flags.zeroEffectValues= new LinkedHashMap<String, FrameCv>();
		flags.numberOfParameters=0;	
	}
	    
	public LinkedHashMap<String, FrameCv> getParameter() {
		return state;
	}
	
	public void setParameter(LinkedHashMap<String, FrameCv> frames) {		
		
		Boolean framesAreTheSame=true;
		state=frames;
		
		Iterator<Entry<String, FrameCv>> new_Iterator= frames.entrySet().iterator();
		
	    while (new_Iterator.hasNext() && framesAreTheSame == true) {
	    	HashMap.Entry<String, FrameCv> frameItem= (HashMap.Entry<String, FrameCv>) new_Iterator.next();
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
	
	public void addParameterFlag(String name, FrameCv defaultValue, FrameCv zeroEffectValue) {
		flags.defaultValues.put(name, defaultValue);
		flags.zeroEffectValues.put(name, zeroEffectValue);
		flags.numberOfParameters ++;
	}
	public void setZeroEffectValues(LinkedHashMap<String, FrameCv> parameter) {
		flags.zeroEffectValues=parameter;
	}
}