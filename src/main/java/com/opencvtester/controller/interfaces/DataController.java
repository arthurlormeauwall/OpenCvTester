package com.opencvtester.controller.interfaces;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.opencvtester.renderer.ControlledFilter;
import com.opencvtester.renderer.Layer;

public interface DataController {
	
	void addLayer(int layerIndex);
	
	public void addLayer(Layer layer);

	void deleteLayer(int  layerIndex);
	
	void addFilter(ControlledFilter filterManager);
	
	ControlledFilter addFilter(int layerIndex,int filterInex,String name);

	void deleteFilter(int layerIndex,int filterInex);

	void setParameters(ControlledFilter filterToSet, String name, Float value);
	
	void setParameters(ControlledFilter filterToSet, LinkedHashMap<String, Float> parameters);

	void setBypass(int layerIndex, int filterIndex, Boolean bypass);

	void setOpacity(int layerIndex, Float opacity);
	
	void clearAll();

	List<Layer> getLayers();

	List<ArrayList<ControlledFilter>> getFilters();

	void checkAndActivateLayer(int layerIndex);
}
