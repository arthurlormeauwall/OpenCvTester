package com.opencvtester.dataAccess;

import com.opencvtester.guiManager.FilterManager;

public class FilterDao implements Dao<FilterManager> {
	
	private Session session;
	
	@Override
	public void init(Session session) {
		this.session=session;	
	}

	@Override
	public void update(DataRecord filterData) {
		FilterData data= (FilterData)filterData;	
		for (int i=0;i<session.getFilters().size();i++) {
			if(session.getFilters().get(i).getLayerIndex()==data.getLayerIndex() && session.getFilters().get(i).getFilterIndex()==data.getFilterIndex()) {
				session.getFilters().set(i, data);
			}
		}
	}

	@Override
	public void add(FilterManager  filterManager) {
		session.getFilters().add(filterManager.getData());	
	}

	@Override
	public void delete(FilterManager  filterManager) {
		session.getFilters().remove(filterManager.getData());	
	}

}
