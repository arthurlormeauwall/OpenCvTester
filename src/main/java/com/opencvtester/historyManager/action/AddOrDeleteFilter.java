package com.opencvtester.historyManager.action;

import com.opencvtester.gui.MainWindow;
import com.opencvtester.guiManager.FilterManager;
import com.opencvtester.renderingEngine.ChainOfLayers;

public class AddOrDeleteFilter implements Action {

	private Functionalities addOrDelete;
	private FilterManager filterManager;
	private ChainOfLayers chainOfLayers;
	private MainWindow mainWindow;
	private NatureOfAction natureOfAction;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteFilter(ChainOfLayers chainOfLayers, MainWindow mainWindow, FilterManager filterManager){
		this.filterManager=filterManager;
		this.chainOfLayers=chainOfLayers;
		this.mainWindow=mainWindow;
		this.natureOfAction=NatureOfAction.ADD_OR_DELETE;
	}
	
	public NatureOfAction natureOfAction() {
		return natureOfAction;
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
		AddOrDeleteFilter newAction = new AddOrDeleteFilter(chainOfLayers, mainWindow, filterManager);
		newAction.setAddOrDelete(addOrDelete);
		return newAction;
	}

}
