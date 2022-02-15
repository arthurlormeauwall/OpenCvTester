package com.opencvtester.renderer.entity;

import com.opencvtester.controller.MainController;
import com.opencvtester.data.FilterData;
import com.opencvtester.filterController.FilterController;
import com.opencvtester.renderer.FiltersDataBase;

public class FilterFactory {
	private FiltersDataBase filtersDataBase;
	private MainController guiManager;

	
	public FilterFactory(FiltersDataBase filtersDataBase, MainController guiManager) {
		this.filtersDataBase=filtersDataBase;
		this.guiManager=guiManager;
	}
	
	public FilterController createFilterManager(FilterData filterData) {
		String filterNameInDataBase = filterData.getFilterNameInDataBase();
		int layerIndex= filterData.getLayerIndex();
		int filterIndex= filterData.getFilterIndex();

		
		ControlledFilter newFilter = (ControlledFilter) filtersDataBase.getFilter(filterNameInDataBase, filterData);
		newFilter.setId(layerIndex,filterIndex);
		
		newFilter.setAllParameters(filterData.getParameterValues());

		return new FilterController((ControlledFilter) newFilter, guiManager);
	}
	
	public static ControlledFilter createFilter(FilterData filterData, FiltersDataBase db) {
		String filterNameInDataBase = filterData.getFilterNameInDataBase();
		
		ControlledFilter newFilter = (ControlledFilter)  db.getFilter(filterNameInDataBase, filterData);
		newFilter.setId(filterData.getLayerIndex(), filterData.getFilterIndex());
		return newFilter;
	}
}
