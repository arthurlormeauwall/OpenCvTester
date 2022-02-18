package com.opencvtester.history.action;

import java.util.LinkedHashMap;

import com.opencvtester.controller.MainController;
import com.opencvtester.history.Action;
import com.opencvtester.history.NatureOfAction;
import com.opencvtester.renderer.ControlledFilter;

public class SetParameters implements Action {

	private NatureOfAction natureOfAction;
	
	private ControlledFilter filter;
	private LinkedHashMap<String, Float> parameters;
	private MainController mainController;

	
	/*
	 * CONSTRUCTOR & INITS
	 */
	@SuppressWarnings("unchecked")
	public SetParameters(MainController mainController,ControlledFilter filter){
		this.mainController=mainController;
		this.filter=filter;
		parameters= (LinkedHashMap<String, Float>)((ControlledFilter)filter).getParameters().clone();
		this.natureOfAction=NatureOfAction.PARAMETER_SETTING;
	}
	
	public NatureOfAction natureOfAction() {
		return natureOfAction;
	}
	/*
	 * FEATURES
	 */
	public void invert() {	
	}
	
	public void execute() {	
		mainController.setParameters(filter, parameters);
	}
	
	public Action clone() {
		return new SetParameters(mainController, filter);
	}

}
