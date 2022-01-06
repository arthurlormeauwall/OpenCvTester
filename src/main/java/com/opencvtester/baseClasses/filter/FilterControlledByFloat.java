package com.opencvtester.baseClasses.filter;


import java.util.LinkedHashMap;


public abstract class FilterControlledByFloat extends FilterControlledBy<Float>
{
	
	/*
	 * CONSTRUCTOR & INITS & ABSTRACT
	 */

	public FilterControlledByFloat() {
		initFilterControlledByFloat();
	} 
	
	@SuppressWarnings("unchecked")
	public void initFilterControlledByFloat() {
		flags.filterName = new String();
		flags.defaultValues= new LinkedHashMap<String, Float>();
		flags.zeroEffectValues= new LinkedHashMap<String, Float>();
		flags.numberOfParameters=0;
		currentParameters= new LinkedHashMap<String, Float>();
	
		setParameterFlags();
		currentParameters=(LinkedHashMap<String, Float>)flags.defaultValues.clone();
	}
	
	public abstract FilterControlledByFloat createNew();
	public abstract void setParameterFlags();

	
	/*
	 * GETTERS & SETTERS
	 */
	public Float getParameter(String name) {
		return currentParameters.get(name);
	}
	
	public LinkedHashMap<String, Float> getParameters() {
		return currentParameters;
	}
	
	public void setParameter(LinkedHashMap<String, Float> parameter) {
		
		currentParameters=parameter;
		if (parameter==flags.zeroEffectValues) {
			isBypass=true;
		}
	}
	
	public void setParameters(String name, Float parameterValue) {		
		currentParameters.put(name, parameterValue);
	}
	
	public void setParameters(String name, Integer value) {
		currentParameters.put(name, value.floatValue());
	}	
	
	public void addParameterFlag(String name, Float defaultValue, Float zeroEffectValue) {
	
		flags.defaultValues.put(name, defaultValue);
		flags.zeroEffectValues.put(name, zeroEffectValue);
		flags.numberOfParameters ++;
	}
	
	public void setFilterName(String name) {
		flags.filterName=name;
	}
	
	public String getFilterName() {
		return flags.filterName;
	}
	
	public void setEmptyFlags() {
		flags.numberOfParameters=0;
	}
}