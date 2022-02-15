package com.opencvtester.data;

import java.io.Serializable;
import java.util.LinkedHashMap;

import com.opencvtester.filterController.FilterDataInterface;

public class FilterData extends Command implements Serializable, FilterDataInterface{
	
	private static final long serialVersionUID = 1L;
	
	FilterFlags flags;
	private LinkedHashMap<String, Float> parameterValues;
	
	public FilterData(int layerIndex, int filterIndex, String filterNameInDataBase) {
		super(layerIndex, filterIndex);
	}

	@Override
	public void setBypass(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefaultValues(LinkedHashMap<String, Float> linkedHashMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZeroEffectValues(LinkedHashMap<String, Float> linkedHashMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSliderScale(LinkedHashMap<String, Float> linkedHashMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParameterValues(LinkedHashMap<String, Float> parameters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LinkedHashMap<String, Float> getDefaultValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap<String, Float> getZeroEffectValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap<String, Float> getParameterValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap<String, Integer> getSliderScale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBypass() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBypassLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	


}
