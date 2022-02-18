package com.opencvtester.history.action;


import com.opencvtester.controller.MainController;
import com.opencvtester.history.Action;
import com.opencvtester.history.Functionalities;
import com.opencvtester.history.NatureOfAction;
import com.opencvtester.renderer.Layer;

public class AddOrDeleteLayer implements Action 
{
	private Functionalities addOrDelete;
	private NatureOfAction natureOfAction;
	
	private Layer layer;
	private MainController mainController;

	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteLayer(MainController guiManager, Layer layer){
		this.mainController = guiManager;
		this.layer=layer;
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
			mainController.addLayer(layer);
			
		}
		else if (addOrDelete== Functionalities.DELETE) {
			mainController.deleteLayer(layer);
		}	
	}
	
	public void setAddOrDelete(Functionalities addOrDelete) {
		this.addOrDelete=addOrDelete;
	}
	
	public Action clone() {
		AddOrDeleteLayer newAction = new AddOrDeleteLayer(mainController, layer);
		newAction.setAddOrDelete(addOrDelete);
		return newAction;
	}
}
