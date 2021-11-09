package renderingEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
	public ChainOfLayers (FiltersDataBase dbControls, Frame background, Id id, IdHistory<Id>  renderAtIdHistory) {
		super(dbControls, background, id, renderAtIdHistory);	
	}
	
	
	public Layer createLayer(Stack<Id> filterId, Stack<String> filterNames) {
		Layer newLayer = (Layer)create(filterId, filterNames);
		return newLayer;
	}
	
	public Layer addLayer(Stack<Id> FilterId, Stack<String> filterNames){
		
		Layer newLayer = (Layer)createAndAdd(FilterId, filterNames);
		execute();
		return newLayer;
	}
	
	public Layer addLayer(Layer layer) {
		add(layer);
		execute();
		return layer;
	}

	public Layer delLayer(Id layerId){
		Layer newLayer = (Layer)delete(layerId);
		execute();
		return newLayer;		
	}  
	
	public Layer delLayer(Layer layer){
		Layer newLayer = (Layer)delete(layer.getId());
		execute();
		return newLayer;		
	}  
	
	public Filter createFilter(Stack<Id> filterId, String filterNames) {
		Stack<String> stackOfFilterNames = new Stack<String>();
		stackOfFilterNames.push(filterNames);		
		return  ((Layer)chainOfFilters.getCommand(filterId.get(0).get()[0])).create(filterId, stackOfFilterNames);
	}
	
	public Filter addFilterInLayer(Filter filter) {
		
		((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).add(filter);
		execute();	
		return filter;
	}
	
	public Filter delFilterInLayer(Id filterId){
		if (getNumberOfFilters()> filterId.get()[0]) {
			Filter erasedFilter =((Layer)chainOfFilters.getCommand(filterId.get()[0])).delete(filterId);
			execute();
			return erasedFilter;
		}
		else {
			return null;
		}	
	}  
	
	public Filter delFilterInLayer(Filter filter){
		if (getNumberOfFilters()> filter.getId().get()[0]) {
			Filter erasedFilter =((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).delete(filter.getId());
			execute();
			return erasedFilter;
		}
		else {
			return null;
		}	
	} 

	public Filter createAndAddFilterInLayer(Stack<Id> filterId, String filterNames) {
		Stack<String> stackOfFilterNames = new Stack<String>();
		stackOfFilterNames.push(filterNames);
		
		Filter newFilter =((Layer)chainOfFilters.getCommand(filterId.get(0).get()[0])).createAndAdd(filterId, stackOfFilterNames);
		execute();	
		return newFilter;
	}

	public void setOpacity(int layerIndex, int opacity){
		if (getNumberOfFilters() >layerIndex) {
			((Layer)chainOfFilters.getCommand(layerIndex)).setOpacity(opacity);
			execute();
		}
	}  
	
	public void setParameters(Id ControlId, LinkedHashMap<String,Float> parameters){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
		if (getNumberOfFilters() > layerIndex && ((Layer)chainOfFilters.getCommand(layerIndex)).getNumberOfFilters()  > controlIndex) {
			FilterControlledByFloat adjustControlToSet = (FilterControlledByFloat)((Layer)chainOfFilters.getCommand(layerIndex)).get(controlIndex);
			adjustControlToSet.setParameter(parameters);
			execute();
		}
	} 
	
	public void setParameters(Id id, String name, Float value) {
		int layerIndex = id.get()[0];
		int controlIndex = id.get()[1];
		
		if (getNumberOfFilters() > layerIndex && ((Layer)chainOfFilters.getCommand(layerIndex)).getNumberOfFilters()  > controlIndex) {
			FilterControlledByFloat adjustControlToSet = (FilterControlledByFloat)((Layer)chainOfFilters.getCommand(layerIndex)).get(controlIndex);
			adjustControlToSet.setParameter(name, value);
			execute();
		}
		
	}
	
	public void setBypass(Id ControlId, Boolean p){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
	
		if ( getNumberOfFilters()>layerIndex && ((Layer)chainOfFilters.getCommand(layerIndex)).getNumberOfFilters() > controlIndex) {
			FilterControlledByFloat temp = ((FilterControlledByFloat)((Layer)chainOfFilters.getCommand(layerIndex)).get(controlIndex));
			temp.setBypass(p);
			execute();
		}
	}  

	public void play() throws IOException{
		dest.play();
	}   
	
	protected Filter create(Stack<Id> controlId, Stack<String> controlName){
		Layer maskedLayer = new Layer(filtersDataBase, controlId.get(0), renderAtIdHistory);
		maskedLayer.init(m_background, source, dest);
		
		int numberOfControlToAdd = controlName.size();

		for (int i = 0; i < numberOfControlToAdd; i++) {
			Stack<Id> temp=new Stack<Id>();
			Stack<String> temp2=new Stack<String>();
			
			temp.push(controlId.get(i + 1));
			temp2.push(controlName.get(i));
			
			maskedLayer.createAndAdd(temp, temp2);
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

			Layer test = (Layer)get(i);
			test.dealFrames();
		}
	}   


	
	public FiltersDataBase getFilterDataBase() {
		return filtersDataBase;
	}
	
	public Command getLastFilter(){
		return chainOfFilters.getCommand(chainOfFilters.getSize() - 1);
	}   
	
	public int getNumberOfFilters() {
		return chainOfFilters.getSize();
	}


	
}