package com.opencvtester.persistence;

import java.util.LinkedHashMap;

public record FilterData(int layerIndex, int filterIndex, String filterNameInDataBase, LinkedHashMap<String, Float> parameterValues) implements DataRecord {
	public FilterData(int layerIndex, int filterIndex, String filterNameInDataBase, LinkedHashMap<String, Float> parameterValues) {
		this.layerIndex=layerIndex;
		this.filterIndex=filterIndex;
		this.filterNameInDataBase=filterNameInDataBase;
		this.parameterValues=parameterValues;
	}
}
