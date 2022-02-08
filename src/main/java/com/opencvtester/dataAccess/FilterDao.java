package com.opencvtester.dataAccess;

import com.opencvtester.guiManager.FilterManager;

public class FilterDao implements Dao<FilterManager> {

	private FilterFactory filterFactory;
	private Session session;

	public FilterDao(FilterFactory filterFactory) {
		this.filterFactory=filterFactory;
	}
	
	@Override
	public void init(Session session) {
		this.session=session;
		
	}

	@Override
	public void update(FilterManager filterManager) {
		
		
	}

	@Override
	public FilterManager create(DataRecord filterData) {
		
		return filterFactory.createFilterManager((FilterData)filterData);
	}

	@Override
	public void add(FilterManager  filterManager) {
		session.filters().add(filterManager.getData());
		
	}

	@Override
	public void delete(FilterManager  filterManager) {
		
		
	}

}
