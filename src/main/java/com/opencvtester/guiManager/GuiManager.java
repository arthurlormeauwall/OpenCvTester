package com.opencvtester.guiManager;

import java.io.IOException;
import java.util.Stack;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.gui.MainWindow;
import com.opencvtester.historyManager.HystoryManager;
import com.opencvtester.historyManager.action.SetParameters;
import com.opencvtester.renderingEngine.ChainOfLayers;
import com.opencvtester.renderingEngine.Layer;


public class GuiManager 
{
	private HystoryManager history;
	private ChainOfLayers chainOfLayers;
	private MainWindow mainWindow;
	private FrameWindowManager frameWindowManager;
	
	public GuiManager(ChainOfLayers chainOfLayers){
		this.chainOfLayers=chainOfLayers;
		
		history=new HystoryManager();
		frameWindowManager=new FrameWindowManager();
		frameWindowManager.refresh(chainOfLayers.getDest().getBufferedImage());	
	}
	
	public void setGui(MainWindow mainWindow) {
		this.mainWindow=mainWindow;		
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
		
		Layer newLayer= chainOfLayers.addLayer(id, filterNames);
		
		LayerManager newLayerManager= new LayerManager(newLayer, this);
		newLayerManager.getLayerWindow().setVisible(false);
		mainWindow.addLayerManager(newLayerManager);
		refreshResult();
		return newLayer;
	}
	
	
	public void createAndAddFilterInLayer(int layerIndex, int filterIndex, String filterName) {	
		Stack<Id> id= new Stack<Id>();
		id.push(createFilterId(layerIndex, filterIndex));
		FilterControlledByFloat newFilter = chainOfLayers.createAndAddFilterInLayer(id, filterName);
		
		FilterManager newLayerController = new FilterManager(newFilter, this);
		mainWindow.addFilterWidgetInLayerWidget(newLayerController);	
		refreshResult();
	}
	
	public void delFilterInLayer(FilterManager filterManagerToDel)  {		
		if (filterManagerToDel!=null) {
			chainOfLayers.delFilterInLayer(filterManagerToDel.getFilter());
			mainWindow.delFilterWidgetInLayerWidget(filterManagerToDel);
			refreshResult();
		}
	}
	
	public void deleteLayerManager(LayerManager layerManager) {		
		if (layerManager!=null) {
			chainOfLayers.delLayer(layerManager.getLayer());
			mainWindow.deleteLayerManager(layerManager);	
			refreshResult();
		}
	}

	public void setOpacity(int layerIndex, Float opacity) {	
		chainOfLayers.setOpacity(layerIndex, opacity);
		refreshResult();
	}	

	public void setParameters(FilterControlledByFloat filterToSet, String name, Float value) throws IOException {
		chainOfLayers.setParameters (filterToSet, name, value);	
		refreshResult();
		
		SetParameters parameter= new SetParameters(this, chainOfLayers, mainWindow.getChainOfLayerManagers(), filterToSet);
		history.setState(parameter);	
	}
	
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass) {
		chainOfLayers.setBypass(createFilterId(layerIndex, filterIndex), bypass);
		refreshResult();
	}
	
	public void refreshResult(){
		frameWindowManager.refresh(chainOfLayers.getDest().getBufferedImage());	
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

	public Stack<String> getFiltersName() {
		return chainOfLayers.getFilterDataBase().getFiltersName();	
	}
}
