package com.opencvtester.dataPersistence.interfacesImp;

import java.util.Stack;

import com.opencvtester.app.MainController;
import com.opencvtester.controller.interfaces.PersistenceController;
import com.opencvtester.data.FilterData;
import com.opencvtester.data.LayerData;
import com.opencvtester.data.interfaces.FilterDataInterface;
import com.opencvtester.dataPersistence.SessionController;
import com.opencvtester.dataPersistence.SessionFileDriver;
import com.opencvtester.dataPersistence.interfaces.SessionPersistenceDriver;
import com.opencvtester.renderer.FiltersDataBase;
import com.opencvtester.renderer.Layer;


public class PersistenceCtrlImp implements PersistenceController{
	
	private SessionController session;
	
	private SessionPersistenceDriver sessionPersistenceDriver;
	private MainController mainController;
	
	public PersistenceCtrlImp(FiltersDataBase filtersDataBase, MainController mainController) {
		init();	
		sessionPersistenceDriver = new SessionFileDriver();
		this.mainController= mainController;
		
	}
	
	public void init() {
		session=new SessionController();
	}
	
	public void saveSessionAs(String fileName) {
		createSession();
		sessionPersistenceDriver.saveSessionAs(session, fileName);	
	}
	
	public void saveSession() {		
		try{
			createSession();
			sessionPersistenceDriver.saveSession(session);	
		}catch (FileNotCreatedException e){
			mainController.launchSaveSessionAs();
		}
	}
	
	private void createSession() {
		session.clear();
		Stack<Layer> layers = mainController.layers();
		
		for (int i= 0 ; i<layers.size();i++) {
			session.getLayers().add(layers.get(i).getFilterData());
			session.getFilters().add(new Stack<FilterDataInterface>());
			int numberOfFilters = layers.get(i).getNumberOfFilters();
			for (int j=0;j<numberOfFilters;j++) {
				session.getFilters().get(i).add(layers.get(i).getFilter(j).getFilterData());
			}
		}	
	}

	public void openSession(String fileName, MainController mainController) {
		init();
		SessionController sessionTemp = sessionPersistenceDriver.reloadSession(session, fileName);
		session.setTitle(sessionTemp.getTitle());
		mainController.clearAll();
		buildFromSession(mainController, sessionTemp);
	}


	private void buildFromSession(MainController mainController, SessionController sessionTemp) {

		int numberOfLayer= sessionTemp.getLayers().size();
		for (int i=0;i<numberOfLayer;i++) {
			LayerData layerData = (LayerData) sessionTemp.getLayers().get(i);
			mainController.addLayer(layerData.getIndexData().layerIndex());	
			
			int numberOfFilters = layerData.getNumberOfFilters();
			for (int j=0;j<numberOfFilters;j++) {
				FilterDataInterface filterData = sessionTemp.getFilters().get(i).get(j);
				mainController.addFilter(filterData.getIndexData().layerIndex(), j, filterData.getName());
				mainController.setParameters(filterData.getIndexData().layerIndex(), filterData.getIndexData().filterIndex(), filterData.getParameterValues());
			}
			mainController.setOpacity(layerData.getIndexData().layerIndex(),layerData.getOpacity());
		}
	}

}
