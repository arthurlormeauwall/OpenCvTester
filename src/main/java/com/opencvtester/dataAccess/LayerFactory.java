package com.opencvtester.dataAccess;

import java.util.Stack;

import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.guiManager.GuiManager;
import com.opencvtester.guiManager.LayerManager;
import com.opencvtester.renderingEngine.Layer;

public class LayerFactory {
	
	private FiltersDataBase filtersDataBase;
	private GuiManager guiManager;

	public LayerFactory(FiltersDataBase filtersDataBase, GuiManager guiManager) {
		this.filtersDataBase=filtersDataBase;
		this.guiManager=guiManager;
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

		Layer newLayer= new Layer(layerData, filtersdb.getOpacityFilter());
		return newLayer;
	}
}
