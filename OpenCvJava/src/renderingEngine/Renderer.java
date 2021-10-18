package renderingEngine;

import java.util.Stack;

import algorithmsDataBase.DbControls;
import application.RendererInterface;
import baseClasses.Control;
import baseClasses.Id;
import baseClasses.adjustControl.AdjustControlFloat;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;

public class Renderer extends RendererInterface
{
	public Renderer (DbControls dbControls, Frame background, Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id>  renderAtIdHistory) {
		super(dbControls, background, id, undoIdHistory, renderAtIdHistory);	
	}

	public void addControlInLayer(Stack<Id> controlId, int controlIndexInDataBase) {
		Stack<Integer> stackOfControlIndexInDataBase = new Stack<Integer>();
		stackOfControlIndexInDataBase.push(controlIndexInDataBase);
		
		if ( getNumberOfControl()> controlId.get(0).get()[0]) {
			((MaskedLayer)chainOfControls.getControl(controlId.get(0).get()[0])).addControl(controlId, stackOfControlIndexInDataBase);
			compute();
		}	
	}
	
	public void delControlInLayer(Stack<Id> controlId){
		if (getNumberOfControl()> controlId.get(0).get()[0]) {
			((MaskedLayer)chainOfControls.getControl(controlId.get(0).get()[0])).delControl(controlId);
			compute();
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
			((MaskedLayer)chainOfControls.getControl(layerIndex)).setAlpha(alpha);
			compute();
		}	
	}   
	
	public void setAlpha(int layerIndex, int opacity){
		if (getNumberOfControl() >layerIndex) {
			((MaskedLayer)chainOfControls.getControl(layerIndex)).setAlpha(opacity);
			compute();
		}
	}  
	
	public void setParameters(Id ControlId, Stack<Float> parameters){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
		if (getNumberOfControl() > layerIndex && ((MaskedLayer)chainOfControls.getControl(layerIndex)).getNumberOfControl()  > controlIndex) {
			AdjustControlFloat adjustControlToSet = (AdjustControlFloat)((MaskedLayer)chainOfControls.getControl(layerIndex)).getControl(controlIndex);
			adjustControlToSet.setParameter(parameters);
			compute();
		}
	} 
	
	public void setBypass(Id ControlId, Boolean p){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
	
		if ( getNumberOfControl()>layerIndex && ((MaskedLayer)chainOfControls.getControl(layerIndex)).getNumberOfControl() > controlIndex) {
			AdjustControlFloat temp = ((AdjustControlFloat)((MaskedLayer)chainOfControls.getControl(layerIndex)).getControl(controlIndex));
			temp.setBypass(p);
			compute();
		}
	}   

	public void dealBackground(){
		int numberOfMaskedLayers = chainOfControls.getSize();
		
		if (numberOfMaskedLayers>0) {
			((MaskedLayer)chainOfControls.getControl(0)).setBackGround(m_background);
			for (int i = 1; i < numberOfMaskedLayers; i++) {
				((MaskedLayer)chainOfControls.getControl(i)).setBackGround(((MaskedLayer)chainOfControls.getControl(i - 1)).getDest());
			}
		}	
	}   
	
	public void dealFramesInMaskedLayers(){
		for (int i = 0; i < chainOfControls.getSize(); i++) {

			MaskedLayer test = (MaskedLayer)getControl(i);
			test.dealFrames();
		}
	}   

	public void play(){
		dest.play();
	}   

	protected Control createControl(Stack<Id> controlId, Stack<Integer> controlNumber){
		MaskedLayer maskedLayer = new MaskedLayer(dbControls, controlId.get(0), undoIdHistory, renderAtIdHistory);
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
	
	public Control getLastControl(){
		return chainOfControls.getControl(chainOfControls.getSize() - 1);
	}   
	
	public int getNumberOfControl() {
		return chainOfControls.getSize();
	}
	
	public void compute(){
		dealFrames();
		dealBackground();
		dealFramesInMaskedLayers();
		render();	
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

	public void addAlgorithm(AdjustControlFloat algoParameters) {
		dbControls.addAlgorithm(algoParameters);
	}
}