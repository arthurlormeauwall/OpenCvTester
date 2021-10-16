package gui;

import java.util.Stack;

import application.UIInterface;
import baseClasses.Id;
import baseClasses.openCvFacade.Frame;
import renderingEngine.Renderer;

public class UIImp extends UIInterface 
{
	public UIImp(Renderer renderer) {
		super(renderer);
	
	}
	
	@Override
	public void addControlInLayer(Stack<Id> id, int controlId) {	
	}

	@Override
	public void delControlInLayer(Stack<Id> id) {
	}

	@Override
	public void addLayer(Stack<Id> id, Stack<Integer> controlIndex) {	
	}

	@Override
	public void delLayer(Stack<Id> id) {	
	}

	@Override
	public void setParameters(Id id, Stack<Float> parameters) {	
	}

	@Override
	public void setAlpha(int layerIndex, int opacity) {
	}

	@Override
	public void setAlpha(int layerIndex, Frame frame) {	
	}

	@Override
	public Boolean undo() {	
		return false;
	}

	@Override
	public Boolean redo() {	
		return false;
	}

	@Override
	public void store() {
	}

	@Override
	public void play() {	
	}

	@Override
	public void setBypass(Id ControlId, Boolean p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dealBackground() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dealFramesInMaskedLayers() {
		// TODO Auto-generated method stub
		
	}

}
