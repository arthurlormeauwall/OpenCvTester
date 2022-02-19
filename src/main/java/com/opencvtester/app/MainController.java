package com.opencvtester.app;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Stack;

import com.opencvtester.appControllers.DataInterface;
import com.opencvtester.appControllers.HistoryInterface;
import com.opencvtester.appControllers.MainWindowInterface;
import com.opencvtester.appControllers.PersistenceInterface;
import com.opencvtester.appControllers.Renderer;
import com.opencvtester.data.DataController;
import com.opencvtester.dataPersistence.PersistenceController;
import com.opencvtester.gui.controller.FrameWindowController;
import com.opencvtester.gui.controller.MainWindowController;
import com.opencvtester.history.Functionalities;
import com.opencvtester.history.HistoryController;
import com.opencvtester.history.action.AddOrDeleteFilter;
import com.opencvtester.history.action.AddOrDeleteLayer;
import com.opencvtester.history.action.SetParameters;
import com.opencvtester.renderer.ChainOfLayersRenderer;
import com.opencvtester.renderer.ControlledFilter;
import com.opencvtester.renderer.FiltersDataBase;
import com.opencvtester.renderer.Layer;


public class MainController 
{
	private HistoryInterface historyController;
	
	private Renderer renderer;
	
	private DataInterface dataController;
	
	private PersistenceInterface persistenceController;
	
	private MainWindowInterface mainWindow;
		
	private FrameWindowController frameOutWindow;
	
	private FiltersDataBase filtersDataBase;
	

	/*
	 * CONSTRUCTOR & INITS
	 */
	public MainController(String fileName, FiltersDataBase filtersDataBase) throws IOException {
		
		this.filtersDataBase = filtersDataBase;
		
		dataController= new DataController(filtersDataBase);
		renderer = new ChainOfLayersRenderer(fileName, dataController.getLayers());
		mainWindow = new MainWindowController(this, dataController.getLayers());		
		historyController=new HistoryController();
		
		persistenceController = new PersistenceController(filtersDataBase, this);
		
		frameOutWindow=new FrameWindowController(renderer.getFrameOut());
		frameOutWindow.setInMiddleOfScreen();	
	}

	public Stack<Layer> layers(){
		return dataController.getLayers();
	}

	public Stack<String> getFiltersName() {
		return filtersDataBase.getFiltersName();	
	}

	
	public void createAddLayerAndSetHistory(int layerIndex) {		
		addLayer(layerIndex);
		setAddLayerHistory(layers().get(layerIndex));
	}

	public void deleteLayerAndSetHistory(int layerIndex) {			
		setDeleteLayerHistory(layers().get(layerIndex));
		deleteLayer(layerIndex);				
	}
	
	public void addLayer(int layerIndex) {
		dataController.addLayer(layerIndex);
		mainWindow.addLayer(layerIndex);
		renderer.render();
		refreshFrameOut();
	}	
	
	public void addLayer(Layer layer) {
		dataController.addLayer(layer);
		renderer.render();
		mainWindow.addLayer(layer.getIndexData().layerIndex());
		refreshFrameOut();
	}	
	
	public void deleteLayer(int layerIndex) {
		dataController.deleteLayer(layerIndex);
		renderer.render();
		mainWindow.deleteLayer(layerIndex);
		refreshFrameOut();
	}
	
	public void deleteLayer(Layer layer) {
		dataController.deleteLayer(layer.getFilterData().getIndexData().layerIndex());
		renderer.render();
		mainWindow.deleteLayer(layer.getFilterData().getIndexData().layerIndex());
		refreshFrameOut();
	}
	
	public void setAddLayerHistory(Layer layer) {
		AddOrDeleteLayer parameter= new AddOrDeleteLayer (this, layer);
		parameter.setAddOrDelete(Functionalities.ADD);
		historyController.setState(parameter);	
	}
	
	public void setDeleteLayerHistory(Layer layer) {
		AddOrDeleteLayer parameter= new AddOrDeleteLayer (this, layer);
		parameter.setAddOrDelete(Functionalities.DELETE);
		historyController.setState(parameter);	
	}
	
	
	///////////////////////////////////////////////////////////
	
	public void createAddFilterAndSetHistory(int layerIndex, int filterIndex, String filterName) {
		addFilter(layerIndex, filterIndex ,filterName);
		setAddFilterHistory(dataController.getLayers().get(layerIndex).getFilter(filterIndex));
	}	

	public void deleteFilterAndSetHistory(int layerIndex, int filterIndex, String name) {		
		setDeleteFilterHistory(dataController.getLayers().get(layerIndex).getFilter(filterIndex));	
		deleteFilter(layerIndex, filterIndex);			
	}
	
	public void addFilter(int layerIndex, int filterIndex, String filterName) {
		dataController.addFilter(layerIndex, filterIndex,filterName);
		renderer.render();
		mainWindow.addFilter(layerIndex, filterIndex);
		refreshFrameOut();
	}	
	
	public void addFilter(ControlledFilter filter) {
		dataController.addFilter(filter);
		renderer.render();
		mainWindow.addFilter(filter.layerIndex(), filter.filterIndex());
		refreshFrameOut();
	}
	
	public void deleteFilter(int layerIndex, int filterIndex) {
		dataController.deleteFilter(layerIndex, filterIndex);
		renderer.render();
		mainWindow.deleteFilter(layerIndex, filterIndex);
		refreshFrameOut();
	}
	
	public void setAddFilterHistory(ControlledFilter filter) {
		AddOrDeleteFilter parameter= new AddOrDeleteFilter (this, filter);
		parameter.setAddOrDelete(Functionalities.ADD);
		historyController.setState(parameter);
	}
	
	public void setDeleteFilterHistory(ControlledFilter filter) {
		AddOrDeleteFilter parameter= new AddOrDeleteFilter (this, filter);
		parameter.setAddOrDelete(Functionalities.DELETE);
		historyController.setState(parameter);
	}
	
	public void setOpacity(int layerIndex, Float opacity) {
		dataController.setOpacity(layerIndex, opacity);	
		renderer.render();
		refreshFrameOut();
	}

	public void setParametersAndSetHistory(ControlledFilter filterToSet, String name, Float value) throws IOException {
		setSetParameterHistory(filterToSet);
		setParameters(filterToSet, name, value);
	}

	public void setParameters(ControlledFilter filterToSet, String name, Float value) {
		dataController.setParameters(filterToSet, name, value);
		mainWindow.updateFilter(filterToSet.layerIndex(), filterToSet.filterIndex());
		renderer.render();
		refreshFrameOut();
	}
	
	public void setParameters(ControlledFilter filterToSet, LinkedHashMap<String, Float> parameters) {
		dataController.setParameters(filterToSet, parameters);
		mainWindow.updateFilter(filterToSet.layerIndex(), filterToSet.filterIndex());
		renderer.render();
		refreshFrameOut();
	}
	
	public void setParameters(int layerIndex, int filterIndex, LinkedHashMap<String, Float> parameters) {
		dataController.setParameters(layerIndex, filterIndex, parameters);
		mainWindow.updateFilter(layerIndex,filterIndex);
		renderer.render();
		refreshFrameOut();
	}
	
	public void setSetParameterHistory(ControlledFilter filter) {
		SetParameters parameter= new SetParameters(this, filter);
		historyController.setState(parameter);	
	}
	
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass) {
		dataController.setBypass(layerIndex, filterIndex, bypass);
		renderer.render();
		refreshFrameOut();
	}
	
	public void refreshFrameOut(){
		frameOutWindow.refresh(renderer.getFrameOut());	
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
		mainWindow.launchSaveSessionAs();
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
		renderer.render();
		
		refreshFrameOut();
	}

	public void openImage(String fileName) {
		
		renderer.openImage(fileName);
		
		if (dataController.getLayers().size()>0) {
			dataController.checkAndActivateLayer(0);
		}
	
		renderer.render();
		
		frameOutWindow.setInMiddleOfScreen();
		refreshFrameOut();
	}
}
