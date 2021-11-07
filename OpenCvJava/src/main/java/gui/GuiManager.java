package gui;

import java.util.HashMap;
import java.util.Stack;
import actionsHistory.ActionsHistory;
import baseClasses.Id;
import renderingEngine.ChainOfLayers;
import renderingEngine.GroupsId;



public class GuiManager 
{
	
	private ActionsHistory history;
	private ChainOfLayers chainOfLayers;
	private App gui;
	
	public GuiManager(ChainOfLayers chainOfLayers, App gui){
		this.chainOfLayers=chainOfLayers;
		this.gui=gui;
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
	
	
	public void createAndAddLayer (int layerIndex, int filterIndex, Stack<String> filterNames) {	
	}
	
	public void createAndAddFilterInLayer(int layerIndex, int filterIndex, String filterName) {	
	}
	
	public void delFilterInLayer(FilterWidget filterWidgetToDel)  {		
	}
	
	public void delLayer(LayerWidget layerWidget) {
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
