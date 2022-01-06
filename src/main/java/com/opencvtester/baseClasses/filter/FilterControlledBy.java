package com.opencvtester.baseClasses.filter;

import java.util.LinkedHashMap;

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
	
	public abstract void setParameter(LinkedHashMap<String, T> p);
	
	
	/*
	 * GETTERS & SETTERS
	 */
	public void setIndex(String indexType, int newValue) {
		id.setFilterOrLayer(indexType, newValue);
	}

	public void bypass(Boolean bypass){
		setBypass(bypass);
	}
	
	public void setNames(String name) {
		flags.filterName = name;
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
		setParameter(flags.defaultValues);
	}
	
	public FilterFlags<T> getFlags() {
		return flags;
	}
}
