package com.opencvtester.history.action;


import com.opencvtester.controller.MainController;
import com.opencvtester.history.Action;
import com.opencvtester.history.Functionalities;
import com.opencvtester.history.NatureOfAction;

public class AddOrDeleteLayer implements Action 
{
	private Functionalities addOrDelete;
	private int layerIndex;
	private MainController guiManager;
	private NatureOfAction natureOfAction;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteLayer(MainController guiManager, int layerIndex){
		this.guiManager = guiManager;
		this.layerIndex=layerIndex;
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
			guiManager.addLayer(layerIndex);
			
		}
		else if (addOrDelete== Functionalities.DELETE) {
			guiManager.deleteLayer(layerIndex);
		}	
	}
	
	public void setAddOrDelete(Functionalities addOrDelete) {
		this.addOrDelete=addOrDelete;
	}
	
	public Action clone() {
		AddOrDeleteLayer newAction = new AddOrDeleteLayer(guiManager, layerIndex);
		newAction.setAddOrDelete(addOrDelete);
		return newAction;
	}
}
