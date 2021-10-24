package application;

import renderingEngine.Renderer;
import baseClasses.Command;

public abstract class UIInterface extends Command implements FunctionalitiesInterface
{
	protected Renderer renderer;
	
	public UIInterface(Renderer renderer) {
		this.renderer = renderer;
	}

	public void dealOrder(Action p) {
		switch (p.whatToDo) {
			case ADD_FILTER_TO_DATABASE:
				renderer.addAlgorithm(p.parameters.algoParameters);
				addAlgorithm(p.parameters.algoParameters);
				break;
				
			case ADD_FILTER:
				renderer.addControlInLayer(p.id,((int)p.parameters.intParameters.get(0)));	
				addControlInLayer (p.id, ((int)p.parameters.intParameters.get(0)));
				break;
	
			case DELETE_FILTER :
				renderer.delControlInLayer(p.id);
				delControlInLayer(p.id);
				break;
	
			case ADD_LAYER:
				renderer.addLayer(p.id, p.parameters.intParameters);
				addLayer(p.id, p.parameters.intParameters);
				break;
	
			case DELETE_LAYER:
				renderer.delLayer(p.id);
				delLayer(p.id);
				break;
	
			case SET_ALPHA_OPACITY:
				renderer.setAlpha(p.parameters.intParameters.get(0), p.parameters.intParameters.get(1));
				setAlpha(p.parameters.intParameters.get(0), p.parameters.intParameters.get(1));
				break;
	
			case SET_ALPHA_FRAME:
				renderer.setAlpha(p.parameters.intParameters.get(0), p.parameters.frameParameters.get(0));
				setAlpha(p.parameters.intParameters.get(0), p.parameters.frameParameters.get(0));
				break;
				
			case SET_PARAMETERS : 
				renderer.setParameters(p.id.get(0), p.parameters.floatParameters);
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
				renderer.setBypass(p.id.get(0), p.parameters.boolParameters);
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
