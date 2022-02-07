package com.opencvtester.guiManager;

import java.util.Stack;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.renderingEngine.Layer;

public class LayerFactory {
	
	private FiltersDataBase filtersDataBase;
	private GuiManager guiManager;
	private static FilterFactory filtersFactory;

	public LayerFactory(FiltersDataBase filtersDataBase, GuiManager guiManager) {
		this.filtersDataBase=filtersDataBase;
		this.guiManager=guiManager;
		LayerFactory.filtersFactory=new FilterFactory(filtersDataBase, guiManager);
	}
	
	private static Id createLayerId(int layerIndex) {	
		Id id = new Id();
		id.set(layerIndex, 0);
		return id;
	}	
	
	private static Id createFilterId(int layerIndex, int filterIndex) {
		Id id = new Id();
		id.set(layerIndex, filterIndex);
		return id;
	}
	
	public LayerManager createLayerManager(int layerIndex, Stack<String> filterNames){
		Stack<Id> id= new Stack<Id>();
		id.push(createLayerId(layerIndex));
		
		if (filterNames!=null) {
			for (int i=0; i< filterNames.size(); i++) {
				id.push(createFilterId(layerIndex, i));
			}
		}
		
		Layer newLayer= new Layer(id.get(0).layerIndex(), filtersDataBase.getOpacityFilter());
		
		if (filterNames!=null) {
			int numberOfFilterToAdd = filterNames.size();

			for (int i = 0; i < numberOfFilterToAdd; i++) {	
				newLayer.addFilter(filtersFactory.createFilterManager(id.get(i + 1), filterNames.get(i)).getFilter());
			}
		}
		
		LayerManager layerManager= new LayerManager(newLayer, guiManager);
		return layerManager;
	}
	
	public LayerManager createEmptyLayerManager(int layerIndex){
		Layer newLayer= new Layer(layerIndex, filtersDataBase.getOpacityFilter());
		return new LayerManager(newLayer, guiManager);
	}
	
	public static Layer createLayer(int layerIndex, Stack<String> filterNames,FiltersDataBase filtersdb){
		Stack<Id> id= new Stack<Id>();
		id.push(createLayerId(layerIndex));
		
		if (filterNames!=null) {
			for (int i=0; i< filterNames.size(); i++) {
				id.push(createFilterId(layerIndex, i));
			}
		}
		
		Layer newLayer= new Layer(id.get(0).layerIndex(), filtersdb.getOpacityFilter());
		
		if (filterNames!=null) {
			int numberOfFilterToAdd = filterNames.size();

			for (int i = 0; i < numberOfFilterToAdd; i++) {	
				newLayer.addFilter(FilterFactory.createFilter(id.get(i + 1), filterNames.get(i), filtersdb));
			}
		}
		
	
		return newLayer;
	}
	
	public static Layer createEmptyLayer(int layerIndex, FiltersDataBase filtersdb){
		Layer newLayer= new Layer(layerIndex, filtersdb.getOpacityFilter());
		return newLayer;
	}
}
