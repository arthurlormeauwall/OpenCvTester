package com.opencvtester.renderingEngine;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.filtersDataBase.FiltersDataBase;

public class FilterFactory {
	public static Filter createFilter(Id  id,String filterNamesInDataBase, FiltersDataBase filtersDataBase) {
		Filter newFilter = (Filter) filtersDataBase.getFilter(filterNamesInDataBase);
		newFilter.setId(id.clone());
		return newFilter;
	}
}
