package com.opencvtester.data;

import java.io.Serializable;
import java.util.LinkedHashMap;

import com.opencvtester.data.interfaces.FilterDataInterface;

public class FilterData extends Index implements Serializable, FilterDataInterface{
	
	private static final long serialVersionUID = 1L;
	
	protected Boolean isBypass;
	protected FilterFlags flags;
	private LinkedHashMap<String, Float> parameterValues;

	
	public FilterData(int layerIndex, int filterIndex, String name) {
		super(layerIndex, filterIndex);
		flags=new FilterFlags();
		flags.name=name;
		parameterValues = new LinkedHashMap<String, Float>();
		isBypass= false;
	}

	@Override
	public void setBypass(boolean bypass) {
		isBypass=bypass;
	}

	@Override
	public void setName(String name) {
		flags.name=name;	
	}

	@Override
	public void setDefaultValues(LinkedHashMap<String, Float> defaultValues) {
		flags.defaultValues=defaultValues;	
	}

	@Override
	public void setZeroEffectValues(LinkedHashMap<String, Float> zeroEffectValues) {
		flags.zeroEffectValues=zeroEffectValues;
		
	}

	@Override
	public void setSliderScale(LinkedHashMap<String, Integer> sliderScale) {
		flags.sliderScale=sliderScale;
		
	}

	@Override
	public void setParameterValues(LinkedHashMap<String, Float> parameters) {
		parameterValues=parameters;		
	}

	@Override
	public LinkedHashMap<String, Float> getDefaultValues() {
		
		return flags.defaultValues;
	}

	@Override
	public LinkedHashMap<String, Float> getZeroEffectValues() {
		
		return flags.zeroEffectValues;
	}

	@Override
	public String getName() {
		
		return flags.name;
	}

	@Override
	public LinkedHashMap<String, Float> getParameterValues() {
		
		return parameterValues;
	}

	@Override
	public LinkedHashMap<String, Integer> getSliderScale() {
		
		return flags.sliderScale;
	}

	@Override
	public boolean isBypass() {
		return isBypass;
	}


}
