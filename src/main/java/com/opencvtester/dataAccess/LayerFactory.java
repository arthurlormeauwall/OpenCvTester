package com.opencvtester.dataAccess;

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
