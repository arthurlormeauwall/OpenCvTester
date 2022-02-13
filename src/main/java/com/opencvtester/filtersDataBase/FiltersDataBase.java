package com.opencvtester.filtersDataBase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Stack;

import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.dataAccess.FilterData;

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
	public FilterControlledByFloat getFilter(String name, FilterData filterData){
		FilterControlledByFloat filter = filters.get(name).createNew();	
		
		if (filterData.getParameterValues()==null) {
			filterData.setParameterValues(filter.getFlags().defaultValues);	
		}

		filter.setData(filterData);
		filter.setAllParameters(filter.getFlags().defaultValues);
		return filter;
	}
	
	public void addFilter(String name, FilterControlledByFloat filter) {
		if (filter!=null) {
			filter.setFilterName(name);
		}
	
		filters.put(name, filter);
	}
	
	public OpacityFilter getOpacityFilter(){
		return new OpacityFilter("Opacity");
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