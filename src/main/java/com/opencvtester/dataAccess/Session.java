package com.opencvtester.dataAccess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Session implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4641469352932119900L;
	
	private String title;
	private List<LayerData> layers;
	private List<FilterData> filters;

	public Session(List<LayerData> layers, List<FilterData> filters) {
		this.title=null;
		this.layers=layers;
		this.filters=filters;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<LayerData> getLayers() {
		return layers;
	}

	public void setLayers(List<LayerData> layers) {
		this.layers = layers;
	}

	public List<FilterData> getFilters() {
		return filters;
	}

	public void setFilters(List<FilterData> filters) {
		this.filters = filters;
	}
	public Session clone() {
		Session sessionTemp= new Session(new ArrayList<LayerData>(), new ArrayList<FilterData>());
		for (int i=0;i<getLayers().size();i++) {
			sessionTemp.getLayers().add(getLayers().get(i));
		}
		for (int i=0;i<getFilters().size();i++) {
			sessionTemp.getFilters().add(getFilters().get(i));
		}
		return sessionTemp;
	}
}
