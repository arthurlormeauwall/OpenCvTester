package com.opencvtester.historyManager.action;


import com.opencvtester.guiManager.ChainOfLayerManagers;
import com.opencvtester.guiManager.FilterManager;
import com.opencvtester.renderingEngine.ChainOfLayers;

public class AddOrDeleteFilter implements Action {

	public Functionalities addOrDelete;
	private FilterManager filterManager;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerManagers chainOfLayerManager;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public AddOrDeleteFilter(ChainOfLayers chainOfLayers, ChainOfLayerManagers chainOfLayerManager, FilterManager filterManager){
		this.filterManager=filterManager;
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
			chainOfLayers.addFilterInLayer(filterManager.getFilter());
			chainOfLayerManager.addFilterWigetInLayerWiget(filterManager);
		}
		else if (addOrDelete== Functionalities.DELETE) {
			chainOfLayers.delFilterInLayer(filterManager.getFilter());
			chainOfLayerManager.deFilterWidgetInLayerWidget(filterManager);
		}	
	}
	
	public void setAddOrDelete(Functionalities addOrDelete) {
		this.addOrDelete=addOrDelete;
	}
	
	public Action clone() {
		AddOrDeleteFilter newAction = new AddOrDeleteFilter(chainOfLayers, chainOfLayerManager, filterManager);
		newAction.setAddOrDelete(addOrDelete);
		return newAction;
	}

}
