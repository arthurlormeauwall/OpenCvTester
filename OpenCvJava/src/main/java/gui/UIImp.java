package gui;

import java.util.HashMap;
import java.util.Stack;

import application.UIInterface;
import baseClasses.Id;
import baseClasses.filter.Filter;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.openCvFacade.Frame;
import renderingEngine.ChainOfLayers;
import renderingEngine.Layer;
 
public class UIImp extends UIInterface 
{
	public UIImp(ChainOfLayers renderer) {
		super(renderer);	
	}
	
	
	public Filter addFilterInLayer(Stack<Id> id, String filterName) {	
		return null;
	}

	
	public Filter delFilterInLayer(Stack<Id> id) {
		return null;
	}

	
	public Layer addLayer(Stack<Id> id, Stack<String> filtersNames) {	
		return null;
	}

	public Layer delLayer(Stack<Id> id) {	
		return null;
	}

	
	public void setParameters(Id id, HashMap<String,Float> parameters) {	
	}


	public void setOpacity(int layerIndex, int opacity) {
	}

	
	public void setAlpha(int layerIndex, Frame frame) {	
	}

	public void setBypass(Id filterId, Boolean bypass) {

	}

	public void addFilterInDatabase(String name, FilterControlledByFloat filter) {	
	}


}
