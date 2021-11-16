package historyManager.action;

import java.util.LinkedHashMap;

import baseClasses.Id;
import baseClasses.filter.Filter;
import baseClasses.filter.FilterControlledByFloat;
import guiManager.ChainOfLayerManagers;
import guiManager.GuiManager;
import renderingEngine.ChainOfLayers;

public class SetParameters implements Action {

	
	private Id id;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerManagers chainOfLayerManager;
	private LinkedHashMap<String, Float> parameters;
	private GuiManager guiManager;
	
	@SuppressWarnings("unchecked")
	public SetParameters(GuiManager guiManager, ChainOfLayers chainOfLayers, ChainOfLayerManagers chainOfLayerManager, Id id){
		this.guiManager=guiManager;
		this.id=id;
		Filter filter= chainOfLayers.getLayer(id.layerIndex()).getFilter(id.filterIndex());
		parameters= (LinkedHashMap<String, Float>)((FilterControlledByFloat)filter).getParameters().clone();
		this.chainOfLayers=chainOfLayers;
		this.chainOfLayerManager=chainOfLayerManager;

	}
	
	public void invert() {	
	}
	
	public void execute() {	
		chainOfLayerManager.getLayerManager(id.layerIndex()).getFilterManager(id.filterIndex()).setEmitSignal(false);
		
		
		chainOfLayerManager.setParameters(id, parameters);
		chainOfLayers.setParameters(id, parameters);
		
		guiManager.refreshResult();
		
		chainOfLayerManager.getLayerManager(id.layerIndex()).getFilterManager(id.filterIndex()).setEmitSignal(true);
	}
	
	public Action clone() {
		SetParameters newAction = new SetParameters(guiManager, chainOfLayers, chainOfLayerManager, id.clone());
		return newAction;
	}

}
