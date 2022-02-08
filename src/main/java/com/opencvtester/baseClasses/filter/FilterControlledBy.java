package com.opencvtester.baseClasses.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.opencvtester.filtersDataBase.FilterFlags;

public abstract class FilterControlledBy<T> extends Filter
{
	protected LinkedHashMap<String, T> currentParameters;
	protected FilterFlags<T> flags;
	
	/*
	 * CONSTRUCTOR & INITS & ABSTRACT
	 */
	public FilterControlledBy() {
		initFilterControlledBy();
	}
	
	private void initFilterControlledBy() {
		flags = new FilterFlags<T>();
		isBypass=false;	
	}

	/*
	 * GETTERS & SETTERS
	 */
	public void setIndex(String indexType, int newValue) {
		id.setFilterOrLayer(indexType, newValue);
	}

	public void bypass(Boolean bypass){
		setBypass(bypass);
	}
	
	public void setNumberOfParamters (int n) {
		flags.numberOfParameters = n;
	}
	
	public void setDefaultParameters(String name, T p) {
		flags.defaultValues.put(name, p);
	}
	
	public void setZeroEffectValues(String name, T p) {
		flags.zeroEffectValues.put(name, p);
	}
	
	public void reset() {
		setAllParameters(flags.defaultValues);
	}
	
	public FilterFlags<T> getFlags() {
		return flags;
	}
	
	@SuppressWarnings("unchecked")
	public void setParameter(String name, T value) {
		LinkedHashMap<String, T> temp= new LinkedHashMap<String, T>();
		temp=(LinkedHashMap<String, T>)currentParameters.clone();
		temp.put(name, value);
		setAllParameters(temp);
	}
	
	@SuppressWarnings("unchecked") // we assume that class 'T' extends Comparable interface, this is the case for Float and Frame classes
	public void setAllParameters(LinkedHashMap<String, T> parameters) {		
		if (isBypass && !isBypassLocked) {
			isBypass=false;
		}
		
		currentParameters=parameters;	
		boolean parametersAreTheSame = true;
		
		Iterator<Entry<String, T>> zeroEffectValuesIterator= flags.zeroEffectValues.entrySet().iterator();
		
	    while (zeroEffectValuesIterator.hasNext() && parametersAreTheSame == true) {
	    	HashMap.Entry<String, Comparable<T>> item= (HashMap.Entry<String, Comparable<T>>) zeroEffectValuesIterator.next();
	    	
	    	if (item.getValue().compareTo(currentParameters.get(item.getKey()))!=0) {
	        	parametersAreTheSame=false;
	        }
	    }
		if (parametersAreTheSame) {
			isBypass=true;
		}
		activate();
		
	}
}
