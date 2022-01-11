package com.opencvtester.historyManager.action;


import com.opencvtester.gui.MainWindow;
import com.opencvtester.guiManager.LayerManager;

import com.opencvtester.renderingEngine.ChainOfLayers;

public class AddOrDeleteLayer implements Action {

	public Functionalities addOrDelete;
	private LayerManager layerManager;
	private ChainOfLayers chainOfLayers;
	private MainWindow mainWindow;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteLayer(ChainOfLayers chainOfLayers, MainWindow mainWindow, LayerManager layerManager){
		this.layerManager=layerManager;
		this.chainOfLayers=chainOfLayers;
		this.mainWindow=mainWindow;
	}
	
	public boolean addOrDeleteAction() {
		return true;
	}

	/*
	 * FEATURES
	 */
	public void invert() {
		if (addOrDelete== Functionalities.ADD) {
			addOrDelete=Functionalities.DELETE;
		}
		else if (addOrDelete== Functionalities.DELETE) {
			addOrDelete=Functionalities.ADD;
		}
	}

	public void execute() {
		if (addOrDelete== Functionalities.ADD) {
			mainWindow.getGuiManager().addLayer(layerManager);
		}
		else if (addOrDelete== Functionalities.DELETE) {
			mainWindow.getGuiManager().deleteLayer(layerManager);
		}	
	}
	
	public void setAddOrDelete(Functionalities addOrDelete) {
		this.addOrDelete=addOrDelete;
	}
	
	public Action clone() {
		AddOrDeleteLayer newAction = new AddOrDeleteLayer(chainOfLayers, mainWindow, layerManager);
		newAction.setAddOrDelete(addOrDelete);
		return newAction;
	}
}
