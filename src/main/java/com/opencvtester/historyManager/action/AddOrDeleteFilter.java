package com.opencvtester.historyManager.action;

import com.opencvtester.guiManager.FilterManager;
import com.opencvtester.guiManager.GuiManager;

public class AddOrDeleteFilter implements Action {

	private Functionalities addOrDelete;
	private FilterManager filterManager;
	private GuiManager guiManager;
	private NatureOfAction natureOfAction;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteFilter(GuiManager guiManager, FilterManager filterManager){
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
