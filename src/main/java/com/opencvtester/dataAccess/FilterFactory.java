package com.opencvtester.dataAccess;

import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.guiManager.FilterManager;
import com.opencvtester.guiManager.GuiManager;

public class FilterFactory {
	private FiltersDataBase filtersDataBase;
	private GuiManager guiManager;

	
	public FilterFactory(FiltersDataBase filtersDataBase, GuiManager guiManager) {
		this.filtersDataBase=filtersDataBase;
		this.guiManager=guiManager;
	}
	
	public FilterManager createFilterManager(FilterData filterData) {
		String filterNameInDataBase = filterData.getFilterNameInDataBase();
		int layerIndex= filterData.getLayerIndex();
		int filterIndex= filterData.getFilterIndex();

		
		FilterControlledByFloat newFilter = (FilterControlledByFloat) filtersDataBase.getFilter(filterNameInDataBase, filterData);
		newFilter.setId(layerIndex,filterIndex);
		
		newFilter.setAllParameters(filterData.getParameterValues());

		return new FilterManager((FilterControlledByFloat) newFilter, guiManager);
	}
	
	public static Filter createFilter(FilterData filterData, FiltersDataBase db) {
		String filterNameInDataBase = filterData.getFilterNameInDataBase();
		
		Filter newFilter = (Filter)  db.getFilter(filterNameInDataBase, filterData);
		newFilter.setId(filterData.getLayerIndex(), filterData.getFilterIndex());
		return newFilter;
	}
}
