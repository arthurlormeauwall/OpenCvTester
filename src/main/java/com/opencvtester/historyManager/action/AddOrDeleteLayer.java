package com.opencvtester.historyManager.action;


import com.opencvtester.guiManager.ChainOfLayerManagers;
import com.opencvtester.guiManager.LayerManager;
import com.opencvtester.renderingEngine.ChainOfLayers;

public class AddOrDeleteLayer implements Action {

	public Functionalities addOrDelete;
	private LayerManager layerManager;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerManagers chainOfLayerManager;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteLayer(ChainOfLayers chainOfLayers, ChainOfLayerManagers chainOfLayerManager, LayerManager layerManager){
		this.layerManager=layerManager;
		this.chainOfLayers=chainOfLayers;
		this.chainOfLayerManager=chainOfLayerManager;
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
			chainOfLayers.addLayer(layerManager.getLayer());
			chainOfLayerManager.addLayerManager(layerManager);
		}
		else if (addOrDelete== Functionalities.DELETE) {
			chainOfLayers.delLayer(layerManager.getLayer());
			chainOfLayerManager.deleteLayerManager(layerManager);
		}	
	}
	
	public void setAddOrDelete(Functionalities addOrDelete) {
		this.addOrDelete=addOrDelete;
	}
	
	public Action clone() {
		AddOrDeleteLayer newAction = new AddOrDeleteLayer(chainOfLayers, chainOfLayerManager, layerManager);
		newAction.setAddOrDelete(addOrDelete);
		return newAction;
	}
}
