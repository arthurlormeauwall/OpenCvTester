package com.opencvtester.data.interfaces;

import java.util.LinkedHashMap;

public interface FilterDataInterface extends IndexInterface {

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
	
	
	public Boolean isActivate();
	public void activate() ;
	
	public void desactivate() ;
}
