package com.opencvtester.controller.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.opencvtester.renderer.ControlledFilter;
import com.opencvtester.renderer.Layer;

public interface DataController {
	
	void addLayer(int layerIndex);
	
//	public void addLayer(Layer layer);

	void deleteLayer(int  layerIndex);
	
	void addFilter(ControlledFilter filterManager);
	
	ControlledFilter addFilter(int layerIndex,int filterInex,String name);

	void deleteFilter(int layerIndex,int filterInex);

	void setParameters(ControlledFilter filterToSet, String name, Float value);

	void setBypass(int layerIndex, int filterIndex, Boolean bypass);

	void setOpacity(int layerIndex, Float opacity);
	
	void clearAll();

	List<Layer> getLayers();
	
	ArrayList<ControlledFilter> getFilters(int layerIndex);

	void checkAndActivateLayer(int layerIndex);
}
