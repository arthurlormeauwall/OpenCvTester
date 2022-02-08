package com.opencvtester.dataAccess;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;

import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledBy;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.guiManager.FilterManager;
import com.opencvtester.guiManager.GuiManager;
import com.opencvtester.guiManager.LayerManager;

public class SessionManager {
	private LayerFactory layerFactory;
	private FilterFactory filterFactory;
	private LayerDao layerDao;
	private FilterDao filterDao;
	private Session session;
	
	public SessionManager(FiltersDataBase filtersDataBase, GuiManager guiManager) {
		session=new Session("temp", new ArrayList<LayerData>(), new ArrayList<FilterData>());
		
		filterFactory=new FilterFactory(filtersDataBase, guiManager);
		
		layerDao=new LayerDao(new LayerFactory(filtersDataBase, guiManager));
		layerDao.init(session);
		filterDao=new FilterDao(new FilterFactory(filtersDataBase, guiManager));
		filterDao.init(session);
	}
	
	public void saveSession(String fileName, GuiManager guiManager) {	
	}

	public void reloadSession(String fileName, GuiManager guiManager) {	
	}

	public LayerManager createLayer(int layerIndex, Stack<String> filterNames) {	
		return layerDao.create(new LayerData(layerIndex, 100f, filterNames));
	}

	
	public LayerManager createLayer(int layerIndex) {	
		return layerDao.create(new LayerData(layerIndex, 100f, null));
	}
	
	
	public void addLayer(LayerManager layerManager) {
		layerDao.add(layerManager);
	}	
	
	public void deleteLayer(LayerManager layerManager) {
		layerDao.delete(layerManager);	
	}


	public void updateOpacity(FilterControlledByFloat opacityFilter, Float opacity) {
		LinkedHashMap<String, Float> parameters= new LinkedHashMap<String, Float>();
		parameters.put("Opacity", opacity);
		filterDao.update(new FilterData(opacityFilter.layerIndex(), opacityFilter.filterIndex(), opacityFilter.getFilterName(), parameters));
	}

	public FilterManager createFilter(int layerIndex, int filterIndex, String filterName) {
		return filterDao.create(new FilterData(layerIndex,filterIndex, filterName, null));
	}
	
	public void addFilter(FilterManager filterManager) {	
		filterDao.add(filterManager);
	}
	
	public void deleteFilter(FilterManager filterManager) {
		filterDao.delete(filterManager);
	}


	public void updateParameters(FilterControlledByFloat filterToSet, String name, Float value) {
		LinkedHashMap<String, Float> parameters= new LinkedHashMap<String, Float>();
		parameters.put(name, value);
		filterDao.update(new FilterData(filterToSet.layerIndex(), filterToSet.filterIndex(), filterToSet.getFilterName(), parameters));
	}

}
