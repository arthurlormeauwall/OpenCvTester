package actions;


import gui.ChainOfLayerControllers;
import gui.FilterController;
import gui.GuiManager;
import gui.LayerController;
import renderingEngine.ChainOfLayers;

public class AddOrDeleteLayer implements Action {

	public Functionalities addOrDelete;
	private LayerController layerWidget;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerControllers chainOfLayerWidgets;
	
	public AddOrDeleteLayer(ChainOfLayers chainOfLayers, ChainOfLayerControllers chainOfLayerWidgets, LayerController filterWidget){
		this.layerWidget=filterWidget;
		this.chainOfLayers=chainOfLayers;
		this.chainOfLayerWidgets=chainOfLayerWidgets;
	}

	public void invert() {
		if (addOrDelete== Functionalities.ADD_FILTER) {
			addOrDelete=Functionalities.DELETE_FILTER;
		}
		else if (addOrDelete== Functionalities.DELETE_FILTER) {
			addOrDelete=Functionalities.ADD_FILTER;
		}
	}

	public void execute() {
		if (addOrDelete== Functionalities.ADD_FILTER) {
			chainOfLayers.addLayer(layerWidget.getLayer());
			chainOfLayerWidgets.addLayerWidget(layerWidget);
		}
		else if (addOrDelete== Functionalities.DELETE_FILTER) {
			chainOfLayers.delLayer(layerWidget.getLayer());
			chainOfLayerWidgets.delLayerWidget(layerWidget);
		}	
	}

}
