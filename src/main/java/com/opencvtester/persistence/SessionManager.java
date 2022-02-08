package com.opencvtester.persistence;

import java.util.Stack;

import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.guiManager.FilterFactory;
import com.opencvtester.guiManager.FilterManager;
import com.opencvtester.guiManager.GuiManager;
import com.opencvtester.guiManager.LayerFactory;
import com.opencvtester.guiManager.LayerManager;

public class SessionManager {
	private LayerFactory layerFactory;
	private FilterFactory filterFactory;
	private LayerDao layerDao;
	private FilterDao filterDao;
	private Session session;
	
	public SessionManager(FiltersDataBase filtersDataBase, GuiManager guiManager) {
		filterFactory=new FilterFactory(filtersDataBase, guiManager);
		
		layerDao=new LayerDao(new LayerFactory(filtersDataBase, guiManager));
		layerDao.init(session);
	}
	
	public void saveSession(String fileName, GuiManager guiManager) {
		// TODO Auto-generated method stub
		
	}

	public void reloadSession(String fileName, GuiManager guiManager) {
		// TODO Auto-generated method stub
		
	}

	public LayerManager createLayer(int layerIndex, Stack<String> filterNames) {
		
		return layerDao.create(new LayerData(layerIndex, 100f, filterNames));
	}

	public LayerManager createLayer(int layerIndex) {
		
		return layerDao.create(new LayerData(layerIndex, 100f, null));
	}
	
	public void deleteLayer(LayerManager layerManager) {
		
		
	}
	
	public void addLayer(LayerManager layerManager) {
		// TODO Auto-generated method stub		
	}	

	public FilterManager createFilter(int layerIndex, int filterIndex, String filterName) {
		
		return filterDao.create(new FilterData(layerIndex,filterIndex, filterName, null));
	}
	
	public void addFilter(FilterManager filterManager) {
		
		
	}
	
	public void deleteFilter(FilterManager filterManager) {
		
	}

	public void updateOpacity(Filter opacityFilter, Float opacity) {
		
		
	}

	public void updateParameters(FilterControlledByFloat filterToSet, String name, Float value) {
		// TODO Auto-generated method stub
		
	}

}
