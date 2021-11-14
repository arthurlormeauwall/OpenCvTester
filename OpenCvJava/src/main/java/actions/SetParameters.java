package actions;

import baseClasses.filter.Filter;
import baseClasses.filter.FilterControlledByFloat;
import guiManager.ChainOfLayersManager;
import renderingEngine.ChainOfLayers;

public class SetParameters implements Action {

	public Filter filter;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayersManager chainOfLayerWidgets;
	
	public SetParameters(ChainOfLayers chainOfLayers, ChainOfLayersManager chainOfLayerWidgets, Filter filter){
		this.filter=filter;
		this.chainOfLayers=chainOfLayers;
		this.chainOfLayerWidgets=chainOfLayerWidgets;

	}
	
	public void invert() {	
	}
	
	public void execute() {	
		chainOfLayers.setParameters(filter.getId(), ((FilterControlledByFloat)filter).getParameters());
		chainOfLayerWidgets.setParameters(filter);
	}

}