package actions;

import baseClasses.filter.Filter;
import baseClasses.filter.FilterControlledByFloat;
import guiManager.ChainOfLayerManagers;
import renderingEngine.ChainOfLayers;

public class SetParameters implements Action {

	public Filter filter;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerManagers chainOfLayerManager;
	
	public SetParameters(ChainOfLayers chainOfLayers, ChainOfLayerManagers chainOfLayerManager, Filter filter){
		this.filter=filter;
		this.chainOfLayers=chainOfLayers;
		this.chainOfLayerManager=chainOfLayerManager;

	}
	
	public void invert() {	
	}
	
	public void execute() {	
		chainOfLayers.setParameters(filter.getId(), ((FilterControlledByFloat)filter).getParameters());
		chainOfLayerManager.setParameters(filter);
	}

}
