package com.opencvtester.controller.layer;

import java.util.LinkedHashMap;
import java.util.Stack;

import com.opencvtester.controller.filter.FilterManager;
import com.opencvtester.entity.Filter;
import com.opencvtester.gui.MainWindow;

public class LayersController {
	
	private Stack<LayerController> layerManagers;
	private MainWindow mainWindow;

	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayersController (MainWindow gui){	
		this.mainWindow=gui;
		layerManagers= new Stack<LayerController>();
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public LayerController getLayerManager (int i) {
		return (LayerController)layerManagers.get(i);
	}
	
	public Stack<LayerController> getChainOfCommands () {
		return layerManagers;
	}
	
	/*
	 * FEATURES
	 */
	public void addFilterManager(FilterManager filterManager) {
		((LayerController)layerManagers.get(filterManager.layerIndex())).addFilterManager(filterManager);
		mainWindow.updateGui();
	}
	
	public void deleteFilterManager(FilterManager filterManager) {
		((LayerController)layerManagers.get(filterManager.layerIndex())).deleteFilterWidget(filterManager);
		mainWindow.updateGui();
	}

	public void addLayerManager(LayerController layerController) {
		layerManagers.add(layerController);	
		mainWindow.updateGui();
	}

	public void deleteLayerManager(LayerController layerManager) {
		layerManagers.remove(layerManager.layerIndex());	
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
			((LayerController)layerManagers.get(i)).updateGui();
		}	
	}
}
