package actions;


import guiManager.ChainOfLayersManager;
import guiManager.LayerManager;
import renderingEngine.ChainOfLayers;

public class AddOrDeleteLayer implements Action {

	public Functionalities addOrDelete;
	private LayerManager layerController;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayersManager chainOfLayerWidgets;
	
	public AddOrDeleteLayer(ChainOfLayers chainOfLayers, ChainOfLayersManager chainOfLayerWidgets, LayerManager layerController){
		this.layerController=layerController;
		this.chainOfLayers=chainOfLayers;
		this.chainOfLayerWidgets=chainOfLayerWidgets;
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
			chainOfLayers.addLayer(layerController.getLayer());
			chainOfLayerWidgets.addLayerController(layerController);
		}
		else if (addOrDelete== Functionalities.DELETE) {
			chainOfLayers.delLayer(layerController.getLayer());
			chainOfLayerWidgets.deleteLayerController(layerController);
		}	
	}

}
