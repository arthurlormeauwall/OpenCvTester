package com.opencvtester.guiManager;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.persistence.FilterData;

public class FilterFactory {
	private FiltersDataBase filtersDataBase;
	private GuiManager guiManager;

	
	public FilterFactory(FiltersDataBase filtersDataBase, GuiManager guiManager) {
		this.filtersDataBase=filtersDataBase;
		this.guiManager=guiManager;
	}
	
	public FilterManager createFilterManager(FilterData filterData) {
		String filterNameInDataBase = filterData.filterNameInDataBase();
		int layerIndex= filterData.layerIndex();
		int filterIndex= filterData.filterIndex();

		
		FilterControlledByFloat newFilter = (FilterControlledByFloat) filtersDataBase.getFilter(filterNameInDataBase);
		newFilter.setId(createFilterId(layerIndex,filterIndex));
		
		if (filterData.parameterValues()!=null) {
			newFilter.setAllParameters(filterData.parameterValues());
		}
		
		return new FilterManager((FilterControlledByFloat) newFilter, guiManager);
	}
	
	public static Filter createFilter(FilterData filterData, FiltersDataBase db) {
		String filterNameInDataBase = filterData.filterNameInDataBase();
		Id id= createFilterId(filterData.layerIndex(), filterData.filterIndex());
		
		Filter newFilter = (Filter)  db.getFilter(filterNameInDataBase);
		newFilter.setId(id.clone());
		return newFilter;
	}
	
	
	private static Id createFilterId(int layerIndex, int filterIndex) {
		Id id = new Id();
		id.set(layerIndex, filterIndex);
		return id;
	}
}
