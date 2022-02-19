package com.opencvtester.dataPersistence;

import java.io.Serializable;
import java.util.List;
import java.util.Stack;

import com.opencvtester.data.FilterDataInterface;
import com.opencvtester.data.LayerDataInterface;

public class SessionController implements Serializable {

	private static final long serialVersionUID = -4641469352932119900L;
	
	private String title;
	private Stack<LayerDataInterface> layers;
	private Stack<Stack<FilterDataInterface>> filters;

	public SessionController() {
		this.title=null;
		this.layers=new Stack<LayerDataInterface>();
		this.filters=new Stack<Stack<FilterDataInterface>>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<LayerDataInterface> getLayers() {
		return layers;
	}

	public void setLayers(Stack<LayerDataInterface> layers) {
		this.layers = layers;
	}

	public Stack<Stack<FilterDataInterface>> getFilters() {
		return filters;
	}

	public void setFilters(Stack<Stack<FilterDataInterface>> filters) {
		this.filters = filters;
	}
	public SessionController clone() {
		SessionController sessionTemp= new SessionController();
		for (int i=0;i<getLayers().size();i++) {
			sessionTemp.getLayers().add(getLayers().get(i));
		}
		for (int i=0;i<getFilters().size();i++) {
			sessionTemp.getFilters().add(getFilters().get(i));
		}
		return sessionTemp;
	}

	public void clear() {
		filters.clear();
		layers.clear();
		
	}
}
