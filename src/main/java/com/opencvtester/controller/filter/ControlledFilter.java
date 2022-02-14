package com.opencvtester.controller.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.opencvtester.entity.Filter;
import com.opencvtester.entity.FilterData;
import com.opencvtester.entity.FilterFlags;

public abstract class ControlledFilter extends Filter
{
	protected String name;
	protected FilterFlags<Float> flags;
	protected FilterData filterData;
	
	/*
	 * CONSTRUCTOR & INITS & ABSTRACT
	 */
	public ControlledFilter(String name) {
		this.name=name;
		initFilterControlledBy();
		initFilterControlledByFloat();
	} 
	
	private void initFilterControlledBy() {
		flags = new FilterFlags<Float>();
		isBypass=false;	
	}

	@SuppressWarnings("unchecked")
	public void initFilterControlledByFloat() {
		flags.filterName = new String();
		flags.defaultValues= new LinkedHashMap<String, Float>();
		flags.zeroEffectValues= new LinkedHashMap<String, Float>();
		flags.sliderScale = new LinkedHashMap<String, Integer>();
		flags.numberOfParameters=0;
		filterData=new FilterData(0,0,null);
		
		filterData.setParameterValues(new LinkedHashMap<String, Float>());
	
		setParameterFlags();
		setFilterName(name);
		setAllParameters((LinkedHashMap<String, Float>)flags.defaultValues.clone());
	}
	

	public abstract ControlledFilter createNew();
	public abstract void setParameterFlags();
	
	public void bypass(Boolean bypass){
		setBypass(bypass);
	}
	
	public void setNumberOfParamters (int n) {
		flags.numberOfParameters = n;
	}
	
	public void setDefaultParameters(String name, Float p) {
		flags.defaultValues.put(name, p);
	}
	
	public void setZeroEffectValues(String name, Float p) {
		flags.zeroEffectValues.put(name, p);
	}
	
	public void reset() {
		setAllParameters(flags.defaultValues);
	}
	
	public FilterFlags<Float> getFlags() {
		return flags;
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

	public FilterData getData() {	
		return filterData;
	}

	public void setData(FilterData filterData) {
		this.filterData=filterData;		
		setFilterIndex(filterData.getFilterIndex());
		setLayerIndex(filterData.getLayerIndex());
	}

	public Float getParameter(String name) {
		return filterData.getParameterValues().get(name);
	}
	
	public LinkedHashMap<String, Float> getParameters() {
		return filterData.getParameterValues();
	}
	

	@SuppressWarnings("unchecked")
	public void setParameter(String name, Float value) {
		LinkedHashMap<String, Float> temp= new LinkedHashMap<String, Float>();
		temp=(LinkedHashMap<String, Float>)filterData.getParameterValues().clone();
		temp.put(name, value);
		setAllParameters(temp);
	}
	
	public void setAllParameters(LinkedHashMap<String, Float> parameters) {		
		if (isBypass && !isBypassLocked) {
			isBypass=false;
		}
		
		filterData.setParameterValues(parameters);	
		boolean parametersAreTheSame = true;
		
		Iterator<Entry<String, Float>> zeroEffectValuesIterator= flags.zeroEffectValues.entrySet().iterator();
		
	    while (zeroEffectValuesIterator.hasNext() && parametersAreTheSame == true) {
	    	HashMap.Entry<String, Float> item= (HashMap.Entry<String, Float>) zeroEffectValuesIterator.next();
	    	
	    	if (item.getValue().compareTo(filterData.getParameterValues().get(item.getKey()))!=0) {
	        	parametersAreTheSame=false;
	        }
	    }
		if (parametersAreTheSame) {
			isBypass=true;
		}
		activate();
		
	}
	
	public void addParameterFlag(String name, Float defaultValue, Float zeroEffectValue, int sliderScale) {
	
		flags.defaultValues.put(name, defaultValue);
		flags.zeroEffectValues.put(name, zeroEffectValue);
		flags.numberOfParameters ++;
		flags.sliderScale.put(name, sliderScale);
	}
}