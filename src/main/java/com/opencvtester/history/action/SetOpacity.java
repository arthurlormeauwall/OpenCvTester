package com.opencvtester.history.action;

import com.opencvtester.controller.layer.LayersController;
import com.opencvtester.data.interfacesImp.DataCtrlImp;
import com.opencvtester.history.Action;
import com.opencvtester.history.NatureOfAction;
import com.opencvtester.renderer.entity.OpacityFilter;

public class SetOpacity implements Action {

	private OpacityFilter opacityFilter;
	private DataCtrlImp chainOfLayers;
	private LayersController chainOfLayerManager;
	private NatureOfAction natureOfAction;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public SetOpacity(DataCtrlImp chainOfLayers, LayersController chainOfLayerManager, OpacityFilter opacityFilter){
		this.opacityFilter=opacityFilter;
		this.chainOfLayers=chainOfLayers;
		this.chainOfLayerManager=chainOfLayerManager;
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
		chainOfLayers.setOpacity(opacityFilter, opacityFilter.getOpacity());
		chainOfLayerManager.setOpacity(opacityFilter.layerIndex(), opacityFilter.getOpacity());
	}
	
	public Action clone() {
		SetOpacity newAction = new SetOpacity(chainOfLayers, chainOfLayerManager, opacityFilter);
		return newAction;
	}

}
