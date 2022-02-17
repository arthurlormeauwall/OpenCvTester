package com.opencvtester.dataPersistence.interfacesImp;

import java.util.ArrayList;


import com.opencvtester.controller.MainController;
import com.opencvtester.controller.interfaces.PersistenceController;
import com.opencvtester.data.FilterData;
import com.opencvtester.data.LayerData;
import com.opencvtester.dataPersistence.SessionController;
import com.opencvtester.dataPersistence.SessionFileDriver;
import com.opencvtester.dataPersistence.interfaces.SessionPersistenceDriver;
import com.opencvtester.renderer.FiltersDataBase;

class FileNotCreatedException extends RuntimeException{
	private static final long serialVersionUID = 9222458802487115627L;	
}
public class PersistenceCtrlImp implements PersistenceController{
	
	private SessionController session;
	
	private SessionPersistenceDriver sessionPersistenceDriver;
	private MainController guiManager;
	
	public PersistenceCtrlImp(FiltersDataBase filtersDataBase, MainController guiManager) {
		session=new SessionController(new ArrayList<LayerData>(), new ArrayList<FilterData>());
		sessionPersistenceDriver = new SessionFileDriver();
		this.guiManager= guiManager;
		init();
	}
	
	public void init() {
		session=new SessionController(new ArrayList<LayerData>(), new ArrayList<FilterData>());

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
	
	public void openSession(String fileName, MainController guiManager) {
		init();
		SessionController sessionTemp = sessionPersistenceDriver.reloadSession(session, fileName);
		session.setTitle(sessionTemp.getTitle());
		guiManager.clearAll();
		buildFromSession(guiManager, sessionTemp);
	}


	private void buildFromSession(MainController guiManager, SessionController sessionTemp) {
		int numberOfLayer= sessionTemp.getLayers().size();
		for (int i=0;i<numberOfLayer;i++) {
			guiManager.addLayer(guiManager.createLayerManager(sessionTemp.getLayers().get(i)));	
			guiManager.setOpacity(sessionTemp.getLayers().get(i).layerIndex(),sessionTemp.getLayers().get(i).getOpacityValue());
		}
		int numberOfFilter= sessionTemp.getFilters().size();
		for (int i=0;i<numberOfFilter;i++) {
			guiManager.addFilter(guiManager.createFilterManager(sessionTemp.getFilters().get(i)));
		}
	}

}
