package actions;


import guiManager.ChainOfLayerManagers;
import guiManager.LayerManager;
import renderingEngine.ChainOfLayers;

public class AddOrDeleteLayer implements Action {

	public Functionalities addOrDelete;
	private LayerManager layerController;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerManagers chainOfLayerWidgets;
	
	public AddOrDeleteLayer(ChainOfLayers chainOfLayers, ChainOfLayerManagers chainOfLayerWidgets, LayerManager layerController){
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
			chainOfLayerWidgets.addLayerManager(layerController);
		}
		else if (addOrDelete== Functionalities.DELETE) {
			chainOfLayers.delLayer(layerController.getLayer());
			chainOfLayerWidgets.deleteLayerManager(layerController);
		}	
	}

}
