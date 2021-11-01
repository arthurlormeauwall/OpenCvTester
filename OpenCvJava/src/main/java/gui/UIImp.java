package gui;

import java.util.HashMap;
import java.util.Stack;

import application.UIInterface;
import baseClasses.Id;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.openCvFacade.Frame;
import renderingEngine.ChainOfLayers;
 
public class UIImp extends UIInterface 
{
	public UIImp(ChainOfLayers renderer) {
		super(renderer);	
	}
	
	
	public void addFilterInLayer(Stack<Id> id, String filterName) {	
	}

	
	public void delFilterInLayer(Stack<Id> id) {
	}

	
	public void addLayer(Stack<Id> id, Stack<String> filtersNames) {	
	}

	public void delLayer(Stack<Id> id) {	
	}

	
	public void setParameters(Id id, HashMap<String,Float> parameters) {	
	}


	public void setAlpha(int layerIndex, int opacity) {
	}

	
	public void setAlpha(int layerIndex, Frame frame) {	
	}


	public Boolean undo() {	
		return false;
	}


	public Boolean redo() {	
		return false;
	}


	public void store() {
	}

	
	public void play() {	
	}


	public void setBypass(Id filterId, Boolean bypass) {

	}

	public void addFilterInDatabase(String name, FilterControlledByFloat filter) {
		
	}

}
