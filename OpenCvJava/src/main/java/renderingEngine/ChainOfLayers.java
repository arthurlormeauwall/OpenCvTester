package renderingEngine;

import java.util.HashMap;
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

	public void addFilterInLayer(Stack<Id> filterId, String filterNames) {
		Stack<String> stackOfFilterNames = new Stack<String>();
		stackOfFilterNames.push(filterNames);
		
		if ( getNumberOfFilters()> filterId.get(0).get()[0]) {
			if (((Layer)chainOfFilters.getCommand(filterId.get(0).get()[0])).addFilter(filterId, stackOfFilterNames)) {
				execute();
			}
		}	
	}
	
	public void delFilterInLayer(Stack<Id> filterId){
		if (getNumberOfFilters()> filterId.get(0).get()[0]) {
			if(((Layer)chainOfFilters.getCommand(filterId.get(0).get()[0])).delFilter(filterId)) {
				execute();
			}
		}
	}   
	
	public void addLayer(Stack<Id> controlId, Stack<String> stackOfCommandIndexInDataBase){
		if (addFilter(controlId, stackOfCommandIndexInDataBase)) {
			execute();
		}	
	}  

	public void delLayer(Stack<Id> filterId){
		if (delFilter(filterId))
		{
			execute();
		}
	}   
	
	public void setAlpha(int layerIndex, Frame alpha){
		if (getNumberOfFilters() > layerIndex) {
			((Layer)chainOfFilters.getCommand(layerIndex)).setAlpha(alpha);
			execute();
		}	
	}   
	
	public void setAlpha(int layerIndex, int opacity){
		if (getNumberOfFilters() >layerIndex) {
			((Layer)chainOfFilters.getCommand(layerIndex)).setAlpha(opacity);
			execute();
		}
	}  
	
	public void setParameters(Id ControlId, HashMap<String,Float> parameters){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
		if (getNumberOfFilters() > layerIndex && ((Layer)chainOfFilters.getCommand(layerIndex)).getNumberOfFilters()  > controlIndex) {
			FilterControlledByFloat adjustControlToSet = (FilterControlledByFloat)((Layer)chainOfFilters.getCommand(layerIndex)).getFilter(controlIndex);
			adjustControlToSet.setParameter(parameters);
			execute();
		}
	} 
	
	public void setBypass(Id ControlId, Boolean p){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
	
		if ( getNumberOfFilters()>layerIndex && ((Layer)chainOfFilters.getCommand(layerIndex)).getNumberOfFilters() > controlIndex) {
			FilterControlledByFloat temp = ((FilterControlledByFloat)((Layer)chainOfFilters.getCommand(layerIndex)).getFilter(controlIndex));
			temp.setBypass(p);
			execute();
		}
	}  

	public void play(){
		dest.play();
	}   
	
	protected Filter createFilter(Stack<Id> controlId, Stack<String> controlName){
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
		dealFramesInLayers();
		render();	
	}

	public void dealBackground(){
		int numberOfMaskedLayers = chainOfFilters.getSize();
		
		if (numberOfMaskedLayers>0) {
			((Layer)chainOfFilters.getCommand(0)).setBackGround(m_background);
			for (int i = 1; i < numberOfMaskedLayers; i++) {
				((Layer)chainOfFilters.getCommand(i)).setBackGround(((Layer)chainOfFilters.getCommand(i - 1)).getDest());
			}
		}	
	}   
	
	public void dealFramesInLayers(){
		for (int i = 0; i < chainOfFilters.getSize(); i++) {

			Layer test = (Layer)getFilter(i);
			test.dealFrames();
		}
	}   

	public Boolean undo() {
		undoIdHistory.undo();
		renderAtIdHistory.undo();

		if (chainOfFilters.undo()) {
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
		
		if (chainOfFilters.redo())
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

		chainOfFilters.store();
	}

	public void addFilterInDatabase(String name, FilterControlledByFloat filter) {
		dbControls.addFilter(name, filter);
	}
	
	public Command getLastFilter(){
		return chainOfFilters.getCommand(chainOfFilters.getSize() - 1);
	}   
	
	public int getNumberOfFilters() {
		return chainOfFilters.getSize();
	}

	
}