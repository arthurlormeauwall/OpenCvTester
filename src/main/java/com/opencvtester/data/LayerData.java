package com.opencvtester.data;

import java.io.Serializable;

import com.opencvtester.data.interfaces.IndexInterface;
import com.opencvtester.data.interfaces.LayerDataInterface;

public class LayerData implements LayerDataInterface, Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4899123066734214677L;
	private Float opacity;
	private int numberOfFilters;
	private Index index;
	
	public LayerData() {
		index = new Index(0,-2);
		opacity=1f;
		setNumberOfFilters(0);
	}
	
	public LayerData (int layerIndex) {
		index = new Index(layerIndex,-2);
		opacity=1f;
		setNumberOfFilters(0);
	}

	public Float getOpacity() {
		return opacity;
	}

	public void setOpacity(Float opacity) {
		this.opacity = opacity;
	}

	public int getNumberOfFilters() {
		return numberOfFilters;
	}

	public void setNumberOfFilters(int numberOfFilter) {
		this.numberOfFilters = numberOfFilter;
	}

	@Override
	public IndexInterface getIndexData() {
		return index;
	}
}
