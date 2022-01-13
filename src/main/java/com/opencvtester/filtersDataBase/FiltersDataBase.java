package com.opencvtester.filtersDataBase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Stack;

import com.opencvtester.baseClasses.filter.FilterControlledByFloat;

public class FiltersDataBase 
{
	protected LinkedHashMap<String, FilterControlledByFloat> filters;
	protected OpacityFilter alphaFilter;

	/*
	 * CONSTRUCTOR & INITS
	 */
	public FiltersDataBase() {
		filters= new LinkedHashMap<String, FilterControlledByFloat>();
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public FilterControlledByFloat getFilter(String name){
		return filters.get(name).createNew();	
	}
	
	public void addFilter(String name, FilterControlledByFloat filter) {
		filters.put(name, filter);
	}
	
	public OpacityFilter getOpacityFilter(){
		return new OpacityFilter();
	}
	
	public FilterFlags<Float> getFlags(String name){
			return filters.get(name).getFlags();
	}
	
	/*
	 * FEATURES
	 */
	public Stack<String> getFiltersName() {
		Stack<String> filtersName= new Stack<String>();
		Iterator<Entry<String, FilterControlledByFloat>> new_Iterator= filters.entrySet().iterator();
		
	    while (new_Iterator.hasNext()) {
	    	HashMap.Entry<String, FilterControlledByFloat> filterItem= (HashMap.Entry<String, FilterControlledByFloat>) new_Iterator.next();
	    	filtersName.push(filterItem.getKey());
	    }    
	    return filtersName;
	}
}