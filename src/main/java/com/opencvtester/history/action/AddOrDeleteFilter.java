package com.opencvtester.history.action;

import com.opencvtester.controller.MainController;
import com.opencvtester.filterController.FilterController;
import com.opencvtester.history.Action;
import com.opencvtester.history.Functionalities;
import com.opencvtester.history.NatureOfAction;

public class AddOrDeleteFilter implements Action {

	private Functionalities addOrDelete;
	private FilterController filterManager;
	private MainController guiManager;
	private NatureOfAction natureOfAction;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteFilter(MainController guiManager, FilterController filterManager){
		this.filterManager=filterManager;
		this.guiManager=guiManager;
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
			guiManager.addFilter(filterManager);
		}
		else if (addOrDelete== Functionalities.DELETE) {
			if (filterManager!=null) {
				guiManager.deleteFilter(filterManager);
			}
		}	
	}
	
	public void setAddOrDelete(Functionalities addOrDelete) {
		this.addOrDelete=addOrDelete;
	}
	
	public Action clone() {
		AddOrDeleteFilter newAction = new AddOrDeleteFilter(guiManager, filterManager);
		newAction.setAddOrDelete(addOrDelete);
		return newAction;
	}

}
