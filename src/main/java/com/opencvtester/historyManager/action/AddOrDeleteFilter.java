package com.opencvtester.historyManager.action;

import com.opencvtester.gui.MainWindow;
import com.opencvtester.guiManager.FilterManager;
import com.opencvtester.historyManager.HistoryReader;
import com.opencvtester.renderingEngine.ChainOfLayers;

public class AddOrDeleteFilter implements Action {

	public Functionalities addOrDelete;
	private FilterManager filterManager;
	private ChainOfLayers chainOfLayers;
	private MainWindow mainWindow;
	private HistoryReader historyReader;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteFilter(ChainOfLayers chainOfLayers, MainWindow mainWindow, FilterManager filterManager, HistoryReader historyReader){
		this.historyReader=historyReader;
		this.filterManager=filterManager;
		this.chainOfLayers=chainOfLayers;
		this.mainWindow=mainWindow;
	}

	public HistoryReader getHistoryReader() {
		return historyReader;
	}
	
	public boolean lockedSystem() {
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
			mainWindow.getGuiManager().addFilter(filterManager.getFilter());
		}
		else if (addOrDelete== Functionalities.DELETE) {
			mainWindow.getGuiManager().deleteFilter(filterManager);
		}	
	}
	
	public void setAddOrDelete(Functionalities addOrDelete) {
		this.addOrDelete=addOrDelete;
	}
	
	public Action clone() {
		AddOrDeleteFilter newAction = new AddOrDeleteFilter(chainOfLayers, mainWindow, filterManager, historyReader);
		newAction.setAddOrDelete(addOrDelete);
		return newAction;
	}

}
