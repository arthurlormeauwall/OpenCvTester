package com.opencvtester.filterController;

import java.util.LinkedHashMap;

public interface FilterDataInterface {

	void setBypass(boolean b);

	void setName(String name);

	void setDefaultValues(LinkedHashMap<String, Float> linkedHashMap);

	void setZeroEffectValues(LinkedHashMap<String, Float> linkedHashMap);

	void setSliderScale(LinkedHashMap<String, Float> linkedHashMap);
	
	void setParameterValues(LinkedHashMap<String, Float> parameters);

	LinkedHashMap<String, Float> getDefaultValues();

	LinkedHashMap<String, Float> getZeroEffectValues();

	String getName();

	LinkedHashMap<String, Float> getParameterValues();
	
	LinkedHashMap<String, Integer> getSliderScale();

	boolean isBypass();

	boolean isBypassLocked();	
	
	int filterIndex();
	
	int layerIndex();
	
	void activate();
}
