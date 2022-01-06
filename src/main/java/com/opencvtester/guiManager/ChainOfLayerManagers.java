package com.opencvtester.guiManager;

import java.util.LinkedHashMap;

import com.opencvtester.baseClasses.ChainOfCommands;
import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.gui.MainWindow;

public class ChainOfLayerManagers {
	
	private ChainOfCommands layerManagers;
	private MainWindow gui;
	private String groupID;
	
	public ChainOfLayerManagers (MainWindow gui){	
		Id chainId = new Id();
		chainId.set(0,0);
		layerManagers= new ChainOfCommands();
		this.gui=gui;
		groupID="layer";
	}
	
	public void addFilterWigetInLayerWiget(FilterManager filterController) {
		((LayerManager)layerManagers.getCommand(filterController.getLayerIndex())).addFilterManager(filterController);
		gui.updateGui();
	}
	
	public void deFilterWidgetInLayerWidget(FilterManager filterManager) {
		((LayerManager)layerManagers.getCommand(filterManager.getLayerIndex())).deleteFilterWidget(filterManager);
		gui.updateGui();
	}

	public void addLayerManager(LayerManager layerController) {
		layerManagers.addCommand(layerController, indexType());	
		gui.updateGui();
	}

	public void deleteLayerManager(LayerManager layerManager) {
		layerManagers.delCommand(layerManager, indexType());	
		gui.updateGui();
	}

	public void setOpacity(int layerIndex, Float opacity) {
		gui.updateOpacityValue(layerIndex,opacity);
	}

	public void setParameters(Filter filter, LinkedHashMap<String, Float> parameters) {
		gui.updateParametersValues(filter, parameters);
	}
	
	public int getNumberOfLayer(){
		return layerManagers.getSize();
	}

	public void updateGui() {
		for (int i=0;i<getNumberOfLayer();i++) {
			((LayerManager)layerManagers.getCommand(i)).updateGui();
		}	
	}

	public LayerManager getLayerManager (int i) {
		
		return (LayerManager)layerManagers.getCommand(i);
	}
	
	public String indexType() {
		return groupID;
	}

}
