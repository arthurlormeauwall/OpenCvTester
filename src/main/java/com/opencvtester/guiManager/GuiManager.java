package com.opencvtester.guiManager;

import java.io.IOException;
import java.util.Stack;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.gui.MainWindow;
import com.opencvtester.historyManager.AddOrDeleteHistoryReader;
import com.opencvtester.historyManager.HistoryManager;
import com.opencvtester.historyManager.SetParameterHistoryReader;
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
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public GuiManager(ChainOfLayers chainOfLayers){
		this.chainOfLayers=chainOfLayers;
		
		history=new HistoryManager();
		frameOutWindow=new FrameWindowManager();
		frameOutWindow.refresh(chainOfLayers.getFrameOut().getBufferedImage());	
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow=mainWindow;		
	}

	public Stack<String> getFiltersName() {
		return chainOfLayers.getFilterDataBase().getFiltersName();	
	}
	
	/*
	 * FEATURES
	 */
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
	
	public void addFilterInDatabase(String name, FilterControlledByFloat filter) {
		chainOfLayers.getFiltersDataBase().addFilter(name, filter);
	}
	
	public Layer createAndAddLayer (int layerIndex, Stack<String> filterNames) {	
		Stack<Id> id= new Stack<Id>();
		id.push(createLayerId(layerIndex));
		
		if (filterNames!=null) {
			for (int i=0; i< filterNames.size(); i++) {
				id.push(createFilterId(layerIndex, i));
			}
		}
		
		Layer newLayer= chainOfLayers.createAndAddLayer(id, filterNames);
		
		LayerManager newLayerManager= new LayerManager(newLayer, this);
		newLayerManager.getLayerWindow().setVisible(false);
		mainWindow.addLayerManager(newLayerManager);
		refreshResult();
		
		AddOrDeleteLayer parameter= new AddOrDeleteLayer (chainOfLayers, mainWindow, newLayerManager, new AddOrDeleteHistoryReader());
		parameter.setAddOrDelete(Functionalities.ADD);
		history.setState(parameter);
		
		return newLayer;
	}
	
	public void deleteLayer(LayerManager layerManager) {		
		if (layerManager!=null) {
			chainOfLayers.delLayer(layerManager.getLayer());
			mainWindow.deleteLayerManager(layerManager);	
			refreshResult();
			
			AddOrDeleteLayer parameter= new AddOrDeleteLayer (chainOfLayers, mainWindow, layerManager, new AddOrDeleteHistoryReader());
			parameter.setAddOrDelete(Functionalities.DELETE);
			history.setState(parameter);
		}
	}
	
	public void createAndAddFilterInLayer(int layerIndex, int filterIndex, String filterName) {	
		Id id= createFilterId(layerIndex, filterIndex);
		FilterControlledByFloat newFilter = chainOfLayers.createAndAddFilterInLayer(id, filterName);
		
		FilterManager newFilterManager = new FilterManager(newFilter, this);
		mainWindow.addFilterWidgetInLayerWidget(newFilterManager);	
		refreshResult();
		
		AddOrDeleteFilter parameter= new AddOrDeleteFilter (chainOfLayers, mainWindow.getChainOfLayerManagers(), newFilterManager, new AddOrDeleteHistoryReader());
		parameter.setAddOrDelete(Functionalities.ADD);
		history.setState(parameter);
	}
	
	public void deleteFilterInLayer(FilterManager filterManagerToDel)  {		
		if (filterManagerToDel!=null) {
			chainOfLayers.delFilterInLayer(filterManagerToDel.getFilter());
			mainWindow.delFilterWidgetInLayerWidget(filterManagerToDel);
			refreshResult();
			
			AddOrDeleteFilter parameter= new AddOrDeleteFilter (chainOfLayers, mainWindow.getChainOfLayerManagers(), filterManagerToDel, new AddOrDeleteHistoryReader());
			parameter.setAddOrDelete(Functionalities.DELETE);
			history.setState(parameter);
		}
	}

	public void setOpacity(Filter opacityFilter, Float opacity) {	
		chainOfLayers.setOpacity(opacityFilter, opacity);
		refreshResult();
	}	

	public void setParameters(FilterControlledByFloat filterToSet, String name, Float value) throws IOException {
		chainOfLayers.setParameters (filterToSet, name, value);	
		refreshResult();
		
		SetParameters parameter= new SetParameters(this, chainOfLayers, mainWindow.getChainOfLayerManagers(), filterToSet, new SetParameterHistoryReader());
		history.setState(parameter);	
	}
	
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass) {
		chainOfLayers.setBypass(createFilterId(layerIndex, filterIndex), bypass);
		refreshResult();
	}
	
	public void refreshResult(){
		frameOutWindow.refresh(chainOfLayers.getFrameOut().getBufferedImage());	
	}
	
	public void undo() {
		history.undo();	
	}
	
	public void redo() {
		history.redo();
	}
	
	public void store() {	
		history.store();
	}
}
