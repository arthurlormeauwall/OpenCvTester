package com.opencvtester.data.interfaces;

import java.util.LinkedHashMap;

import com.opencvtester.controller.interfaces.DataIndexProvider;

public interface FilterDataInterface extends DataIndexProvider {

	void setBypass(boolean b);

	void setName(String name);

	void setDefaultValues(LinkedHashMap<String, Float> linkedHashMap);

	void setZeroEffectValues(LinkedHashMap<String, Float> linkedHashMap);

	void setSliderScale(LinkedHashMap<String, Integer> linkedHashMap);
	
	void setParameterValues(LinkedHashMap<String, Float> parameters);

	LinkedHashMap<String, Float> getDefaultValues();

	LinkedHashMap<String, Float> getZeroEffectValues();

	String getName();

	LinkedHashMap<String, Float> getParameterValues();
	
	LinkedHashMap<String, Integer> getSliderScale();

	boolean isBypass();

	boolean isBypassLocked();

	void lockedBypass(Boolean bypass);
}
