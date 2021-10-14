package application;

import java.util.Stack;

import renderingEngine.Renderer;

import baseClasses.Id;
import baseClasses.enums_structs.Action;
import baseClasses.openCvFacade.Frame;

public abstract class UIInterface
{

	public UIInterface(Renderer renderer) {
		m_renderer = renderer;
	}

	public abstract void addControlInLayer(Stack<Id> id, int controlId);
	public abstract void delControlInLayer(Stack<Id> id);
	public abstract void addLayer(Stack<Id> id, Stack<Integer> controlIndex);
	public abstract void delLayer(Stack<Id> id);
	public abstract void setParameters(Id id, Stack<Float> parameters);
	public abstract void setAlpha(int layerIndex, int opacity);
	public abstract void setAlpha(int layerIndex, Frame frame);
	public abstract void undo();
	public abstract void redo();
	public abstract void store();
	public abstract void play();

	public void dealOrder(Action p) {
		switch (p.whatToDo) {
			case ADD_CONTROL:
				m_renderer.addControlInLayer(p.id,((int)p.parameters.int_parameters.get(0)));	
				addControlInLayer (p.id, ((int)p.parameters.int_parameters.get(0)));
				break;
	
			case DELETE_CONTROL :
				m_renderer.delControlInLayer(p.id);
				delControlInLayer(p.id);
				break;
	
			case ADD_LAYER:
				m_renderer.addLayer(p.id, p.parameters.int_parameters);
				addLayer(p.id, p.parameters.int_parameters);
				break;
	
			case DELETE_LAYER:
				m_renderer.delLayer(p.id);
				delLayer(p.id);
				break;
	
			case SET_ALPHA_OPACITY:
				m_renderer.setAlpha(p.parameters.int_parameters.get(0), p.parameters.int_parameters.get(1));
				setAlpha(p.parameters.int_parameters.get(0), p.parameters.int_parameters.get(1));
				break;
	
			case SET_ALPHA_FRAME:
				m_renderer.setAlpha(p.parameters.int_parameters.get(0), p.parameters.frame_parameters.get(0));
				setAlpha(p.parameters.int_parameters.get(0), p.parameters.frame_parameters.get(0));
				break;
				
			case SET_PARAMETERS : 
				m_renderer.setParameters(p.id.get(0), p.parameters.float_parameters);
				break;
	
			case UNDO:
				m_renderer.undo();
				undo();
				break;
	
			case REDO:
				m_renderer.redo();
				redo();
				break;
	
			case STORE:
				m_renderer.store();
				store();
				break;
				
			case SET_BYPASS:
				m_renderer.setBypass(p.id.get(0), p.parameters.bool_parameters);
				break;
				
			case REFRESH:
				refresh();
			}
	}
	
	public void refresh() {
		m_renderer.play();
		play();
	}

	protected Renderer m_renderer;
};
