package com.opencvtester.historyActions;

import com.opencvtester.app.MainController;
import com.opencvtester.history.Action;
import com.opencvtester.history.NatureOfAction;

public class SetOpacity implements Action {

	private NatureOfAction natureOfAction;
	private Float opacity;
	private MainController mainController;
	private int layerIndex;
	
	
	public SetOpacity(MainController mainController, int layerIndex, Float opacity){
		this.opacity=opacity;
		this.layerIndex=layerIndex;
		this.mainController=mainController;
		this.natureOfAction=NatureOfAction.PARAMETER_SETTING;
	}
	
	public NatureOfAction natureOfAction() {
		return natureOfAction;
	}
	
	public void invert() {	
	}
	
	public void execute() {	
		mainController.setOpacity(layerIndex, opacity);
	}
	
	public Action clone() {
		SetOpacity newAction = new SetOpacity(mainController, layerIndex, opacity);
		return newAction;
	}

}
