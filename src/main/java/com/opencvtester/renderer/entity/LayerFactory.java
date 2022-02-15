package com.opencvtester.renderer.entity;

import com.opencvtester.controller.MainController;
import com.opencvtester.controller.layer.LayerController;
import com.opencvtester.data.FilterData;
import com.opencvtester.data.LayerData;
import com.opencvtester.renderer.FiltersDataBase;

public class LayerFactory {
	
	private FiltersDataBase filtersDataBase;
	private MainController guiManager;

	public LayerFactory(FiltersDataBase filtersDataBase, MainController guiManager) {
		this.filtersDataBase=filtersDataBase;
		this.guiManager=guiManager;
	}
	
	public LayerController createLayerManager(LayerData layerData){
		return new LayerController(createLayer(layerData, filtersDataBase), guiManager);
	}
	
	public LayerController createEmptyLayerManager(LayerData layerData){
		return new LayerController(createEmptyLayer(layerData,filtersDataBase), guiManager);
	}
	
	public static LayerData createLayer(LayerData layerData, FiltersDataBase filtersdb){
		if (layerData.getFilterNames()==null) {
			return createEmptyLayer(layerData, filtersdb);
		}else {
			LayerData newLayer= new LayerData(layerData, filtersdb.getOpacityFilter());
			
			for (int i=0;i<layerData.getFilterNames().size();i++) {
				newLayer.addFilter(
						filtersdb.getFilter(
								layerData.getFilterNames().get(i), new FilterData(
																			layerData.getLayerIndex(), i,layerData.getFilterNames().get(i)
																			)
								 )
						 );
			}
			return newLayer;
		}
	}
	
	public static LayerData createEmptyLayer(LayerData layerData, FiltersDataBase filtersdb){

		LayerData newLayer= new LayerData(layerData, filtersdb.getOpacityFilter());
		return newLayer;
	}
}
