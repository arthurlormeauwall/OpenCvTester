package renderingEngine;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.filter.Filter;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.history.imp.UndoIdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;

public class ChainOfLayers extends ChainOfLayersInterface
{
	public ChainOfLayers (FiltersDataBase dbControls, Frame background, Id id, UndoIdHistory<Id> undoIdHistory, UndoIdHistory<Id>  renderAtIdHistory) {
		super(dbControls, background, id, undoIdHistory, renderAtIdHistory);	
	}

	public void addFilterInLayer(Stack<Id> controlId, String controlIndexInDataBase) {
		Stack<String> stackOfControlIndexInDataBase = new Stack<String>();
		stackOfControlIndexInDataBase.push(controlIndexInDataBase);
		
		if ( getNumberOfControl()> controlId.get(0).get()[0]) {
			if (((Layer)chainOfCommands.getCommand(controlId.get(0).get()[0])).addFilter(controlId, stackOfControlIndexInDataBase)) {
				execute();
			}
		}	
	}
	
	public void delFilterInLayer(Stack<Id> controlId){
		if (getNumberOfControl()> controlId.get(0).get()[0]) {
			if(((Layer)chainOfCommands.getCommand(controlId.get(0).get()[0])).delFilter(controlId)) {
				execute();
			}
		}
	}   
	
	public void addLayer(Stack<Id> controlId, Stack<String> stackOfCommandIndexInDataBase){
		if (addFilter(controlId, stackOfCommandIndexInDataBase)) {
			execute();
		}	
	}  

	public void delLayer(Stack<Id> controlId){
		if (delFilter(controlId))
		{
			execute();
		}
	}   
	
	public void setAlpha(int layerIndex, Frame alpha){
		if (getNumberOfControl() > layerIndex) {
			((Layer)chainOfCommands.getCommand(layerIndex)).setAlpha(alpha);
			execute();
		}	
	}   
	
	public void setAlpha(int layerIndex, int opacity){
		if (getNumberOfControl() >layerIndex) {
			((Layer)chainOfCommands.getCommand(layerIndex)).setAlpha(opacity);
			execute();
		}
	}  
	
	public void setParameters(Id ControlId, Stack<Float> parameters){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
		if (getNumberOfControl() > layerIndex && ((Layer)chainOfCommands.getCommand(layerIndex)).getNumberOfControl()  > controlIndex) {
			FilterControlledByFloat adjustControlToSet = (FilterControlledByFloat)((Layer)chainOfCommands.getCommand(layerIndex)).getFilter(controlIndex);
			adjustControlToSet.setParameter(parameters);
			execute();
		}
	} 
	
	public void setBypass(Id ControlId, Boolean p){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
	
		if ( getNumberOfControl()>layerIndex && ((Layer)chainOfCommands.getCommand(layerIndex)).getNumberOfControl() > controlIndex) {
			FilterControlledByFloat temp = ((FilterControlledByFloat)((Layer)chainOfCommands.getCommand(layerIndex)).getFilter(controlIndex));
			temp.setBypass(p);
			execute();
		}
	}  

	public void play(){
		dest.play();
	}   
	
	protected Command createFilter(Stack<Id> controlId, Stack<String> controlName){
		Layer maskedLayer = new Layer(dbControls, controlId.get(0), undoIdHistory, renderAtIdHistory);
		maskedLayer.init(m_background, source, dest);
		
		int numberOfControlToAdd = controlName.size();

		for (int i = 0; i < numberOfControlToAdd; i++) {
			Stack<Id> temp=new Stack<Id>();
			Stack<String> temp2=new Stack<String>();
			
			temp.push(controlId.get(i + 1));
			temp2.push(controlName.get(i));
			
			maskedLayer.addFilter(temp, temp2);
		}
		return maskedLayer;
	} 
	
	public void execute(){
		dealFrames();
		dealBackground();
		dealFramesInMaskedLayers();
		render();	
	}

	public void dealBackground(){
		int numberOfMaskedLayers = chainOfCommands.getSize();
		
		if (numberOfMaskedLayers>0) {
			((Layer)chainOfCommands.getCommand(0)).setBackGround(m_background);
			for (int i = 1; i < numberOfMaskedLayers; i++) {
				((Layer)chainOfCommands.getCommand(i)).setBackGround(((Layer)chainOfCommands.getCommand(i - 1)).getDest());
			}
		}	
	}   
	
	public void dealFramesInMaskedLayers(){
		for (int i = 0; i < chainOfCommands.getSize(); i++) {

			Layer test = (Layer)getFilter(i);
			test.dealFrames();
		}
	}   

	public Boolean undo() {
		undoIdHistory.undo();
		renderAtIdHistory.undo();

		if (chainOfCommands.undo()) {
			execute();
			return true;
		}
		else {
			return false;
		}	
	}
	
	public Boolean redo() {
		undoIdHistory.redo();
		renderAtIdHistory.redo();
		
		if (chainOfCommands.redo())
		{
			execute();
			return true;
		}
		else {
			return false;
		}	
	}
	
	public void store(){
		undoIdHistory.store();
		renderAtIdHistory.store();

		chainOfCommands.store();
	}

	public void addFilterInDatabase(String name, FilterControlledByFloat algoParameters) {
		dbControls.addFilter(name, algoParameters);
	}
	
	public Command getLastControl(){
		return chainOfCommands.getCommand(chainOfCommands.getSize() - 1);
	}   
	
	public int getNumberOfControl() {
		return chainOfCommands.getSize();
	}
}