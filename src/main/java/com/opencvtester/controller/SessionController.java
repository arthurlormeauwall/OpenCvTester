package com.opencvtester.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.opencvtester.controller.filter.ControlledFilter;
import com.opencvtester.controller.filter.FilterDao;
import com.opencvtester.controller.filter.FilterManager;
import com.opencvtester.controller.interfaces.SessionPersistenceDriver;
import com.opencvtester.controller.layer.LayerController;
import com.opencvtester.controller.layer.LayerDao;
import com.opencvtester.entity.FilterData;
import com.opencvtester.entity.LayerData;
import com.opencvtester.entity.Session;

public class SessionController {
	private LayerDao layerDao;
	private FilterDao filterDao;
	private Session session;
	
	private SessionPersistenceDriver sessionPersistenceDriver;
	private MainController guiManager;
	
	public SessionController(FiltersDataBase filtersDataBase, MainController guiManager) {
		session=new Session(new ArrayList<LayerData>(), new ArrayList<FilterData>());
		sessionPersistenceDriver = new SessionFileDriver();
		this.guiManager= guiManager;
		init();
	}
	
	public void init() {
		session=new Session(new ArrayList<LayerData>(), new ArrayList<FilterData>());
		layerDao=new LayerDao();
		layerDao.init(session);
		filterDao=new FilterDao();
		filterDao.init(session);
	}
	
	public void saveSessionAs(String fileName) {
		sessionPersistenceDriver.saveSessionAs(session, fileName);	
	}
	
	public void saveSession() {
		
		try{
			sessionPersistenceDriver.saveSession(session);	
		}catch (FileNotCreatedException e){
			guiManager.launchSaveSessionAs();
		}
	}
	
	public void restoreSession(String fileName, MainController guiManager) {
		init();
		Session sessionTemp = sessionPersistenceDriver.reloadSession(session, fileName);
		session.setTitle(sessionTemp.getTitle());
		guiManager.clearAll();
		buildFromSession(guiManager, sessionTemp);
	}


	private void buildFromSession(MainController guiManager, Session sessionTemp) {
		int numberOfLayer= sessionTemp.getLayers().size();
		for (int i=0;i<numberOfLayer;i++) {
			guiManager.addLayer(guiManager.createLayerManager(sessionTemp.getLayers().get(i)));	
			guiManager.setOpacity(sessionTemp.getLayers().get(i).getLayerIndex(),sessionTemp.getLayers().get(i).getOpacityValue());
		}
		int numberOfFilter= sessionTemp.getFilters().size();
		for (int i=0;i<numberOfFilter;i++) {
			guiManager.addFilter(guiManager.createFilterManager(sessionTemp.getFilters().get(i)));
		}
	}

	public LayerData createLayerData(int layerIndex) {	
		return new LayerData(layerIndex, 1f);
	}
	
	public void addLayer(LayerController layerManager) {
		layerDao.add(layerManager);
	}	
	
	public void deleteLayer(LayerController layerManager) {
		layerDao.delete(layerManager);	
	}

	public void updateOpacity(ControlledFilter opacityFilter, Float opacity) {	
		LinkedHashMap<String, Float> parameters= new LinkedHashMap<String, Float>();
		parameters.put("Opacity", opacity);
		layerDao.update(new LayerData(opacityFilter.layerIndex(), opacity));
	}

	public FilterData createFilter(int layerIndex, int filterIndex, String filterName) {
		return new FilterData(layerIndex,filterIndex, filterName, null);
	}
	
	public void addFilter(FilterManager filterManager) {	
		filterDao.add(filterManager);
	}
	
	public void deleteFilter(FilterManager filterManager) {
		filterDao.delete(filterManager);
	}

	public void updateParameters(ControlledFilter filterToSet, String name, Float value) {
		LinkedHashMap<String, Float> parameters= filterToSet.getParameters();
		parameters.put(name, value);
		filterDao.update(new FilterData(filterToSet.layerIndex(), filterToSet.filterIndex(), filterToSet.getFilterName(), parameters));
	}
	


}
