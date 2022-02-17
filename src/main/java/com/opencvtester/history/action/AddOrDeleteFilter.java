package com.opencvtester.history.action;

import com.opencvtester.controller.MainController;
import com.opencvtester.history.Action;
import com.opencvtester.history.Functionalities;
import com.opencvtester.history.NatureOfAction;

public class AddOrDeleteFilter implements Action {

	private Functionalities addOrDelete;
	private MainController mainController;
	private NatureOfAction natureOfAction;
	private int layerIndex;
	private int filterIndex;
	private String name;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteFilter(MainController mainController, int layerIndex, int  filterIndex , String filterName){
		this.mainController=mainController;
		this.layerIndex=layerIndex;
		this.filterIndex=filterIndex;
		this.name=filterName;
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
			mainController.addFilter(layerIndex, filterIndex, name);
		}
		else if (addOrDelete== Functionalities.DELETE) {
			mainController.deleteFilter(layerIndex, filterIndex);
		}	
	}
	
	public void setAddOrDelete(Functionalities addOrDelete) {
		this.addOrDelete=addOrDelete;
	}
	
	public Action clone() {
		AddOrDeleteFilter newAction = new AddOrDeleteFilter(mainController, layerIndex, filterIndex, name);
		newAction.setAddOrDelete(addOrDelete);
		return newAction;
	}

}
