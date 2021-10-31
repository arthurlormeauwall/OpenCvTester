package application;

import renderingEngine.ChainOfLayers;
import baseClasses.Undoable;

public abstract class UIInterface extends Undoable implements FunctionalitiesInterface
{
	protected ChainOfLayers renderer;
	
	public UIInterface(ChainOfLayers renderer) {
		this.renderer = renderer;
	}

	public void dealOrder(Action action) {
		switch (action.whatToDo) {
			case ADD_FILTER_IN_DATABASE:
				renderer.addFilterInDatabase(action.parameters.stringParameters.get(0), action.parameters.filterParameters);
				addFilterInDatabase(action.parameters.stringParameters.get(0), action.parameters.filterParameters);
				break;
				
			case ADD_FILTER:
				renderer.addFilterInLayer(action.id, action.parameters.stringParameters.get(0));	
				addFilterInLayer (action.id, action.parameters.stringParameters.get(0));
				break;
	
			case DELETE_FILTER :
				renderer.delFilterInLayer(action.id);
				delFilterInLayer(action.id);
				break;
	
			case ADD_LAYER:
				renderer.addLayer(action.id, action.parameters.stringParameters);
				addLayer(action.id,  action.parameters.stringParameters);
				break;
	
			case DELETE_LAYER:
				renderer.delLayer(action.id);
				delLayer(action.id);
				break;
	
			case SET_ALPHA_OPACITY:
				renderer.setAlpha(action.parameters.intParameters.get(0), action.parameters.intParameters.get(1));
				setAlpha(action.parameters.intParameters.get(0), action.parameters.intParameters.get(1));
				break;
	
			case SET_ALPHA_FRAME:
				renderer.setAlpha(action.parameters.intParameters.get(0), action.parameters.frameParameters);
				setAlpha(action.parameters.intParameters.get(0), action.parameters.frameParameters);
				break;
				
			case SET_PARAMETERS : 
				renderer.setParameters(action.id.get(0), action.parameters.floatParameters);
				break;
	
			case UNDO:
				renderer.undo();
				undo();
				break;
	
			case REDO:
				renderer.redo();
				redo();
				break;
	
			case STORE:
				renderer.store();
				store();
				break;
				
			case SET_BYPASS:
				renderer.setBypass(action.id.get(0), action.parameters.boolParameters);
				break;
				
			case REFRESH:
				refresh();
			}
	}
	
	private void refresh() {
		renderer.play();
		play();
	}
}
