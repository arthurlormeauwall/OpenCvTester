package com.opencvtester.historyManager.action;

import com.opencvtester.filtersDataBase.OpacityFilter;
import com.opencvtester.guiManager.ChainOfLayerManagers;
import com.opencvtester.renderingEngine.ChainOfLayers;

public class SetOpacity implements Action {

	private OpacityFilter opacityFilter;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerManagers chainOfLayerManager;
	private NatureOfAction natureOfAction;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public SetOpacity(ChainOfLayers chainOfLayers, ChainOfLayerManagers chainOfLayerManager, OpacityFilter opacityFilter){
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
