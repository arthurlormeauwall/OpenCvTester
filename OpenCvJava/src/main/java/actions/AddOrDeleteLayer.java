package actions;


import guiManager.ChainOfLayerManagers;
import guiManager.LayerManager;
import renderingEngine.ChainOfLayers;

public class AddOrDeleteLayer implements Action {

	public Functionalities addOrDelete;
	private LayerManager layerManager;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerManagers chainOfLayerManager;
	
	public AddOrDeleteLayer(ChainOfLayers chainOfLayers, ChainOfLayerManagers chainOfLayerManager, LayerManager layerManager){
		this.layerManager=layerManager;
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
			chainOfLayers.addLayer(layerManager.getLayer());
			chainOfLayerManager.addLayerManager(layerManager);
		}
		else if (addOrDelete== Functionalities.DELETE) {
			chainOfLayers.delLayer(layerManager.getLayer());
			chainOfLayerManager.deleteLayerManager(layerManager);
		}	
	}

}
