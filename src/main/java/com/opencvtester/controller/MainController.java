package com.opencvtester.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;

import com.opencvtester.controller.interfaces.DataController;
import com.opencvtester.controller.interfaces.HistoryController;
import com.opencvtester.controller.interfaces.MainWindowController;
import com.opencvtester.controller.interfaces.PersistenceController;
import com.opencvtester.controller.interfaces.Renderer;
import com.opencvtester.data.interfacesImp.DataCtrlImp;
import com.opencvtester.gui.interfacesImp.MainWindowSwing;
import com.opencvtester.history.Functionalities;
import com.opencvtester.history.action.AddOrDeleteFilter;
import com.opencvtester.history.action.AddOrDeleteLayer;
import com.opencvtester.history.action.SetParameters;
import com.opencvtester.history.interfacesImp.HistoryCtrlImp;
import com.opencvtester.renderer.ControlledFilter;
import com.opencvtester.renderer.FiltersDataBase;
import com.opencvtester.renderer.Layer;
import com.opencvtester.renderer.interfacesImp.ChainOfLayersRenderer;


public class MainController 
{
	private HistoryController historyController;
	
	private Renderer renderer;
	
	private DataController dataController;
	
	private PersistenceController persistenceController;
	
	private MainWindowController mainWindow;
		
	private FrameWindowController frameOutWindow;
	
	private FiltersDataBase filtersDataBase;
	

	/*
	 * CONSTRUCTOR & INITS
	 */
	public MainController(String fileName, FiltersDataBase filtersDataBase) throws IOException {
		
		this.filtersDataBase = filtersDataBase;
		
		dataController= new DataCtrlImp(filtersDataBase);
		renderer = new ChainOfLayersRenderer(fileName, dataController.getLayers());
		
		mainWindow = new MainWindowSwing(this, dataController.getFilters(), dataController.getLayers());		
		
		
		historyController=new HistoryCtrlImp();
		frameOutWindow=new FrameWindowController(renderer.getFrameOut());
		frameOutWindow.setInMiddleOfScreen();	
	}

	public List<Layer> layers(){
		return dataController.getLayers();
	}
	
	public List<ArrayList<ControlledFilter>> filters(){
		return dataController.getFilters();
	}

	public Stack<String> getFiltersName() {
		return filtersDataBase.getFiltersName();	
	}

	
	public void createAddLayerAndSetHistory(int layerIndex) {		
		addLayer(layerIndex);
		setAddLayerHistory(layers().get(layerIndex));
	}

	public void deleteLayerAndSetHistory(int layerIndex) {			
		deleteLayer(layerIndex);			
		setDeleteLayerHistory(layers().get(layerIndex));
	}
	
	public void addLayer(int layerManager) {
		dataController.addLayer(layerManager);
		mainWindow.addLayer(layerManager);
		renderer.render();
		refreshFrameOut();
	}	
	
	public void addLayer(Layer layer) {
		dataController.addLayer(layer);
		
		renderer.render();
		mainWindow.addLayer(layer.getData().layerIndex());
		refreshFrameOut();
	}	
	
	public void deleteLayer(int layerIndex) {
		dataController.deleteLayer(layerIndex);
		
		renderer.render();
		refreshFrameOut();
	}
	
	public void deleteLayer(Layer layer) {
		dataController.deleteLayer(layer.getData().layerIndex());
		
		renderer.render();
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
		setAddFilterHistory(filters().get(layerIndex).get(filterIndex));
	}	

	public void deleteFilterAndSetHistory(int layerIndex, int filterIndex, String name) {		
			deleteFilter(layerIndex, filterIndex);			
			setDeleteFilterHistory(filters().get(layerIndex).get(filterIndex));
	}
	
	public void addFilter(int layerIndex, int filterIndex, String filterName) {
		dataController.addFilter(layerIndex, filterIndex,filterName);

		renderer.render();
		refreshFrameOut();
	}	
	
	public void addFilter(ControlledFilter filter) {
		dataController.addFilter(filter);
		renderer.render();
		refreshFrameOut();
	}
	
	public void deleteFilter(int layerIndex, int filterIndex) {
		dataController.deleteFilter(layerIndex, filterIndex);
	
		renderer.render();
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
		
		setParameters(filterToSet, name, value);
		setSetParameterHistory(filterToSet);
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
		
		renderer.setFrameIn(fileName);
		renderer.render();
		
		frameOutWindow.setInMiddleOfScreen();
		refreshFrameOut();
	}
}
