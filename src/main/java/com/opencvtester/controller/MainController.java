package com.opencvtester.controller;

import java.io.IOException;
import java.util.Stack;

import com.opencvtester.controller.interfaces.DataController;
import com.opencvtester.controller.interfaces.HistoryController;
import com.opencvtester.controller.interfaces.MainWindow;
import com.opencvtester.controller.interfaces.PersistenceController;
import com.opencvtester.controller.interfaces.RendererController;
import com.opencvtester.controller.layer.LayerController;
import com.opencvtester.data.FilterData;
import com.opencvtester.data.LayerData;
import com.opencvtester.data.interfacesImp.DataCtrlImp;
import com.opencvtester.filterController.FilterController;
import com.opencvtester.gui.interfacesImp.MainWindowSwing;
import com.opencvtester.history.Functionalities;
import com.opencvtester.history.action.AddOrDeleteFilter;
import com.opencvtester.history.action.AddOrDeleteLayer;
import com.opencvtester.history.action.SetParameters;
import com.opencvtester.history.interfacesImp.HistoryCtrlImp;
import com.opencvtester.renderer.FiltersDataBase;
import com.opencvtester.renderer.entity.ControlledFilter;
import com.opencvtester.renderer.entity.FilterFactory;
import com.opencvtester.renderer.entity.LayerFactory;
import com.opencvtester.renderer.interfacesImp.RendererImp;


public class MainController 
{
	
	
	private HistoryController historyController;
	
	private RendererController rendererController;
	
	private DataController dataController;
	
	private PersistenceController persistenceController;
	
	private MainWindow mainWindow;
	

	
	private FrameWindowController frameOutWindow;
	
	private FiltersDataBase filtersDataBase;

	
	
	private LayerFactory layerFactory;
	private FilterFactory filterFactory;

	/*
	 * CONSTRUCTOR & INITS
	 */
	public MainController(String fileName, FiltersDataBase filtersDataBase) throws IOException{
		
		this.filtersDataBase = filtersDataBase;
		
		rendererController = new RendererImp(fileName);
		
		mainWindow = new MainWindowSwing(this);		
		historyController=new HistoryCtrlImp();
		frameOutWindow=new FrameWindowController(rendererController.getFrameOut());
		frameOutWindow.setInMiddleOfScreen();

		dataController= new DataCtrlImp();
		layerFactory=new LayerFactory(filtersDataBase,this);
		filterFactory=new FilterFactory(filtersDataBase,this);
	}


	public Stack<String> getFiltersName() {
		return filtersDataBase.getFiltersName();	
	}

	
	public void createAddLayerAndSetHistory(int layerIndex) {
		
		LayerController layerManager = createLayerManager(dataController.createLayerData(layerIndex));
		
		
		addLayer(layerManager);
		setAddLayerHistory(layerManager);
	}
	
	public LayerController createLayerManager(LayerData layerData) {
		return layerFactory.createLayerManager(layerData);
	}

	public boolean deleteLayerAndSetHistory(LayerController layerManager) {		
		if (layerManager!=null) {
			deleteLayer(layerManager);			
			setDeleteLayerHistory(layerManager);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addLayer(LayerController layerManager) {
		dataController.addLayer(layerManager);
		
		rendererController.render();
		mainWindow.updateGui();
		refreshFrameOut();
	}	
	
	public void deleteLayer(LayerController layerManager) {
		dataController.deleteLayer(layerManager);
		
		rendererController.render();
		mainWindow.updateGui();	
		refreshFrameOut();
	}
	
	public void setAddLayerHistory(LayerController layerManager) {
		AddOrDeleteLayer parameter= new AddOrDeleteLayer (this, layerManager);
		parameter.setAddOrDelete(Functionalities.ADD);
		historyController.setState(parameter);	
	}
	
	public void setDeleteLayerHistory(LayerController layerManager) {
		AddOrDeleteLayer parameter= new AddOrDeleteLayer (this, layerManager);
		parameter.setAddOrDelete(Functionalities.DELETE);
		historyController.setState(parameter);	
	}
	
	
	///////////////////////////////////////////////////////////
	
	public void createAddFilterAndSetHistory(int layerIndex, int filterIndex, String filterName) {
		FilterController filterManager = createFilterManager(dataController.createFilter(layerIndex, filterIndex, filterName));
		addFilter( filterManager);
		setAddFilterHistory(filterManager);
	}	
	
	public FilterController createFilterManager(FilterData filterData) {
		return filterFactory.createFilterManager(filterData);
	}

	public boolean deleteFilterAndSetHistory(FilterController filterManager) {		
		if (filterManager!=null) {
			
			deleteFilter(filterManager);			
			setDeleteFilterHistory(filterManager);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addFilter(FilterController filterManager) {
		dataController.addFilter(filterManager);

		rendererController.render();
		mainWindow.updateGui();
		refreshFrameOut();
	}	
	
	public void deleteFilter(FilterController filterManager) {
		dataController.deleteFilter(filterManager);
	
		rendererController.render();
		mainWindow.updateGui();
		refreshFrameOut();
	}
	
	public void setAddFilterHistory(FilterController filterManager) {
		AddOrDeleteFilter parameter= new AddOrDeleteFilter (this, filterManager);
		parameter.setAddOrDelete(Functionalities.ADD);
		historyController.setState(parameter);
		
	}
	
	public void setDeleteFilterHistory(FilterController filterManager) {
		AddOrDeleteFilter parameter= new AddOrDeleteFilter (this, filterManager);
		parameter.setAddOrDelete(Functionalities.DELETE);
		historyController.setState(parameter);
	}
	
	public void setOpacity(int layerIndex, Float opacity) {
		dataController.setOpacity(layerIndex, opacity);
		
		rendererController.render();
		mainWindow.updateGui();
		refreshFrameOut();
	}

	public void setParametersAndSetHistory(ControlledFilter filterToSet, String name, Float value) throws IOException {
		
		setParameters(filterToSet, name, value);
		setSetParameterHistory(filterToSet);
	}
	
	public void setSetParameterHistory(ControlledFilter filter) {
		SetParameters parameter= new SetParameters(this, rendererController, mainWindow.getChainOfLayerManagers(), filter);
		historyController.setState(parameter);	
	}
	
	public void setParameters(ControlledFilter filterToSet, String name, Float value) {
		dataController.setParameters(filterToSet, name, value);
		
		rendererController.render();
		mainWindow.updateGui();
		refreshFrameOut();
	}
	
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass) {
		dataController.setBypass(layerIndex, filterIndex, bypass);
		rendererController.render();
		mainWindow.updateGui();
		refreshFrameOut();
	}
	
	public void refreshFrameOut(){
		frameOutWindow.refresh(rendererController.getFrameOut());	
	}
	
	public void undo() {
		historyController.undo();	
	}
	
	public void redo() {
		historyController.redo();
	}
	
	public void store() {	
		historyController.storeCurrentStateInHistory();
	}

	
	public void saveSession() {
		persistenceController.saveSession();
	}
	
	public void saveSessionAs(String fileName) {
		persistenceController.saveSessionAs(fileName);
	}
	
	public void launchSaveSessionAs() {
		mainWindow.saveSessionAs();
	}

	public void save() {
		persistenceController.saveSession();	
	}

	public void openSession(String fileName) {
		persistenceController.openSession(fileName, this);
	}

	public void clearAll() {
		dataController.clearAll();
		
		mainWindow.clearAll();
		historyController.clearAll();
		rendererController.render();
		
		refreshFrameOut();
	}

	public void openImage(String fileName) {
		rendererController.openImage(fileName);
		rendererController.render();
		
		frameOutWindow.setInMiddleOfScreen();
		refreshFrameOut();
	}
}
