package com.opencvtester.dataAccess;

import java.util.Stack;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.guiManager.GuiManager;
import com.opencvtester.guiManager.LayerManager;
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
		return new LayerManager(createLayer(layerData, filtersDataBase), guiManager);
	}
	
	public LayerManager createEmptyLayerManager(LayerData layerData){
		return new LayerManager(createEmptyLayer(layerData,filtersDataBase), guiManager);
	}
	
	public static Layer createLayer(LayerData layerData, FiltersDataBase filtersdb){
		
		int layerIndex=layerData.layerIndex();
		Stack<String> filterNames=layerData.filterNames();

		
		Layer newLayer= new Layer(layerData, filtersdb.getOpacityFilter());
		
		if (filterNames!=null) {
			int numberOfFilterToAdd = filterNames.size();

			for (int i = 0; i < numberOfFilterToAdd; i++) {	
				newLayer.addFilter(FilterFactory.createFilter(new FilterData(layerIndex, i, filterNames.get(i),null), filtersdb));
			}
		}
		
		return newLayer;
	}
	
	public static Layer createEmptyLayer(LayerData layerData, FiltersDataBase filtersdb){
		int layerIndex=layerData.layerIndex();
		Layer newLayer= new Layer(layerData, filtersdb.getOpacityFilter());
		return newLayer;
	}
}
