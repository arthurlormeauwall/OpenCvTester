package com.opencvtester.controller.action;

import com.opencvtester.controller.MainController;
import com.opencvtester.controller.filter.FilterManager;
import com.opencvtester.controller.interfaces.Action;
import com.opencvtester.entity.enums.Functionalities;
import com.opencvtester.entity.enums.NatureOfAction;

public class AddOrDeleteFilter implements Action {

	private Functionalities addOrDelete;
	private FilterManager filterManager;
	private MainController guiManager;
	private NatureOfAction natureOfAction;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteFilter(MainController guiManager, FilterManager filterManager){
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
