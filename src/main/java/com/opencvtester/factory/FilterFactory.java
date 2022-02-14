package com.opencvtester.factory;

import com.opencvtester.controller.FiltersDataBase;
import com.opencvtester.controller.MainController;
import com.opencvtester.controller.filter.ControlledFilter;
import com.opencvtester.controller.filter.FilterManager;
import com.opencvtester.entity.Filter;
import com.opencvtester.entity.FilterData;

public class FilterFactory {
	private FiltersDataBase filtersDataBase;
	private MainController guiManager;

	
	public FilterFactory(FiltersDataBase filtersDataBase, MainController guiManager) {
		this.filtersDataBase=filtersDataBase;
		this.guiManager=guiManager;
	}
	
	public FilterManager createFilterManager(FilterData filterData) {
		String filterNameInDataBase = filterData.getFilterNameInDataBase();
		int layerIndex= filterData.getLayerIndex();
		int filterIndex= filterData.getFilterIndex();

		
		ControlledFilter newFilter = (ControlledFilter) filtersDataBase.getFilter(filterNameInDataBase, filterData);
		newFilter.setId(layerIndex,filterIndex);
		
		newFilter.setAllParameters(filterData.getParameterValues());

		return new FilterManager((ControlledFilter) newFilter, guiManager);
	}
	
	public static Filter createFilter(FilterData filterData, FiltersDataBase db) {
		String filterNameInDataBase = filterData.getFilterNameInDataBase();
		
		Filter newFilter = (Filter)  db.getFilter(filterNameInDataBase, filterData);
		newFilter.setId(filterData.getLayerIndex(), filterData.getFilterIndex());
		return newFilter;
	}
}
