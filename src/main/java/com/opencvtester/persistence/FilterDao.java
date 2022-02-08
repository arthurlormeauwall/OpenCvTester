package com.opencvtester.persistence;

import com.opencvtester.guiManager.FilterFactory;
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
	public FilterManager create(DataRecord data) {
		
		return filterFactory.createFilterManager((FilterData)data);
	}

	@Override
	public void add(FilterManager  filterManager) {
		
		
	}

	@Override
	public void delete(FilterManager  filterManager) {
		
		
	}

}
