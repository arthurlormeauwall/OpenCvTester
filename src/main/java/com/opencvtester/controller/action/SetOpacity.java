package com.opencvtester.controller.action;

import com.opencvtester.controller.interfaces.Action;
import com.opencvtester.controller.layer.LayersController;
import com.opencvtester.entity.enums.NatureOfAction;
import com.opencvtester.renderer.ChainOfLayers;
import com.opencvtester.renderer.OpacityFilter;

public class SetOpacity implements Action {

	private OpacityFilter opacityFilter;
	private ChainOfLayers chainOfLayers;
	private LayersController chainOfLayerManager;
	private NatureOfAction natureOfAction;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public SetOpacity(ChainOfLayers chainOfLayers, LayersController chainOfLayerManager, OpacityFilter opacityFilter){
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
