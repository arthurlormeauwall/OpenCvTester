package com.opencvtester.renderingEngine;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.filtersDataBase.FiltersDataBase;

public class FilterFactory {
	
	private FiltersDataBase filtersDataBase;
	
	public FilterFactory(FiltersDataBase filtersDataBase) {
		this.filtersDataBase= filtersDataBase;
	}
	public Filter createFilter(Id  id,String commandsNamesInDataBase) {
		return getFilterFromDatabase(id, commandsNamesInDataBase);
	}
	
	public Filter getFilterFromDatabase(Id id, String filterNamesInDataBase){
		Filter newFilter = (Filter) filtersDataBase.getFilter(filterNamesInDataBase);
		newFilter.setId(id.clone());
		return newFilter;
	}
}
