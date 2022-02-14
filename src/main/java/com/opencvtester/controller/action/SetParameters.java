package com.opencvtester.controller.action;

import java.util.LinkedHashMap;

import com.opencvtester.controller.MainController;
import com.opencvtester.controller.filter.ControlledFilter;
import com.opencvtester.controller.interfaces.Action;
import com.opencvtester.controller.layer.LayersController;
import com.opencvtester.entity.enums.NatureOfAction;
import com.opencvtester.renderer.ChainOfLayers;

public class SetParameters implements Action {

	
	private ControlledFilter filter;
	private ChainOfLayers chainOfLayers;
	private LayersController chainOfLayerManager;
	private LinkedHashMap<String, Float> parameters;
	private MainController guiManager;
	private NatureOfAction natureOfAction;
	
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	@SuppressWarnings("unchecked")
	public SetParameters(MainController guiManager, ChainOfLayers chainOfLayers, LayersController chainOfLayerManager,ControlledFilter filter){
		this.guiManager=guiManager;
		this.filter=filter;
		parameters= (LinkedHashMap<String, Float>)((ControlledFilter)filter).getParameters().clone();
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
		chainOfLayerManager.getLayerManager(filter.layerIndex()).getFilterManager(filter.filterIndex()).setEmitSignal(false);
		
		chainOfLayerManager.setParameters(filter, parameters);
		chainOfLayers.setAllFilterParameters(filter, parameters);
		guiManager.refreshFrameOut();
		
		chainOfLayerManager.getLayerManager(filter.layerIndex()).getFilterManager(filter.filterIndex()).setEmitSignal(true);
	}
	
	public Action clone() {
		SetParameters newAction = new SetParameters(guiManager, chainOfLayers, chainOfLayerManager, filter);
		return newAction;
	}

}
