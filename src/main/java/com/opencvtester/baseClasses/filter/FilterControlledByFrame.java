package com.opencvtester.baseClasses.filter;



import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.opencvtester.baseClasses.frame.FrameInterface;

public abstract class FilterControlledByFrame extends FilterControlledBy<FrameInterface>
{
	public FilterControlledByFrame() {
		initFilterControlledByFrame();
	} 
	
	public void initFilterControlledByFrame() {
		flags.filterName = new String();
		flags.defaultValues= new LinkedHashMap<String, FrameInterface>();
		flags.zeroEffectValues= new LinkedHashMap<String, FrameInterface>();
		flags.numberOfParameters=0;	
	}
	    
	public LinkedHashMap<String, FrameInterface> getParameter() {
		return currentParameters;
	}
	
	public void setParameter(LinkedHashMap<String, FrameInterface> frames) {		
		
		Boolean framesAreTheSame=true;
		currentParameters=frames;
		
		Iterator<Entry<String, FrameInterface>> new_Iterator= frames.entrySet().iterator();
		
	    while (new_Iterator.hasNext() && framesAreTheSame == true) {
	    	HashMap.Entry<String, FrameInterface> frameItem= (HashMap.Entry<String, FrameInterface>) new_Iterator.next();
	        if (frameItem.getValue().compareTo(currentParameters.get(frameItem.getKey()))) {
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
	
	public void addParameterFlag(String name, FrameInterface defaultValue, FrameInterface zeroEffectValue) {
		flags.defaultValues.put(name, defaultValue);
		flags.zeroEffectValues.put(name, zeroEffectValue);
		flags.numberOfParameters ++;
	}
	public void setZeroEffectValues(LinkedHashMap<String, FrameInterface> parameter) {
		flags.zeroEffectValues=parameter;
	}
}