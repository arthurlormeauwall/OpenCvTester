package baseClasses.filter;



import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import openCvAdapter.CvFrame;

public abstract class FilterControlledByFrame extends FilterControlledBy<CvFrame>
{
	public FilterControlledByFrame() {
		initFilterControlledByFrame();
	} 
	
	public void initFilterControlledByFrame() {
		flags.filterName = new String();
		flags.defaultValues= new LinkedHashMap<String, CvFrame>();
		flags.zeroEffectValues= new LinkedHashMap<String, CvFrame>();
		flags.numberOfParameters=0;	
	}
	    
	public LinkedHashMap<String, CvFrame> getParameter() {
		return state;
	}
	
	public void setParameter(LinkedHashMap<String, CvFrame> frames) {		
		
		Boolean framesAreTheSame=true;
		state=frames;
		
		Iterator<Entry<String, CvFrame>> new_Iterator= frames.entrySet().iterator();
		
	    while (new_Iterator.hasNext() && framesAreTheSame == true) {
	    	HashMap.Entry<String, CvFrame> frameItem= (HashMap.Entry<String, CvFrame>) new_Iterator.next();
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
	
	public void addParameterFlag(String name, CvFrame defaultValue, CvFrame zeroEffectValue) {
		flags.defaultValues.put(name, defaultValue);
		flags.zeroEffectValues.put(name, zeroEffectValue);
		flags.numberOfParameters ++;
	}
	public void setZeroEffectValues(LinkedHashMap<String, CvFrame> parameter) {
		flags.zeroEffectValues=parameter;
	}
}