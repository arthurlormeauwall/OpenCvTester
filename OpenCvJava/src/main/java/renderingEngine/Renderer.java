package renderingEngine;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.history.imp.UndoIdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;

public class Renderer extends RendererInterface
{
	public Renderer (FiltersDataBase dbControls, Frame background, Id id, UndoIdHistory<Id> undoIdHistory, UndoIdHistory<Id>  renderAtIdHistory) {
		super(dbControls, background, id, undoIdHistory, renderAtIdHistory);	
	}

	public void addControlInLayer(Stack<Id> controlId, int controlIndexInDataBase) {
		Stack<Integer> stackOfControlIndexInDataBase = new Stack<Integer>();
		stackOfControlIndexInDataBase.push(controlIndexInDataBase);
		
		if ( getNumberOfControl()> controlId.get(0).get()[0]) {
			if (((Layer)chainOfControls.getControl(controlId.get(0).get()[0])).addControl(controlId, stackOfControlIndexInDataBase)) {
				compute();
			}
		}	
	}
	
	public void delControlInLayer(Stack<Id> controlId){
		if (getNumberOfControl()> controlId.get(0).get()[0]) {
			if(((Layer)chainOfControls.getControl(controlId.get(0).get()[0])).delControl(controlId)) {
				compute();
			}
		}
	}   
	
	public void addLayer(Stack<Id> controlId, Stack<Integer> stackOfindexInDataBase){
		if (addControl(controlId, stackOfindexInDataBase)) {
			compute();
		}	
	}  

	public void delLayer(Stack<Id> controlId){
		if (delControl(controlId))
		{
			compute();
		}
	}   
	
	public void setAlpha(int layerIndex, Frame alpha){
		if (getNumberOfControl() > layerIndex) {
			((Layer)chainOfControls.getControl(layerIndex)).setAlpha(alpha);
			compute();
		}	
	}   
	
	public void setAlpha(int layerIndex, int opacity){
		if (getNumberOfControl() >layerIndex) {
			((Layer)chainOfControls.getControl(layerIndex)).setAlpha(opacity);
			compute();
		}
	}  
	
	public void setParameters(Id ControlId, Stack<Float> parameters){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
		if (getNumberOfControl() > layerIndex && ((Layer)chainOfControls.getControl(layerIndex)).getNumberOfControl()  > controlIndex) {
			FilterControlledByFloat adjustControlToSet = (FilterControlledByFloat)((Layer)chainOfControls.getControl(layerIndex)).getControl(controlIndex);
			adjustControlToSet.setParameter(parameters);
			compute();
		}
	} 
	
	public void setBypass(Id ControlId, Boolean p){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
	
		if ( getNumberOfControl()>layerIndex && ((Layer)chainOfControls.getControl(layerIndex)).getNumberOfControl() > controlIndex) {
			FilterControlledByFloat temp = ((FilterControlledByFloat)((Layer)chainOfControls.getControl(layerIndex)).getControl(controlIndex));
			temp.setBypass(p);
			compute();
		}
	}  

	public void play(){
		dest.play();
	}   
	
	protected Command createControl(Stack<Id> controlId, Stack<Integer> controlNumber){
		Layer maskedLayer = new Layer(dbControls, controlId.get(0), undoIdHistory, renderAtIdHistory);
		maskedLayer.init(m_background, source, dest);
		
		int numberOfControlToAdd = controlNumber.size();

		for (int i = 0; i < numberOfControlToAdd; i++) {
			Stack<Id> temp=new Stack<Id>();
			Stack<Integer> temp2=new Stack<Integer>();
			
			temp.push(controlId.get(i + 1));
			temp2.push(controlNumber.get(i));
			
			maskedLayer.addControl(temp, temp2);
		}
		return maskedLayer;
	} 
	
	public void compute(){
		dealFrames();
		dealBackground();
		dealFramesInMaskedLayers();
		render();	
	}

	public void dealBackground(){
		int numberOfMaskedLayers = chainOfControls.getSize();
		
		if (numberOfMaskedLayers>0) {
			((Layer)chainOfControls.getControl(0)).setBackGround(m_background);
			for (int i = 1; i < numberOfMaskedLayers; i++) {
				((Layer)chainOfControls.getControl(i)).setBackGround(((Layer)chainOfControls.getControl(i - 1)).getDest());
			}
		}	
	}   
	
	public void dealFramesInMaskedLayers(){
		for (int i = 0; i < chainOfControls.getSize(); i++) {

			Layer test = (Layer)getControl(i);
			test.dealFrames();
		}
	}   

	public Boolean undo() {
		undoIdHistory.undo();
		renderAtIdHistory.undo();

		if (chainOfControls.undo()) {
			compute();
			return true;
		}
		else {
			return false;
		}	
	}
	
	public Boolean redo() {
		undoIdHistory.redo();
		renderAtIdHistory.redo();
		
		if (chainOfControls.redo())
		{
			compute();
			return true;
		}
		else {
			return false;
		}	
	}
	
	public void store(){
		undoIdHistory.store();
		renderAtIdHistory.store();

		chainOfControls.store();
	}

	public void addAlgorithm(FilterControlledByFloat algoParameters) {
		dbControls.addAlgorithm(algoParameters);
	}
	
	public Command getLastControl(){
		return chainOfControls.getControl(chainOfControls.getSize() - 1);
	}   
	
	public int getNumberOfControl() {
		return chainOfControls.getSize();
	}
}