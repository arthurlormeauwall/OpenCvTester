package com.opencvtester.renderer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.opencvtester.data.FilterData;
import com.opencvtester.data.FilterDataInterface;
import com.opencvtester.data.IndexProvider;

public abstract class ControlledFilter extends RendererWithData
{	
	protected FilterDataInterface filterData;
	
	/*
	 * CONSTRUCTOR & INITS & ABSTRACT
	 */
	public ControlledFilter(String name) {		
		filterData=new FilterData(0,0,name);
		
		initFilterControlledByFloat(name);
	} 

	@SuppressWarnings("unchecked")
	public void initFilterControlledByFloat(String name) {
		setParameterFlags();
		setFilterName(name);
		setAllParameters((LinkedHashMap<String, Float>)filterData.getDefaultValues().clone());	
	}
	

	public abstract ControlledFilter createNew();
	public abstract void setParameterFlags();
	
	public FilterDataInterface getFilterData() {
		return filterData;
	}
	
	public void bypass(Boolean bypass){
		filterData.setBypass(bypass);
		filterData.lockedBypass(bypass);
	}
	
	public void setDefaultParameters(String name, Float p) {
		filterData.getDefaultValues().put(name, p);
	}
	
	public void setZeroEffectValues(String name, Float p) {
		filterData.getZeroEffectValues().put(name, p);
	}
	
	public void reset() {
		setAllParameters(filterData.getDefaultValues());
	}
	
	public void setFilterName(String name) {
		filterData.setName(name);
	}
	
	public String getFilterName() {
		return filterData.getName();
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
		temp=(LinkedHashMap<String, Float>)getParameters().clone();
		temp.put(name, value);
		setAllParameters(temp);
	}
	
	public void setAllParameters(LinkedHashMap<String, Float> parameters) {		
		if (filterData.isBypass() && !filterData.isBypassLocked()) {
			filterData.setBypass(false);
		}
		
		filterData.setParameterValues(parameters);	
		boolean parametersAreTheSame = true;
		
		Iterator<Entry<String, Float>> zeroEffectValuesIterator= filterData.getZeroEffectValues().entrySet().iterator();
		
	    while (zeroEffectValuesIterator.hasNext() && parametersAreTheSame == true) {
	    	HashMap.Entry<String, Float> item= (HashMap.Entry<String, Float>) zeroEffectValuesIterator.next();
	    	
	    	if (item.getValue().compareTo(filterData.getParameterValues().get(item.getKey()))!=0) {
	        	parametersAreTheSame=false;
	        }
	    }
		if (parametersAreTheSame) {
			filterData.setBypass(true);
		}
	}
	
	public void addParameterFlag(String name, Float defaultValue, Float zeroEffectValue, int sliderScale) {
	
		filterData.getDefaultValues().put(name, defaultValue);
		filterData.getZeroEffectValues().put(name, zeroEffectValue);
		filterData.getSliderScale().put(name, sliderScale);
	}
	
	public String getName() {
		return filterData.getName();
	}
	
	public int layerIndex() {
		return filterData.getIndexData().layerIndex();
	}
	
	public int filterIndex() {
		return filterData.getIndexData().filterIndex();
	}
	
	public IndexProvider getData() {
		return filterData;
	}
}