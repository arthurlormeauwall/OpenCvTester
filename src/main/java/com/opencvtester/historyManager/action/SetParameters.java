package com.opencvtester.historyManager.action;

import java.util.LinkedHashMap;

import com.opencvtester.baseClasses.filter.ControlledFilter;
import com.opencvtester.guiManager.ChainOfLayerManagers;
import com.opencvtester.guiManager.GuiManager;
import com.opencvtester.renderingEngine.ChainOfLayers;

public class SetParameters implements Action {

	
	private ControlledFilter filter;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerManagers chainOfLayerManager;
	private LinkedHashMap<String, Float> parameters;
	private GuiManager guiManager;
	private NatureOfAction natureOfAction;
	
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	@SuppressWarnings("unchecked")
	public SetParameters(GuiManager guiManager, ChainOfLayers chainOfLayers, ChainOfLayerManagers chainOfLayerManager,ControlledFilter filter){
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
