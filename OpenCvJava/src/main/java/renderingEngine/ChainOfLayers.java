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
		checkAndActivateLayer(newLayer.getId());
		execute();
		return newLayer;
	}
	
	public Layer addLayer(Layer layer) {
		add(layer);
		checkAndActivateLayer(layer.getId());
		execute();
		return layer;
	}

	public Layer delLayer(Id layerId){
		Layer newLayer = (Layer)delete(layerId);
		checkAndActivateLayer(new Id(layerId.layerIndex()-1, 0));
		execute();
		return newLayer;		
	}  
	
	public Layer delLayer(Layer layer){
		
		Id layerId=layer.getId();
		Layer newLayer = (Layer)delete(layerId);
		
		checkAndActivateLayer(new Id(layer.getId().layerIndex()-1, 0));
		
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
		activateFilter(filter.getId());
		execute();	
		return filter;
	}
	
	public Filter delFilterInLayer(Id filterId){
		if (getNumberOfLayers()> filterId.layerIndex()) {
			Filter erasedFilter =((Layer)chainOfFilters.getCommand(filterId.get()[0])).delete(filterId);
			
			checkAndActivateLayer(new Id(filterId.layerIndex(), filterId.filterIndex()-1));
	
			execute();
			return erasedFilter;
		}
		else {
			return null;
		}	
	}  
	
	public Filter delFilterInLayer(Filter filter){
		if (getNumberOfLayers()> filter.getId().get()[0]) {
			
			
			Filter erasedFilter =((Layer)chainOfFilters.getCommand(filter.getId().get()[0])).delete(filter.getId());
			checkAndActivateLayer(new Id(filter.getId().layerIndex(), filter.getId().filterIndex()-1));
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
		
		activateFilter(filterId.get(0));

		execute();	
		return newFilter;
	}

	public void setOpacity(int layerIndex, Float opacity){
		if (getNumberOfLayers() >layerIndex) {
			((Layer)chainOfFilters.getCommand(layerIndex)).setOpacity(opacity);
			checkAndActivateLayer(new Id(layerIndex, 0));
			execute();
		}
	}  
	
	public void setParameters(Id id, LinkedHashMap<String,Float> parameters){
		if (areIndexLegal(id.layerIndex(), id.filterIndex())) {
			FilterControlledByFloat adjustControlToSet = (FilterControlledByFloat)((Layer)chainOfFilters.getCommand(id.layerIndex())).get(id.filterIndex());
			
			adjustControlToSet.setParameter(parameters);
			
			activateFilter(id);
			
			execute();
		}
	} 
	
	public void setParameters(Id id, String name, Float value) {
		if (areIndexLegal(id.layerIndex(), id.filterIndex())) {
			FilterControlledByFloat adjustControlToSet = (FilterControlledByFloat)((Layer)chainOfFilters.getCommand(id.layerIndex())).get(id.filterIndex());
		
			adjustControlToSet.setParameters(name, value);
			
			activateFilter(id);
			
			execute();
		}
		
	}
	
	public void setBypass(Id id, Boolean bypass){
		
		if ( getNumberOfLayers()>id.layerIndex() && ((Layer)chainOfFilters.getCommand(id.layerIndex())).getNumberOfFilters() > id.filterIndex()) {
			FilterControlledByFloat filterToBypass = ((FilterControlledByFloat)((Layer)chainOfFilters.getCommand(id.layerIndex())).get(id.filterIndex()));
			
			filterToBypass.bypass(bypass);
			
			activateFilter(id);
			
			execute();
		}
	}    
	
	
	public Boolean areIndexLegal(int layerIndex, int filterIndex) {
		return getNumberOfLayers() > layerIndex && ((Layer)chainOfFilters.getCommand(layerIndex)).getNumberOfFilters()  > filterIndex;
	}
	
	public void activateFilter(Id id) {
		((Layer)chainOfFilters.getCommand(id.layerIndex())).activate();
		
		if (((Layer)chainOfFilters.getCommand(id.layerIndex())).getFilter(id.filterIndex())!=null) {
			((Layer)chainOfFilters.getCommand(id.layerIndex())).getFilter(id.filterIndex()).activate();
		}

		if (((Layer)chainOfFilters.getCommand(id.layerIndex()+1))!=null) {
			if  (((Layer)chainOfFilters.getCommand(id.layerIndex()+1)).getNumberOfFilters()>0) {
				((Layer)chainOfFilters.getCommand(id.layerIndex()+1)).getFilter(0).activate();
			}
		}
	}
	
	public void checkAndActivateLayer (Id id) {	
		if(getNumberOfLayers()!=0) {
			if (id.layerIndex()<=0){
				activateLayer(new Id(0,0));
			}
			else {
				activateLayer(id);
			}
		}
	}
	
	public void activateLayer(Id id) {
		Layer layerToActivate=((Layer)chainOfFilters.getCommand(id.layerIndex()));
		
		layerToActivate.activate();
		
		if (layerToActivate.hasFilter()) {
			if (layerToActivate.getNumberOfFilters()<2) {
				activateFilter(new Id(id.layerIndex(),0));	
			}
			else if (layerToActivate.getNumberOfFilters()>=2){
				activateFilter(new Id(id.layerIndex(),id.filterIndex()));	
			}
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