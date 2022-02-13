package com.opencvtester.filtersDataBase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Stack;

import com.opencvtester.baseClasses.filter.ControlledFilter;
import com.opencvtester.dataAccess.FilterData;

public class FiltersDataBase 
{
	protected LinkedHashMap<String, ControlledFilter> filters;
	protected OpacityFilter alphaFilter;

	/*
	 * CONSTRUCTOR & INITS
	 */
	public FiltersDataBase() {
		filters= new LinkedHashMap<String, ControlledFilter>();
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public ControlledFilter getFilter(String name, FilterData filterData){
		ControlledFilter filter = filters.get(name).createNew();	
		
		if (filterData.getParameterValues()==null) {
			filterData.setParameterValues(filter.getFlags().defaultValues);	
		}

		filter.setData(filterData);
		return filter;
	}
	
	public void addFilter(String name, ControlledFilter filter) {
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
		Iterator<Entry<String, ControlledFilter>> new_Iterator= filters.entrySet().iterator();
		
	    while (new_Iterator.hasNext()) {
	    	HashMap.Entry<String, ControlledFilter> filterItem= (HashMap.Entry<String, ControlledFilter>) new_Iterator.next();
	    	filtersName.push(filterItem.getKey());
	    }    
	    return filtersName;
	}
}