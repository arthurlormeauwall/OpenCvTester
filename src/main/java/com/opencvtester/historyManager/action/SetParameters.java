package com.opencvtester.historyManager.action;

import java.util.LinkedHashMap;

import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.guiManager.ChainOfLayerManagers;
import com.opencvtester.guiManager.GuiManager;
import com.opencvtester.renderingEngine.ChainOfLayers;

public class SetParameters implements Action {

	
	private FilterControlledByFloat filter;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerManagers chainOfLayerManager;
	private LinkedHashMap<String, Float> parameters;
	private GuiManager guiManager;
	private NatureOfAction natureOfAction;
	
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	@SuppressWarnings("unchecked")
	public SetParameters(GuiManager guiManager, ChainOfLayers chainOfLayers, ChainOfLayerManagers chainOfLayerManager,FilterControlledByFloat filter){
		this.guiManager=guiManager;
		this.filter=filter;
		parameters= (LinkedHashMap<String, Float>)((FilterControlledByFloat)filter).getParameters().clone();
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
		chainOfLayerManager.getLayerManager(filter.getLayerIndex()).getFilterManager(filter.getFilterIndex()).setEmitSignal(false);
		
		chainOfLayerManager.setParameters(filter, parameters);
		chainOfLayers.setAllFilterParameters(filter, parameters);
		guiManager.refreshResult();
		
		chainOfLayerManager.getLayerManager(filter.getLayerIndex()).getFilterManager(filter.getFilterIndex()).setEmitSignal(true);
	}
	
	public Action clone() {
		SetParameters newAction = new SetParameters(guiManager, chainOfLayers, chainOfLayerManager, filter);
		return newAction;
	}

}
