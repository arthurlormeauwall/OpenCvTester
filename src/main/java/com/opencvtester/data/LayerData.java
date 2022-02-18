package com.opencvtester.data;

import java.util.ArrayList;
import java.util.List;

import com.opencvtester.data.interfaces.LayerDataInterface;
import com.opencvtester.renderer.ControlledFilter;

public class LayerData extends Index implements LayerDataInterface
{
	private List<ControlledFilter> filters;
	
	public LayerData() {
		super(0,-2);
		filters = new ArrayList<ControlledFilter>();
	}
	
	public LayerData (int layerIndex, List<ControlledFilter> filters) {
		super(layerIndex,-2);
		this.filters=filters;
	}
	
	public int getNumberOfFilters() {
		return filters.size();
	}

	public Boolean hasFilter() {
		return (getNumberOfFilters()!=0);
	}
}
