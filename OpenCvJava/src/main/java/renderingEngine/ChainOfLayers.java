package renderingEngine;

import java.util.LinkedHashMap;
import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.filter.Filter;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.frame.Frame;
import filtersDataBase.FiltersDataBase;
import guiManager.GroupsId;
import renderingEngine.renderer.ChainOfLayersRenderer;

public class ChainOfLayers extends CompositeFilter
{
	protected Frame background;
		
	public ChainOfLayers (FiltersDataBase dbControls, Frame background, Id id) {
		super(dbControls, id);
		groupID=GroupsId.LAYER;
		this.background = background;
		renderer=new ChainOfLayersRenderer(this, background);
	}
	
	
	public Layer createLayer(Stack<Id> filterId, Stack<String> filterNames) {
		Layer newLayer = (Layer)create(filterId, filterNames);
		return newLayer;
	}
	
	public Layer addLayer(Stack<Id> FilterId, Stack<String> filterNames){
		
		Layer newLayer = (Layer)createAndAdd(FilterId, filterNames);
		newLayer.activate();
		execute();
		return newLayer;
	}
	
	public Layer addLayer(Layer layer) {
		add(layer);
		layer.activate();
		execute();
		return layer;
	}

	public Layer delLayer(Id layerId){
		Layer newLayer = (Layer)delete(layerId);
		if (getNumberOfLayers()==1) {
			((Filter)chainOfFilters.getCommand(0)).activate();		
		}
		else if (getNumberOfLayers()>1) {
			((Filter)chainOfFilters.getCommand(layerId.get()[0]-1)).activate();
		}
		execute();
		return newLayer;		
	}  
	
	public Layer delLayer(Layer layer){
		
		Id layerId=layer.getId();
		Layer newLayer = (Layer)delete(layerId);
		
		if (getNumberOfLayers()==1) {
			((Filter)chainOfFilters.getCommand(0)).activate();		
		}
		else if (getNumberOfLayers()>1) {
			((Filter)chainOfFilters.getCommand(layerId.get()[0]-1)).activate();
		}
		
		execute();
		return newLayer;		
	}  
	
	public Filter createFilter(Stack<Id> filterId, String filterNames) {
		Stack<String> stackOfFilterNames = new Stack<String>();
		stackOfFilterNames.push(filterNames);		
		return  ((Layer)chainOfFilters.getCommand(filterId.get(0).get()[0])).create(filterId, stackOfFilterNames);
	}
	
	public Filter addFilterInLayer(Filter filter) {
		
		filter.activate();
		((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).activate();
		((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).add(filter);
		execute();	
		return filter;
	}
	
	public Filter delFilterInLayer(Id filterId){
		if (getNumberOfLayers()> filterId.get()[0]) {
			Filter erasedFilter =((Layer)chainOfFilters.getCommand(filterId.get()[0])).delete(filterId);
			
			if (((Layer)chainOfFilters.getCommand(filterId.get()[0])).getNumberOfFilters()<2) {
				((Layer)chainOfFilters.getCommand(filterId.get()[0])).activate();
				((Layer)chainOfFilters.getCommand(filterId.get()[0])).getFilter(0).activate();		
			}
			else {
				((Layer)chainOfFilters.getCommand(filterId.get()[0])).activate();
				((Layer)chainOfFilters.getCommand(filterId.get()[0])).getFilter(filterId.get()[1]-1).activate();
			}
			
			execute();
			return erasedFilter;
		}
		else {
			return null;
		}	
	}  
	
	public Filter delFilterInLayer(Filter filter){
		if (getNumberOfLayers()> filter.getId().get()[0]) {
			
			
			if (((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).getNumberOfFilters()-1==0) {
				((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).activate();	
			}
			else if (((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).getNumberOfFilters()-1==1) {
				((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).activate();
				((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).getFilter(0).activate();		
			}
			else {
				((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).activate();
				((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).getFilter(filter.getId().get()[1]-1).activate();
			}
			
			Filter erasedFilter =((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).delete(filter.getId());
			execute();
			return erasedFilter;
		}
		else {
			return null;
		}	
	} 

	public FilterControlledByFloat createAndAddFilterInLayer(Stack<Id> filterId, String filterNames) {
		Stack<String> stackOfFilterNames = new Stack<String>();
		stackOfFilterNames.push(filterNames);
		
		FilterControlledByFloat newFilter =(FilterControlledByFloat) ((Layer)chainOfFilters.getCommand(filterId.get(0).get()[0])).createAndAdd(filterId, stackOfFilterNames);
		newFilter.activate();
		((Filter)chainOfFilters.getCommand(filterId.get(0).get()[0])).activate();
		execute();	
		return newFilter;
	}

	public void setOpacity(int layerIndex, Float opacity){
		if (getNumberOfLayers() >layerIndex) {
			((Layer)chainOfFilters.getCommand(layerIndex)).activate();
			((Layer)chainOfFilters.getCommand(layerIndex)).setOpacity(opacity);
			execute();
		}
	}  
	
	public void setParameters(Id ControlId, LinkedHashMap<String,Float> parameters){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
		if (getNumberOfLayers() > layerIndex && ((Layer)chainOfFilters.getCommand(layerIndex)).getNumberOfFilters()  > controlIndex) {
			FilterControlledByFloat adjustControlToSet = (FilterControlledByFloat)((Layer)chainOfFilters.getCommand(layerIndex)).get(controlIndex);
			adjustControlToSet.setParameter(parameters);
			execute();
		}
	} 
	
	public void setParameters(Id id, String name, Float value) {
		int layerIndex = id.get()[0];
		int filterIndex = id.get()[1];
		
		if (areIndexOutOfRange(layerIndex, filterIndex)) {
			FilterControlledByFloat adjustControlToSet = (FilterControlledByFloat)((Layer)chainOfFilters.getCommand(layerIndex)).get(filterIndex);
			adjustControlToSet.setParameter(name, value);
			((Filter)chainOfFilters.getCommand(layerIndex)).activate();

			activateNextFilterInNextLayer(layerIndex);
			execute();
		}
		
	}
	
	public Boolean areIndexOutOfRange(int layerIndex, int filterIndex) {
		return getNumberOfLayers() > layerIndex && ((Layer)chainOfFilters.getCommand(layerIndex)).getNumberOfFilters()  > filterIndex;
	}
	
	public void activateNextFilterInNextLayer(int layerIndex) {
		if (((Layer)chainOfFilters.getCommand(layerIndex+1))!=null) {
			if  (((Layer)chainOfFilters.getCommand(layerIndex+1)).getNumberOfFilters()>0) {
				((Layer)chainOfFilters.getCommand(layerIndex+1)).getFilter(0).activate();
			}
		}
	}
	
	public void setBypass(Id ControlId, Boolean p){
		int layerIndex = ControlId.get()[0];
		int controlIndex = ControlId.get()[1];
	
		if ( getNumberOfLayers()>layerIndex && ((Layer)chainOfFilters.getCommand(layerIndex)).getNumberOfFilters() > controlIndex) {
			FilterControlledByFloat temp = ((FilterControlledByFloat)((Layer)chainOfFilters.getCommand(layerIndex)).get(controlIndex));
			temp.setBypass(p);
			execute();
		}
	}    
	
	protected Filter create(Stack<Id> filterId, Stack<String> filterName){
		Layer layer = new Layer(filtersDataBase, filterId.get(0));
		layer.init(background, source, dest);
		
		if (filterName!=null) {
			int numberOfFilterToAdd = filterName.size();

			for (int i = 0; i < numberOfFilterToAdd; i++) {		
				layer.createAndAdd(filterIds(filterId, i), filterNames(filterName,i));
			}
		}
		
		return layer;
	}
	
	public Stack<Id> filterIds(Stack<Id> id, int index){
		Stack<Id> temp=new Stack<Id>();
		temp.push(id.get(index + 1));
		return temp;
	}
	public Stack<String> filterNames(Stack<String> names, int index){
		Stack<String> temp=new Stack<String>();
		temp.push(names.get(index));
		return temp;
	}
	
	public void execute() {
		renderer.execute(chainOfFilters.getChain());
	}

	public FiltersDataBase getFilterDataBase() {
		return filtersDataBase;
	}
	
	public Command getLastFilter(){
		return chainOfFilters.getCommand(chainOfFilters.getSize() - 1);
	}   
	
	public int getNumberOfLayers() {
		return chainOfFilters.getSize();
	}	
}