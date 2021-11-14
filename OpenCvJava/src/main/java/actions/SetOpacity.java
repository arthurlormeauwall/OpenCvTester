package actions;

import filtersDataBase.OpacityFilter;
import guiManager.ChainOfLayerManagers;
import renderingEngine.ChainOfLayers;

public class SetOpacity implements Action {

	public OpacityFilter opacityFilter;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerManagers chainOfLayerWidgets;
	
	public SetOpacity(ChainOfLayers chainOfLayers, ChainOfLayerManagers chainOfLayerWidgets, OpacityFilter opacityFilter){
		this.opacityFilter=opacityFilter;
		this.chainOfLayers=chainOfLayers;
		this.chainOfLayerWidgets=chainOfLayerWidgets;

	}
	
	public void invert() {	
	}
	
	public void execute() {	
		chainOfLayers.setOpacity(opacityFilter.getId().get()[0], opacityFilter.getOpacity());
		chainOfLayerWidgets.setOpacity(opacityFilter.getId().get()[0], opacityFilter.getOpacity());
	}

}
