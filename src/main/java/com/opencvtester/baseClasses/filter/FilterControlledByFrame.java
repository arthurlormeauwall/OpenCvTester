package com.opencvtester.baseClasses.filter;

import java.util.LinkedHashMap;


import com.opencvtester.baseClasses.frame.FrameInterface;

public abstract class FilterControlledByFrame extends FilterControlledBy<FrameInterface>
{
	/*
	 * CONSTRUCTOR & INITS & ABSTRACT
	 */
	public FilterControlledByFrame() {
		initFilterControlledByFrame();
	} 
	
	public void initFilterControlledByFrame() {
		flags.filterName = new String();
		flags.defaultValues= new LinkedHashMap<String, FrameInterface>();
		flags.zeroEffectValues= new LinkedHashMap<String, FrameInterface>();
		flags.numberOfParameters=0;	
	}
	
	/*
	 * GETTERS & SETTERS
	 */    
	public LinkedHashMap<String, FrameInterface> getParameter() {
		return currentParameters;
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