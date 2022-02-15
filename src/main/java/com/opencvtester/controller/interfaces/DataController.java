package com.opencvtester.controller.interfaces;

import com.opencvtester.controller.layer.LayerController;
import com.opencvtester.data.FilterData;
import com.opencvtester.data.LayerData;
import com.opencvtester.filterController.ControlledFilter;
import com.opencvtester.filterController.FilterController;

public interface DataController {

	LayerData createLayerData(int layerIndex);

	void addLayer(LayerController layerManager);

	void deleteLayer(LayerController layerManager);

	FilterData createFilter(int layerIndex, int filterIndex, String filterName);

	void addFilter(FilterController filterManager);

	void deleteFilter(FilterController filterManager);

	void updateOpacity(ControlledFilter opacityFilter, Float opacity);

	void updateParameters(ControlledFilter filterToSet, String name, Float value);

	void setBypass(int layerIndex, int filterIndex, Boolean bypass);

	void clearAll();
}
