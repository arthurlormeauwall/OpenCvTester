package com.opencvtester.guiControllers;

import java.util.Stack;

import com.opencvtester.renderer.ControlledFilter;

public class LayersController {
	
	private Stack<LayerController> layerManagers;
	private MainWindowController mainWindow;

	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayersController (MainWindowController gui){	
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
	public void addFilterManager(FilterController filterManager) {
		((LayerController)layerManagers.get(filterManager.layerIndex())).addFilterController(filterManager);
		mainWindow.updateGui();
	}
	
	public void deleteFilterManager(int layerIndex, int filterIndex) {
		((LayerController)layerManagers.get(layerIndex)).deleteFilterWidget(filterIndex);
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

	public void setParameters(ControlledFilter filter) {
		mainWindow.updateFilter(filter.layerIndex(), filter.filterIndex());
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
