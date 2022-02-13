package com.opencvtester.guiManager;

import java.util.LinkedHashMap;
import java.util.Stack;

import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.gui.MainWindow;

public class ChainOfLayerManagers {
	
	private Stack<LayerManager> layerManagers;
	private MainWindow mainWindow;
	private String indexType;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public ChainOfLayerManagers (MainWindow gui){	
		this.mainWindow=gui;
		indexType="layer";
		layerManagers= new Stack<LayerManager>();
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public LayerManager getLayerManager (int i) {
		return (LayerManager)layerManagers.get(i);
	}
	
	public Stack<LayerManager> getChainOfCommands () {
		return layerManagers;
	}
	
	/*
	 * FEATURES
	 */
	public void addFilterManager(FilterManager filterManager) {
		((LayerManager)layerManagers.get(filterManager.layerIndex())).addFilterManager(filterManager);
		mainWindow.updateGui();
	}
	
	public void deleteFilterManager(FilterManager filterManager) {
		((LayerManager)layerManagers.get(filterManager.layerIndex())).deleteFilterWidget(filterManager);
		mainWindow.updateGui();
	}

	public void addLayerManager(LayerManager layerController) {
		layerManagers.add(layerController);	
		mainWindow.updateGui();
	}

	public void deleteLayerManager(LayerManager layerManager) {
		layerManagers.remove(layerManager.getIndex(indexType));	
		mainWindow.updateGui();
	}

	public void setOpacity(int layerIndex, Float opacity) {
		mainWindow.updateOpacityValue(layerIndex,opacity);
	}

	public void setParameters(Filter filter, LinkedHashMap<String, Float> parameters) {
		mainWindow.updateParametersValues(filter, parameters);
	}
	
	public int getNumberOfLayer(){
		return layerManagers.size();
	}

	public void updateGui() {
		for (int i=0;i<getNumberOfLayer();i++) {
			((LayerManager)layerManagers.get(i)).updateGui();
		}	
	}
}
