package com.opencvtester.filtersDataBase;

import java.util.LinkedHashMap;

public class FilterFlags<T> 
{	
	public String filterName;
	public int numberOfParameters;
	public LinkedHashMap<String, T> defaultValues;
	public LinkedHashMap<String, T> zeroEffectValues;
	public LinkedHashMap<String, Integer> sliderScale;
}

