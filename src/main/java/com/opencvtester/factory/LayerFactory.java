package com.opencvtester.factory;

import com.opencvtester.controller.FiltersDataBase;
import com.opencvtester.controller.MainController;
import com.opencvtester.controller.layer.LayerController;
import com.opencvtester.entity.FilterData;
import com.opencvtester.entity.LayerData;
import com.opencvtester.renderer.Layer;

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
	
	public static Layer createLayer(LayerData layerData, FiltersDataBase filtersdb){
		if (layerData.getFilterNames()==null) {
			return createEmptyLayer(layerData, filtersdb);
		}else {
			Layer newLayer= new Layer(layerData, filtersdb.getOpacityFilter());
			
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
	
	public static Layer createEmptyLayer(LayerData layerData, FiltersDataBase filtersdb){

		Layer newLayer= new Layer(layerData, filtersdb.getOpacityFilter());
		return newLayer;
	}
}
