package renderingEngine;

import java.util.Stack;

import algo.AdjustControlFloat;
import baseClasses.Control;
import baseClasses.Id;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;

public class Renderer extends FrameLayer
{

	public Renderer (Frame background, Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id>  renderAtIdHistory) {
		super(id, undoIdHistory, renderAtIdHistory);
		m_background = background;
	}

	public void addControlInLayer(Stack<Id> controlId, int controlIndexInDataBase) {
		Stack<Integer> stackOfControlIndexInDataBase = new Stack<Integer>();
		stackOfControlIndexInDataBase.push(controlIndexInDataBase);
		if ( getNumberOfControl()>= controlId.get(0).get()[0]) {
			((MaskedLayer)m_chainOfControls.getControl(controlId.get(0).get()[0])).addControl(controlId, stackOfControlIndexInDataBase);
			compute();
		}	
	}
	
	public void delControlInLayer(Stack<Id> controlId){
		if (getNumberOfControl()>= controlId.get(0).get()[0]) {
			((MaskedLayer)m_chainOfControls.getControl(controlId.get(0).get()[0])).delControl(controlId);
			compute();
		}
	}   
	
	public void addLayer(Stack<Id> controlId, Stack<Integer> stackOfindexInDataBase){
		addControl(controlId, stackOfindexInDataBase);
		compute();
	}   
	
	public void delLayer(Stack<Id> controlId){
		delControl(controlId);
		compute();
	}   
	
	public void setAlpha(int layerIndex, Frame alpha){
		if (getNumberOfControl() >= layerIndex) {
			((MaskedLayer)m_chainOfControls.getControl(layerIndex)).setAlpha(alpha);
			compute();
		}	
	}   
	
	public void setAlpha(int layerIndex, int opacity){
		if (getNumberOfControl() >= layerIndex) {
			((MaskedLayer)m_chainOfControls.getControl(layerIndex)).setAlpha(opacity);
			compute();
		}
	}  
	
	public void setParameters(Id ControlId, Stack<Float> parameters){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
		if (getNumberOfControl() != 0 && getNumberOfControl() >= layerIndex &&  ((MaskedLayer)m_chainOfControls.getControl(layerIndex)).getNumberOfControl() != 0 && ((MaskedLayer)m_chainOfControls.getControl(layerIndex)).getNumberOfControl()  >= controlIndex) {
			AdjustControlFloat adjustControlToSet = (AdjustControlFloat)((MaskedLayer)m_chainOfControls.getControl(layerIndex)).getControl(controlIndex);
			adjustControlToSet.setParameter(parameters);
			compute();
		}
	} 
	
	public void setBypass(Id ControlId, Boolean p){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
		
		if ( getNumberOfControl()>=layerIndex && ((MaskedLayer)m_chainOfControls.getControl(layerIndex)).getNumberOfControl() >= controlIndex) {
			AdjustControlFloat temp = ((AdjustControlFloat)((MaskedLayer)m_chainOfControls.getControl(layerIndex)).getControl(controlIndex));
			temp.setBypass(p);
			compute();
		}
	}   

	public void dealBackground(){
		int numberOfMaskedLayers = m_chainOfControls.getSize();
		
		if (numberOfMaskedLayers>0) {
			((MaskedLayer)m_chainOfControls.getControl(0)).setBackGround(m_background);
			for (int i = 1; i < numberOfMaskedLayers; i++) {
				((MaskedLayer)m_chainOfControls.getControl(i)).setBackGround(((MaskedLayer)m_chainOfControls.getControl(i - 1)).getDest());
			}
		}	
	}   
	
	public void dealFramesInMaskedLayers(){
		for (int i = 0; i < m_chainOfControls.getSize(); i++) {

			MaskedLayer test = (MaskedLayer)getControl(i);
			test.dealFrames();
		}
	}   

	public void play(){
		m_dest.play();
	}   

	// FrameLayer implementation
	public Control createControl(Stack<Id> controlId, Stack<Integer> controlNumber){
		MaskedLayer maskedLayer = new MaskedLayer(controlId.get(0), m_undoIdHistory, m_renderAtIdHistory);
		maskedLayer.init(m_background, m_source, m_dest);
		
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
		return m_chainOfControls.getControl(m_chainOfControls.getSize() - 1);
	}   
	
	public int getNumberOfControl() {
		return m_chainOfControls.getSize();
	}
	
	//Control implementation
	public void compute(){
		dealFrames();
		dealBackground();
		dealFramesInMaskedLayers();
		render();
	}
	
	public Boolean undo() {
		m_undoIdHistory.undo();
		m_renderAtIdHistory.undo();

		if (m_chainOfControls.undo()) {
			compute();
			return true;
		}
		else {
			return false;
		}	
	}
	
	public Boolean redo() {
		m_undoIdHistory.redo();
		m_renderAtIdHistory.redo();
		
		if (m_chainOfControls.redo())
		{
			compute();
			return true;
		}
		else {
			return false;
		}	
	}
	
	public void store(){
		m_undoIdHistory.store();
		m_renderAtIdHistory.store();

		m_chainOfControls.store();
	}

	@Override
	public Control clone() {
		// TODO Auto-generated method stub
		return null;
	}

	protected Frame m_background;
};