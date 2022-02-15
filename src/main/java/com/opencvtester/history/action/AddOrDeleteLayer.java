package com.opencvtester.history.action;


import com.opencvtester.controller.MainController;
import com.opencvtester.controller.layer.LayerController;
import com.opencvtester.history.Action;
import com.opencvtester.history.Functionalities;
import com.opencvtester.history.NatureOfAction;

public class AddOrDeleteLayer implements Action 
{
	private Functionalities addOrDelete;
	private LayerController layerManager;
	private MainController guiManager;
	private NatureOfAction natureOfAction;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteLayer(MainController guiManager, LayerController layerManager){
		this.guiManager = guiManager;
		this.layerManager=layerManager;
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
			guiManager.addLayer(layerManager);
			
		}
		else if (addOrDelete== Functionalities.DELETE) {
			if (layerManager!=null) {
				guiManager.deleteLayer(layerManager);
			}
		}	
	}
	
	public void setAddOrDelete(Functionalities addOrDelete) {
		this.addOrDelete=addOrDelete;
	}
	
	public Action clone() {
		AddOrDeleteLayer newAction = new AddOrDeleteLayer(guiManager, layerManager);
		newAction.setAddOrDelete(addOrDelete);
		return newAction;
	}
}
