package com.opencvtester.controller.interfaces;

import com.opencvtester.controller.layer.LayerController;
import com.opencvtester.data.FilterData;
import com.opencvtester.data.LayerData;
import com.opencvtester.filterController.FilterController;
import com.opencvtester.renderer.entity.ControlledFilter;
import com.opencvtester.renderer.interfaces.IOFrame;

public interface DataController {

	LayerData createLayerData(int layerIndex);

	void addLayer(LayerController layerManager);

	void deleteLayer(LayerController layerManager);

	FilterData createFilter(int layerIndex, int filterIndex, String filterName);

	void addFilter(FilterController filterManager);

	void deleteFilter(FilterController filterManager);

	void layerIndex(int layerIndex, Float opacity);

	void setParameters(ControlledFilter filterToSet, String name, Float value);

	void setBypass(int layerIndex, int filterIndex, Boolean bypass);

	void clearAll();

	void setOpacity(int layerIndex, Float opacity);

	void checkAndActivateFilter(int layerIndex, int filterIndex);

	void checkAndActivateLayer(int layerIndex);
	
	int getNumberOfLayers();
	
	int getNumberOfFilters(int layerIndex);
}
