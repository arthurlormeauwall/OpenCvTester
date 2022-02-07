package com.opencvtester.guiManager;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.filtersDataBase.FiltersDataBase;

public class FilterFactory {
	private FiltersDataBase filtersDataBase;
	private GuiManager guiManager;

	
	public FilterFactory(FiltersDataBase filtersDataBase, GuiManager guiManager) {
		this.filtersDataBase=filtersDataBase;
		this.guiManager=guiManager;
	}
	
	public FilterManager createFilterFilterManager(int layerIndex, int filterIndex,String filterNamesInDataBase) {
		Filter newFilter = (Filter) filtersDataBase.getFilter(filterNamesInDataBase);
		newFilter.setId(createFilterId(layerIndex,filterIndex));
		return new FilterManager((FilterControlledByFloat) newFilter, guiManager);
	}
	
	public FilterManager createFilterManager(Id id,String filterNamesInDataBase) {
		Filter newFilter = (Filter) filtersDataBase.getFilter(filterNamesInDataBase);
		newFilter.setId(id.clone());
		return new FilterManager((FilterControlledByFloat) newFilter, guiManager);
	}
	
	public static Filter createFilter(Id id,String filterNamesInDataBase, FiltersDataBase db) {
		Filter newFilter = (Filter)  db.getFilter(filterNamesInDataBase);
		newFilter.setId(id.clone());
		return newFilter;
	}
	
	
	private Id createFilterId(int layerIndex, int filterIndex) {
		Id id = new Id();
		id.set(layerIndex, filterIndex);
		return id;
	}
}
