package com.opencvtester.data;

import java.util.LinkedHashMap;

public class FilterFlags
{	
	public String name;
	public LinkedHashMap<String, Float> defaultValues;
	public LinkedHashMap<String, Float> zeroEffectValues;
	public LinkedHashMap<String, Integer> sliderScale;
	
	public FilterFlags() {
		defaultValues= new LinkedHashMap<String, Float>();
		zeroEffectValues= new LinkedHashMap<String, Float>();
		sliderScale= new LinkedHashMap<String, Integer>();
	}
}

