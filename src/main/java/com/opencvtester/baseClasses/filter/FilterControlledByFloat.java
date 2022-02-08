package com.opencvtester.baseClasses.filter;

import java.util.LinkedHashMap;

public abstract class FilterControlledByFloat extends FilterControlledBy<Float>
{
	protected String name;
	/*
	 * CONSTRUCTOR & INITS & ABSTRACT
	 */
	public FilterControlledByFloat(String name) {
		this.name=name;
		initFilterControlledByFloat();
	} 
	
	@SuppressWarnings("unchecked")
	public void initFilterControlledByFloat() {
		flags.filterName = new String();
		flags.defaultValues= new LinkedHashMap<String, Float>();
		flags.zeroEffectValues= new LinkedHashMap<String, Float>();
		flags.sliderScale = new LinkedHashMap<String, Integer>();
		flags.numberOfParameters=0;
		currentParameters= new LinkedHashMap<String, Float>();
	
		setParameterFlags();
		setFilterName(name);
		setAllParameters((LinkedHashMap<String, Float>)flags.defaultValues.clone());
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
	
	public void addParameterFlag(String name, Float defaultValue, Float zeroEffectValue, int sliderScale) {
	
		flags.defaultValues.put(name, defaultValue);
		flags.zeroEffectValues.put(name, zeroEffectValue);
		flags.numberOfParameters ++;
		flags.sliderScale.put(name, sliderScale);
	}
}