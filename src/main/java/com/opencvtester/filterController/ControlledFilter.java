package com.opencvtester.filterController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public abstract class ControlledFilter 
{	
	FilterDataInterface data;
	
	/*
	 * CONSTRUCTOR & INITS & ABSTRACT
	 */
	public ControlledFilter(String name) {
		
		initFilterControlledByFloat(name);
	} 

	@SuppressWarnings("unchecked")
	public void initFilterControlledByFloat(String name) {
		
		data.setBypass(false);
		data.setName(name);
		data.setDefaultValues(new LinkedHashMap<String, Float>());
		data.setZeroEffectValues(new LinkedHashMap<String, Float>());
		data.setSliderScale(new LinkedHashMap<String, Float>());
	
		setParameterFlags();
		setFilterName(name);
		setAllParameters((LinkedHashMap<String, Float>)data.getDefaultValues().clone());
	}
	

	public abstract ControlledFilter createNew();
	public abstract void setParameterFlags();
	
	public void bypass(Boolean bypass){
		data.setBypass(bypass);
	}
	
	public void setDefaultParameters(String name, Float p) {
		data.getDefaultValues().put(name, p);
	}
	
	public void setZeroEffectValues(String name, Float p) {
		data.getZeroEffectValues().put(name, p);
	}
	
	public void reset() {
		setAllParameters(data.getDefaultValues());
	}
	
	public void setFilterName(String name) {
		data.setName(name);
	}
	
	public String getFilterName() {
		return data.getName();
	}

	public Float getParameter(String name) {
		return data.getParameterValues().get(name);
	}
	
	public LinkedHashMap<String, Float> getParameters() {
		return data.getParameterValues();
	}
	

	@SuppressWarnings("unchecked")
	public void setParameter(String name, Float value) {
		LinkedHashMap<String, Float> temp= new LinkedHashMap<String, Float>();
		temp=(LinkedHashMap<String, Float>)getParameters().clone();
		temp.put(name, value);
		setAllParameters(temp);
	}
	
	public void setAllParameters(LinkedHashMap<String, Float> parameters) {		
		if (data.isBypass() && !data.isBypassLocked()) {
			data.setBypass(false);
		}
		
		data.setParameterValues(parameters);	
		boolean parametersAreTheSame = true;
		
		Iterator<Entry<String, Float>> zeroEffectValuesIterator= data.getZeroEffectValues().entrySet().iterator();
		
	    while (zeroEffectValuesIterator.hasNext() && parametersAreTheSame == true) {
	    	HashMap.Entry<String, Float> item= (HashMap.Entry<String, Float>) zeroEffectValuesIterator.next();
	    	
	    	if (item.getValue().compareTo(data.getParameterValues().get(item.getKey()))!=0) {
	        	parametersAreTheSame=false;
	        }
	    }
		if (parametersAreTheSame) {
			data.setBypass(true);
		}
	}
	
	public void addParameterFlag(String name, Float defaultValue, Float zeroEffectValue, int sliderScale) {
	
		data.getDefaultValues().put(name, defaultValue);
		data.getZeroEffectValues().put(name, zeroEffectValue);
		data.getSliderScale().put(name, sliderScale);
	}
	

	public String getName() {
		return data.getName();
	}
	
	public int layerIndex() {
		
		return data.layerIndex();
	}
	
	public int filterIndex() {
		return data.filterIndex();
	}
	
	public void activate() {
		data.activate();
	}
}