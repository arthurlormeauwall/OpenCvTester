package actions;


import guiController.ChainOfLayerControllers;
import guiController.FilterController;
import guiController.GuiManager;
import guiController.LayerController;
import renderingEngine.ChainOfLayers;

public class AddOrDeleteLayer implements Action {

	public Functionalities addOrDelete;
	private LayerController layerController;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerControllers chainOfLayerWidgets;
	
	public AddOrDeleteLayer(ChainOfLayers chainOfLayers, ChainOfLayerControllers chainOfLayerWidgets, LayerController layerController){
		this.layerController=layerController;
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
			chainOfLayers.addLayer(layerController.getLayer());
			chainOfLayerWidgets.addLayerController(layerController);
		}
		else if (addOrDelete== Functionalities.DELETE_FILTER) {
			chainOfLayers.delLayer(layerController.getLayer());
			chainOfLayerWidgets.deleteLayerController(layerController);
		}	
	}

}
