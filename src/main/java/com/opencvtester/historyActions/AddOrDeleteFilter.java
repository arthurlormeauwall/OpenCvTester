package com.opencvtester.historyActions;

import com.opencvtester.app.MainController;
import com.opencvtester.history.Action;
import com.opencvtester.history.Functionalities;
import com.opencvtester.history.NatureOfAction;
import com.opencvtester.renderer.ControlledFilter;

public class AddOrDeleteFilter implements Action {

	private Functionalities addOrDelete;
	private NatureOfAction natureOfAction;
	
	private MainController mainController;
	private ControlledFilter filter;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteFilter(MainController mainController, ControlledFilter filter){
		this.mainController=mainController;
//		this.layerIndex=layerIndex;
//		this.filterIndex=filterIndex;
		this.filter=filter;
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
			mainController.addFilter(filter);
		}
		else if (addOrDelete== Functionalities.DELETE) {
			mainController.deleteFilter(filter.layerIndex(), filter.filterIndex());
		}	
	}
	
	public void setAddOrDelete(Functionalities addOrDelete) {
		this.addOrDelete=addOrDelete;
	}
	
	public Action clone() {
		AddOrDeleteFilter newAction = new AddOrDeleteFilter(mainController, filter);
		newAction.setAddOrDelete(addOrDelete);
		return newAction;
	}

}
