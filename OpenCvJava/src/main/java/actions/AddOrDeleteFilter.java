package actions;


import guiManager.ChainOfLayerManagers;
import guiManager.FilterManager;
import renderingEngine.ChainOfLayers;

public class AddOrDeleteFilter implements Action {

	public Functionalities addOrDelete;
	private FilterManager filterManager;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerManagers chainOfLayerManager;
	
	public AddOrDeleteFilter(ChainOfLayers chainOfLayers, ChainOfLayerManagers chainOfLayerManager, FilterManager filterManager){
		this.filterManager=filterManager;
		this.chainOfLayers=chainOfLayers;
		this.chainOfLayerManager=chainOfLayerManager;
	}

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

}
