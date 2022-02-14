package com.opencvtester.guiManager;

import java.io.IOException;
import java.util.Stack;

import com.opencvtester.baseClasses.filter.ControlledFilter;
import com.opencvtester.dataAccess.FilterData;
import com.opencvtester.dataAccess.FilterFactory;
import com.opencvtester.dataAccess.LayerData;
import com.opencvtester.dataAccess.LayerFactory;
import com.opencvtester.dataAccess.SessionManager;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.gui.MainWindow;
import com.opencvtester.historyManager.HistoryManager;
import com.opencvtester.historyManager.action.AddOrDeleteFilter;
import com.opencvtester.historyManager.action.AddOrDeleteLayer;
import com.opencvtester.historyManager.action.Functionalities;
import com.opencvtester.historyManager.action.SetParameters;
import com.opencvtester.renderingEngine.ChainOfLayers;


public class GuiManager 
{
	private HistoryManager history;
	private ChainOfLayers chainOfLayers;
	private MainWindow mainWindow;
	private FrameWindowManager frameOutWindow;
	private FiltersDataBase filtersDataBase;
	private SessionManager sessionManager;
	private LayerFactory layerFactory;
	private FilterFactory filterFactory;

	/*
	 * CONSTRUCTOR & INITS
	 */
	public GuiManager(String fileName, FiltersDataBase filtersDataBase) throws IOException{
		
		this.filtersDataBase = filtersDataBase;
		chainOfLayers = new ChainOfLayers(fileName);
		
		mainWindow = new MainWindow(this);		
		history=new HistoryManager();
		frameOutWindow=new FrameWindowManager(chainOfLayers.getFrameOut());
		frameOutWindow.setInMiddleOfScreen();

		sessionManager= new SessionManager(filtersDataBase, this);
		layerFactory=new LayerFactory(filtersDataBase,this);
		filterFactory=new FilterFactory(filtersDataBase,this);
	}

	
	/*
	 * GETTERS & SETTERS
	 */

	public Stack<String> getFiltersName() {
		return filtersDataBase.getFiltersName();	
	}
	
	
	/*
	 * FEATURES
	 */	

	public void save() {
		sessionManager.saveSession();	
	}
	
	public void createAddLayerAndSetHistory(int layerIndex) {
		LayerManager layerManager = createLayerManager(sessionManager.createLayerData(layerIndex));
		addLayer(layerManager);
		setAddLayerHistory(layerManager);
	}
	
	public LayerManager createLayerManager(LayerData layerData) {
		return layerFactory.createLayerManager(layerData);
	}

	public boolean deleteLayerAndSetHistory(LayerManager layerManager) {		
		if (layerManager!=null) {
			deleteLayer(layerManager);			
			setDeleteLayerHistory(layerManager);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addLayer(LayerManager layerManager) {
		sessionManager.addLayer(layerManager);
		 
		chainOfLayers.addLayer(layerManager.getLayer());			
		mainWindow.addLayerManager(layerManager);
		mainWindow.getGuiManager().refreshFrameOut();
	}	
	
	public void deleteLayer(LayerManager layerManager) {
		sessionManager.deleteLayer(layerManager);
		
		chainOfLayers.deleteLayer(layerManager.getLayer());
		mainWindow.deleteLayerManager(layerManager);	
		refreshFrameOut();
	}
	
	public void setAddLayerHistory(LayerManager layerManager) {
		AddOrDeleteLayer parameter= new AddOrDeleteLayer (this, layerManager);
		parameter.setAddOrDelete(Functionalities.ADD);
		history.setState(parameter);	
	}
	
	public void setDeleteLayerHistory(LayerManager layerManager) {
		AddOrDeleteLayer parameter= new AddOrDeleteLayer (this, layerManager);
		parameter.setAddOrDelete(Functionalities.DELETE);
		history.setState(parameter);	
	}
	
	
	///////////////////////////////////////////////////////////
	
	public void createAddFilterAndSetHistory(int layerIndex, int filterIndex, String filterName) {
		FilterManager filterManager = createFilterManager(sessionManager.createFilter(layerIndex, filterIndex, filterName));
		addFilter( filterManager);
		setAddFilterHistory(filterManager);
	}	
	
	public FilterManager createFilterManager(FilterData filterData) {
		return filterFactory.createFilterManager(filterData);
	}

	public boolean deleteFilterAndSetHistory(FilterManager filterManager) {		
		if (filterManager!=null) {
			
			deleteFilter(filterManager);			
			setDeleteFilterHistory(filterManager);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addFilter(FilterManager filterManager) {
		sessionManager.addFilter(filterManager);
		chainOfLayers.addFilter(filterManager.getFilter());	
		mainWindow.addFilterManager(filterManager);	
		mainWindow.getGuiManager().refreshFrameOut();
	}	
	
	public void deleteFilter(FilterManager filterManager) {
		sessionManager.deleteFilter(filterManager);
		
		chainOfLayers.deleteFilter(filterManager.getFilter());
		mainWindow.deleteFilterManager(filterManager);
		mainWindow.getGuiManager().refreshFrameOut();
	}
	
	public void setAddFilterHistory(FilterManager filterManager) {
		AddOrDeleteFilter parameter= new AddOrDeleteFilter (this, filterManager);
		parameter.setAddOrDelete(Functionalities.ADD);
		history.setState(parameter);
		
	}
	
	public void setDeleteFilterHistory(FilterManager filterManager) {
		AddOrDeleteFilter parameter= new AddOrDeleteFilter (this, filterManager);
		parameter.setAddOrDelete(Functionalities.DELETE);
		history.setState(parameter);
	}
	
	public void setOpacity(ControlledFilter opacityFilter, Float opacity) {	
		sessionManager.updateOpacity(opacityFilter, opacity);
		chainOfLayers.setOpacity(opacityFilter, opacity);
		mainWindow.setOpacity(opacityFilter.layerIndex(), opacity);
		refreshFrameOut();
	}	
	
	public void setOpacity(int layerIndex, Float opacity) {
		ControlledFilter opacityFilter= chainOfLayers.getLayer(layerIndex).getOpacityFilter();
		sessionManager.updateOpacity(opacityFilter, opacity);
		chainOfLayers.setOpacity(opacityFilter, opacity);
		mainWindow.setOpacity(opacityFilter.layerIndex(), opacity);
		refreshFrameOut();
	}

	public void setParametersAndSetHistory(ControlledFilter filterToSet, String name, Float value) throws IOException {
		
		setParameters(filterToSet, name, value);
		setSetParameterHistory(filterToSet);
	}
	
	public void setSetParameterHistory(ControlledFilter filter) {
		SetParameters parameter= new SetParameters(this, chainOfLayers, mainWindow.getChainOfLayerManagers(), filter);
		history.setState(parameter);	
	}
	
	public void setParameters(ControlledFilter filterToSet, String name, Float value) {
		sessionManager.updateParameters(filterToSet, name, value);
		chainOfLayers.setOneParameter (filterToSet, name, value);	
		refreshFrameOut();
	}
	
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass) {
		chainOfLayers.setBypass(layerIndex, filterIndex, bypass);
		refreshFrameOut();
	}
	
	public void refreshFrameOut(){
		frameOutWindow.refresh(chainOfLayers.getFrameOut());	
	}
	
	public void undo() {
		history.undo();	
	}
	
	public void redo() {
		history.redo();
	}
	
	public void store() {	
		history.storeCurrentStateInHistory();
	}


	public void clearAll() {
		chainOfLayers.clearAll();
		mainWindow.clearAll();
		history.clearAll();
		refreshFrameOut();
	}

	public void saveSession() {
		sessionManager.saveSession();
	}
	
	public void saveSessionAs(String fileName) {
		sessionManager.saveSessionAs(fileName);
	}
	
	public void launchSaveSessionAs() {
		mainWindow.saveSessionAs();
	}


	public void openSession(String fileName) {
		sessionManager.restoreSession(fileName, this);
		
	}

	public void openImage(String fileName) {
		chainOfLayers.openImage(fileName);
		frameOutWindow.setInMiddleOfScreen();
		chainOfLayers.execute();
		refreshFrameOut();
	}
}
