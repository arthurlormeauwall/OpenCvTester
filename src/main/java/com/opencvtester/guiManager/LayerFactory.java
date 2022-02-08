package com.opencvtester.guiManager;

import java.util.Stack;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.persistence.FilterData;
import com.opencvtester.persistence.LayerData;
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
	
	public LayerManager createLayerManager(LayerData layerData){
		Stack<Id> id= new Stack<Id>();
		int layerIndex=layerData.layerIndex();
		Stack<String> filterNames=layerData.filterNames();
		
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
				newLayer.addFilter(filtersFactory.createFilterManager(new FilterData(id.get(i + 1).layerIndex(), id.get(i + 1).filterIndex(), filterNames.get(i), null)).getFilter());
			}
		}
		
		LayerManager layerManager= new LayerManager(newLayer, guiManager);
		return layerManager;
	}
	
	public LayerManager createEmptyLayerManager(LayerData layerData){
		int layerIndex=layerData.layerIndex();
	
		Layer newLayer= new Layer(layerIndex, filtersDataBase.getOpacityFilter());
		return new LayerManager(newLayer, guiManager);
	}
	
	public static Layer createLayer(LayerData layerData, FiltersDataBase filtersdb){
//		Stack<Id> id= new Stack<Id>();
		
		int layerIndex=layerData.layerIndex();
		Stack<String> filterNames=layerData.filterNames();
		
//		id.push(createLayerId(layerIndex));
//		
//		if (filterNames!=null) {
//			for (int i=0; i< filterNames.size(); i++) {
//				id.push(createFilterId(layerIndex, i));
//			}
//		}
		
		Layer newLayer= new Layer(layerIndex, filtersdb.getOpacityFilter());
		
		if (filterNames!=null) {
			int numberOfFilterToAdd = filterNames.size();

			for (int i = 0; i < numberOfFilterToAdd; i++) {	
				newLayer.addFilter(FilterFactory.createFilter(new FilterData(layerIndex, i+1, filterNames.get(i),null), filtersdb));
			}
		}
		
	
		return newLayer;
	}
	
	public static Layer createEmptyLayer(LayerData layerData, FiltersDataBase filtersdb){
		int layerIndex=layerData.layerIndex();
		Layer newLayer= new Layer(layerIndex, filtersdb.getOpacityFilter());
		return newLayer;
	}
}
