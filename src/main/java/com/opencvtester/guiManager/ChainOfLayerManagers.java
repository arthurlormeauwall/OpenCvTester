package com.opencvtester.guiManager;

import java.util.LinkedHashMap;

import com.opencvtester.baseClasses.ChainOfCommands;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.gui.MainWindow;

public class ChainOfLayerManagers {
	
	private ChainOfCommands layerManagers;
	private MainWindow mainWindow;
	private String indexType;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public ChainOfLayerManagers (MainWindow gui){	
		this.mainWindow=gui;
		indexType="layer";
		layerManagers= new ChainOfCommands(indexType);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public LayerManager getLayerManager (int i) {
		return (LayerManager)layerManagers.getCommand(i);
	}
	
	/*
	 * FEATURES
	 */
	public void addFilterManager(FilterManager filterController) {
		((LayerManager)layerManagers.getCommand(filterController.getLayerIndex())).addFilterManager(filterController);
		mainWindow.updateGui();
	}
	
	public void deleteFilterManager(FilterManager filterManager) {
		((LayerManager)layerManagers.getCommand(filterManager.getLayerIndex())).deleteFilterWidget(filterManager);
		mainWindow.updateGui();
	}

	public void addLayerManager(LayerManager layerController) {
		layerManagers.addCommand(layerController);	
		mainWindow.updateGui();
	}

	public void deleteLayerManager(LayerManager layerManager) {
		layerManagers.delCommand(layerManager.getIndex(indexType));	
		mainWindow.updateGui();
	}

	public void setOpacity(int layerIndex, Float opacity) {
		mainWindow.updateOpacityValue(layerIndex,opacity);
	}

	public void setParameters(Filter filter, LinkedHashMap<String, Float> parameters) {
		mainWindow.updateParametersValues(filter, parameters);
	}
	
	public int getNumberOfLayer(){
		return layerManagers.getSize();
	}

	public void updateGui() {
		for (int i=0;i<getNumberOfLayer();i++) {
			((LayerManager)layerManagers.getCommand(i)).updateGui();
		}	
	}
}
