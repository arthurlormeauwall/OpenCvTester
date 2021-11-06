package renderingEngine;

import java.util.HashMap;
import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.filter.Filter;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.history.IdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;

public class ChainOfLayers extends ChainOfLayersInterface
{
	public ChainOfLayers (FiltersDataBase dbControls, Frame background, Id id, IdHistory<Id> undoIdHistory, IdHistory<Id>  renderAtIdHistory) {
		super(dbControls, background, id, renderAtIdHistory);	
	}

	public Filter addFilterInLayer(Stack<Id> filterId, String filterNames) {
		Stack<String> stackOfFilterNames = new Stack<String>();
		stackOfFilterNames.push(filterNames);
		
		Filter newFilter =((Layer)chainOfFilters.getCommand(filterId.get(0).get()[0])).addFilter(filterId, stackOfFilterNames);

		execute();	

		return newFilter;
	}
	
	public Filter addFilterInLayer(Filter filter) {
		
		((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).addFilter(filter);
		execute();	
		return filter;
	}
	
	public Filter delFilterInLayer(Stack<Id> filterId){
		if (getNumberOfFilters()> filterId.get(0).get()[0]) {
			Filter newFilter =((Layer)chainOfFilters.getCommand(filterId.get(0).get()[0])).delFilter(filterId);
			execute();
			return newFilter;
		}
		else {
			return null;
		}
		
	}   
	
	public Layer addLayer(Stack<Id> controlId, Stack<String> stackOfCommandIndexInDataBase){
		
		Layer newLayer = (Layer)addFilter(controlId, stackOfCommandIndexInDataBase);
		execute();
		return newLayer;
		
	
	}
	
	public Layer addLayer(Layer layer) {
		addFilter(layer);
		execute();
		return layer;
	}

	public Layer delLayer(Stack<Id> filterId){
		Layer newLayer = (Layer)delFilter(filterId);
		execute();
		return newLayer;
		
	}   
	
	public void setAlpha(int layerIndex, Frame alpha){
		if (getNumberOfFilters() > layerIndex) {
			((Layer)chainOfFilters.getCommand(layerIndex)).setAlpha(alpha);
			execute();
		}	
	}   
	
	public void setOpacity(int layerIndex, int opacity){
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
		Layer maskedLayer = new Layer(dbControls, controlId.get(0), renderAtIdHistory);
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