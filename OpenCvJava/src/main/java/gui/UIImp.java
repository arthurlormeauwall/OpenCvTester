package gui;

import java.util.Stack;

import application.UIInterface;
import baseClasses.Id;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.openCvFacade.Frame;
import renderingEngine.Renderer;
 
public class UIImp extends UIInterface 
{
	public UIImp(Renderer renderer) {
		super(renderer);	
	}
	
	
	public void addFilterInLayer(Stack<Id> id, String commandName) {	
	}

	
	public void delFilterInLayer(Stack<Id> id) {
	}

	
	public void addLayer(Stack<Id> id, Stack<String> commandNames) {	
	}

	public void delLayer(Stack<Id> id) {	
	}

	
	public void setParameters(Id id, Stack<Float> parameters) {	
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


	public void setBypass(Id ControlId, Boolean p) {
		// TODO Auto-generated method stub
		
	}

	
	public void addAlgorithm(FilterControlledByFloat algorithm) {
		// TODO Auto-generated method stub
		
	}


	public void addFilterInDatabase(String name, FilterControlledByFloat algorithm) {
		// TODO Auto-generated method stub
		
	}



}
