package com.opencvtester.guiManager;

import java.io.IOException;
import java.util.Stack;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.baseClasses.frame.Frame;
import com.opencvtester.baseClasses.frame.FrameInterface;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.gui.MainWindow;
import com.opencvtester.historyManager.HistoryManager;
import com.opencvtester.historyManager.action.AddOrDeleteFilter;
import com.opencvtester.historyManager.action.AddOrDeleteLayer;
import com.opencvtester.historyManager.action.Functionalities;
import com.opencvtester.historyManager.action.SetParameters;
import com.opencvtester.renderingEngine.ChainOfLayers;
import com.opencvtester.renderingEngine.Layer;


public class GuiManager 
{
	private HistoryManager history;
	private ChainOfLayers chainOfLayers;
	private MainWindow mainWindow;
	private FrameWindowManager frameOutWindow;
	
	private FrameInterface frameIn;
	private FrameInterface backgroundFrame;
	private FrameInterface frameOut;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public GuiManager(String fileName) throws IOException{
		
		chainOfLayersInitializer(fileName);
		
		mainWindow = new MainWindow(this);
		
		history=new HistoryManager();
		frameOutWindow=new FrameWindowManager(chainOfLayers.getFrameOut());
	}

	
	/*
	 * GETTERS & SETTERS
	 */

	public Stack<String> getFiltersName() {
		return chainOfLayers.getFilterDataBase().getFiltersName();	
	}
	
	/*
	 * FEATURES
	 */
	
	public void chainOfLayersInitializer(String fileName) throws IOException {	
		backgroundFrame = new Frame();
		frameOut =  new Frame();
		frameIn =  new Frame();	
		
		frameIn.readFromFile(fileName);
		backgroundFrame.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
		frameIn.copyTo(frameOut);
		
		Id chainOfLayersId = new Id();
		chainOfLayersId.initNULL();
		
		chainOfLayers = new ChainOfLayers(new FiltersDataBase(), backgroundFrame, chainOfLayersId);
		
		chainOfLayers.setFrameIn(frameIn);
		chainOfLayers.setFrameOut(frameOut);
	}

	
	public void addFilterInDatabase(String name, FilterControlledByFloat filter) {
		chainOfLayers.getFiltersDataBase().addFilter(name, filter);
	}
	
	
	private Id createLayerId(int layerIndex) {	
		Id id = new Id();
		id.set(layerIndex, 0);
		return id;
	}	
	
	private Id createFilterId(int layerIndex, int filterIndex) {
		Id id = new Id();
		id.set(layerIndex, filterIndex);
		return id;
	}
	
	public void createAddLayerAndSetState(int layerIndex, Stack<String> filterNames) {
		LayerManager layerManager = new LayerManager(createLayer(layerIndex, filterNames), this);	
		addLayer(layerManager);
		setAddLayerState(layerManager);
	}
	
	public void createAddLayerAndSetState(int layerIndex) {
		LayerManager layerManager = new LayerManager(createEmptyLayer(layerIndex), this);	
		addLayer(layerManager);
		setAddLayerState(layerManager);
	}
	

	public boolean deleteLayerAndSetState(LayerManager layerManager) {		
		if (layerManager!=null) {
			deleteLayer(layerManager);			
			setDeleteLayerState(layerManager);
			return true;
		}
		else {
			return false;
		}
	}
	
	public Layer createLayer(int layerIndex, Stack<String> filterNames) {
		Stack<Id> id= new Stack<Id>();
		id.push(createLayerId(layerIndex));
		
		if (filterNames!=null) {
			for (int i=0; i< filterNames.size(); i++) {
				id.push(createFilterId(layerIndex, i));
			}
		}
		
		Layer newLayer= chainOfLayers.createLayer(id, filterNames);
		
		return newLayer;
	}
	
	public Layer createEmptyLayer(int layerIndex) {
		Layer newLayer= chainOfLayers.createEmptyLayer(layerIndex);
		
		return newLayer;
	}
	
	public void addLayer(LayerManager layerManager) {
		chainOfLayers.addLayer(layerManager.getLayer());			
		layerManager.createLayerWindow();
		mainWindow.addLayerManager(layerManager);
		mainWindow.getGuiManager().refreshFrameOut();
	}	
	
	public void deleteLayer(LayerManager layerManager) {
		chainOfLayers.deleteLayer(layerManager.getLayer());
		mainWindow.deleteLayerManager(layerManager);	
		refreshFrameOut();
	}
	
	public void setAddLayerState(LayerManager layerManager) {
		AddOrDeleteLayer parameter= new AddOrDeleteLayer (this, layerManager);
		parameter.setAddOrDelete(Functionalities.ADD);
		history.setState(parameter);	
	}
	
	public void setDeleteLayerState(LayerManager layerManager) {
		AddOrDeleteLayer parameter= new AddOrDeleteLayer (this, layerManager);
		parameter.setAddOrDelete(Functionalities.DELETE);
		history.setState(parameter);	
	}
	
	
	///////////////////////////////////////////////////////////
	
	public void createAddFilterAndSetState(int layerIndex, int filterIndex, String filterName) {
		FilterManager filterManager = new FilterManager(createFilter(layerIndex, filterIndex, filterName), this);	
		addFilter( filterManager);
		setAddFilterState(filterManager);
	}
		

	public boolean deleteFilterAndSetState(FilterManager filterManager) {		
		if (filterManager!=null) {
			deleteFilter(filterManager);			
			setDeleteFilterState(filterManager);
			return true;
		}
		else {
			return false;
		}
	}
	
	public FilterControlledByFloat createFilter(int layerIndex, int filterIndex, String filterName) {
		Id id= createFilterId(layerIndex, filterIndex);
		FilterControlledByFloat newFilter = (FilterControlledByFloat) chainOfLayers.createFilter(id, filterName);
		return newFilter;
	}

	
	public void addFilter(FilterManager filterManager) {
		chainOfLayers.addFilter(filterManager.getFilter());	
		mainWindow.addFilterManager(filterManager);	
		mainWindow.getGuiManager().refreshFrameOut();
	}	
	
	public void deleteFilter(FilterManager filterManager) {
		chainOfLayers.deleteFilter(filterManager.getFilter());
		mainWindow.deleteFilterManager(filterManager);
		mainWindow.getGuiManager().refreshFrameOut();
	}
	
	public void setAddFilterState(FilterManager filterManager) {
		AddOrDeleteFilter parameter= new AddOrDeleteFilter (this, filterManager);
		parameter.setAddOrDelete(Functionalities.ADD);
		history.setState(parameter);
		
	}
	
	public void setDeleteFilterState(FilterManager filterManager) {
		AddOrDeleteFilter parameter= new AddOrDeleteFilter (this, filterManager);
		parameter.setAddOrDelete(Functionalities.DELETE);
		history.setState(parameter);
	}
	
	public void setOpacity(Filter opacityFilter, Float opacity) {	
		chainOfLayers.setOpacity(opacityFilter, opacity);
		refreshFrameOut();
	}	

	public void setParametersAndSetState(FilterControlledByFloat filterToSet, String name, Float value) throws IOException {
		chainOfLayers.setOneParameter (filterToSet, name, value);	
		refreshFrameOut();
		
		 setStateSetParameter(filterToSet);
	}
	
	public void setStateSetParameter(FilterControlledByFloat filter) {
		SetParameters parameter= new SetParameters(this, chainOfLayers, mainWindow.getChainOfLayerManagers(), filter);
		history.setState(parameter);	
	}
	
	public void setParameters(FilterControlledByFloat filterToSet, String name, Float value) throws IOException {
		chainOfLayers.setOneParameter (filterToSet, name, value);	
		refreshFrameOut();
	}
	
	
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass) {
		chainOfLayers.setBypass(createFilterId(layerIndex, filterIndex), bypass);
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
}
