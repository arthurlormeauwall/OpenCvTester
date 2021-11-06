package application;

import renderingEngine.ChainOfLayers;
import renderingEngine.Layer;

import java.util.Stack;

import actions.Action;
import baseClasses.Id;
import baseClasses.filter.Filter;

public abstract class UIInterface 
{
	protected ChainOfLayers chainOfLayers;
	
	public UIInterface(ChainOfLayers chainOfLayers) {
		this.chainOfLayers = chainOfLayers;
	}

	public void dealOrder(Action action) {
		switch (action.whatToDo) {
			case ADD_FILTER_IN_DATABASE:
				chainOfLayers.addFilterInDatabase(action.parameters.stringParameters.get(0), action.parameters.filterParameters);
				
				break;
				
			case ADD_FILTER:
				chainOfLayers.addFilterInLayer(action.id, action.parameters.stringParameters.get(0));	
				addFilterInLayer(action.id, action.parameters.stringParameters.get(0));
				break;
	
			case DELETE_FILTER :
				chainOfLayers.delFilterInLayer(action.id);
				delFilterInLayer(action.id);
				break;
	
			case ADD_LAYER:
				chainOfLayers.addLayer(action.id, action.parameters.stringParameters);
				addLayer(action.id,  action.parameters.stringParameters);
				break;
	
			case DELETE_LAYER:
				chainOfLayers.delLayer(action.id);
				delLayer(action.id);
				break;
	
			case SET_ALPHA_OPACITY:
				chainOfLayers.setOpacity(action.parameters.intParameters.get(0), action.parameters.intParameters.get(1));
				break;
	
			case SET_ALPHA_FRAME:
				chainOfLayers.setAlpha(action.parameters.intParameters.get(0), action.parameters.frameParameters);
				break;
				
			case SET_PARAMETERS : 
				chainOfLayers.setParameters(action.id.get(0), action.parameters.floatParameters);
				break;

			case SET_BYPASS:
				chainOfLayers.setBypass(action.id.get(0), action.parameters.boolParameters);
				break;
				
			case REFRESH:
				refresh();
			}
	}
	
	protected abstract Layer delLayer(Stack<Id> id);

	protected abstract Layer addLayer(Stack<Id> id, Stack<String> stringParameters);

	protected abstract Filter delFilterInLayer(Stack<Id> id);

	protected abstract Filter addFilterInLayer(Stack<Id> id, String string);

	private void refresh() {
		chainOfLayers.play();
	}

}
