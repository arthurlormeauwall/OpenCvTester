package actions;

import filtersDataBase.OpacityFilter;
import guiManager.ChainOfLayerManagers;
import renderingEngine.ChainOfLayers;

public class SetOpacity implements Action {

	public OpacityFilter opacityFilter;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerManagers chainOfLayerManager;
	
	public SetOpacity(ChainOfLayers chainOfLayers, ChainOfLayerManagers chainOfLayerManager, OpacityFilter opacityFilter){
		this.opacityFilter=opacityFilter;
		this.chainOfLayers=chainOfLayers;
		this.chainOfLayerManager=chainOfLayerManager;

	}
	
	public void invert() {	
	}
	
	public void execute() {	
		chainOfLayers.setOpacity(opacityFilter.getId().get()[0], opacityFilter.getOpacity());
		chainOfLayerManager.setOpacity(opacityFilter.getId().get()[0], opacityFilter.getOpacity());
	}

}
