package com.opencvtester.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;

import com.opencvtester.entity.interfaces.DataRecord;

public class FilterData implements DataRecord, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private int layerIndex;
	private int filterIndex;
	private String filterNameInDataBase;
	private LinkedHashMap<String, Float> parameterValues;
	
	public FilterData(int layerIndex, int filterIndex, String filterNameInDataBase) {
		this.layerIndex=layerIndex;
		this.filterIndex=filterIndex;
		this.filterNameInDataBase=filterNameInDataBase;
		this.parameterValues=null;
	}
	
	public FilterData(int layerIndex, int filterIndex, String filterNameInDataBase, LinkedHashMap<String, Float> parameterValues) {
		this.layerIndex=layerIndex;
		this.filterIndex=filterIndex;
		this.filterNameInDataBase=filterNameInDataBase;
		this.parameterValues=parameterValues;
	}
	
	public int getLayerIndex() {
		return layerIndex;
	}

	public void setLayerIndex(int layerIndex) {
		this.layerIndex = layerIndex;
	}

	public int getFilterIndex() {
		return filterIndex;
	}

	public void setFilterIndex(int filterIndex) {
		this.filterIndex = filterIndex;
	}

	public String getFilterNameInDataBase() {
		return filterNameInDataBase;
	}

	public void setFilterNameInDataBase(String filterNameInDataBase) {
		this.filterNameInDataBase = filterNameInDataBase;
	}

	public LinkedHashMap<String, Float> getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(LinkedHashMap<String, Float> parameterValues) {
		this.parameterValues = parameterValues;
	}

}
