package gui;

import java.util.HashMap;
import java.util.Stack;
import actionsHistory.ActionsHistory;
import baseClasses.Id;
import baseClasses.filter.FilterControlledByFloat;
import renderingEngine.ChainOfLayers;
import renderingEngine.GroupsId;



public class GuiManager 
{
	
	private ActionsHistory history;
	private ChainOfLayers chainOfLayers;
	private App app;
	
	public GuiManager(ChainOfLayers chainOfLayers, App app){
		this.chainOfLayers=chainOfLayers;
		this.app=app;
		history=new ActionsHistory();
	}
	private Id createLayerId(int layerIndex) {	
		Id id = new Id();
		id.set(layerIndex, 0, GroupsId.LAYER.ordinal());
		return id;
	}	
	
	private Id createFilterId(int layerIndex, int filterIndex) {
		Id id = new Id();
		id.set(layerIndex, filterIndex, GroupsId.CONTROL.ordinal());
		return id;
	}
	
	public void addFilterInDatabase(String name, FilterControlledByFloat filter) {
		chainOfLayers.getFiltersDataBase().addFilter(name, filter);
	}
	
	
	
	public void createAndAddLayer (int layerIndex, int filterIndex, Stack<String> filterNames) {	
	}
	
	public void createAndAddFilterInLayer(int layerIndex, int filterIndex, String filterName) {	
	}
	
	public void delFilterInLayer(FilterController filterWidgetToDel)  {		
	}
	
	public void delLayer(LayerController layerWidget) {
	}

	public void setOpacity(int layerIndex, int opacity) {	
	}	
	
	public void setParameters(int layerIndex, int filterIndex, HashMap<String,Float> parametersValues){
	}	
	
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass) {
	}
	
	public void undo() {
		history.undo();	
	}
	
	public void redo() {
		history.redo();
	}
	
	public void store() {	
	}
}
