package com.opencvtester.historyManager.action;


import com.opencvtester.gui.MainWindow;
import com.opencvtester.guiManager.LayerManager;
import com.opencvtester.historyManager.HistoryReader;
import com.opencvtester.renderingEngine.ChainOfLayers;

public class AddOrDeleteLayer implements Action {

	public Functionalities addOrDelete;
	private LayerManager layerManager;
	private ChainOfLayers chainOfLayers;
	private MainWindow mainWindow;
	private HistoryReader historyReader;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteLayer(ChainOfLayers chainOfLayers, MainWindow mainWindow, LayerManager layerManager, HistoryReader historyReader){
		this.layerManager=layerManager;
		this.chainOfLayers=chainOfLayers;
		this.mainWindow=mainWindow;
		this.historyReader=historyReader;
	}
	
	public boolean addOrDeleteSystem() {
		return true;
	}
	
	public HistoryReader getHistoryReader() {
		return historyReader;
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
		AddOrDeleteLayer newAction = new AddOrDeleteLayer(chainOfLayers, mainWindow, layerManager, historyReader);
		newAction.setAddOrDelete(addOrDelete);
		return newAction;
	}
}
