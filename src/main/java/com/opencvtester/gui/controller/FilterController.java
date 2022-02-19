package com.opencvtester.gui.controller;

import java.util.LinkedHashMap;

import com.opencvtester.app.MainController;
import com.opencvtester.gui.FilterWidget;
import com.opencvtester.renderer.ControlledFilter;

public class FilterController{
	
	protected ControlledFilter filter;
	protected FilterWidget filterWidget;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public FilterController(ControlledFilter filter, MainController guiManager){
		this.filter=filter;
		filterWidget=new FilterWidget(filter, guiManager);
	}
	
	
	/*
	 * GETTERS & SETTERS
	 */
	
	public int filterIndex() {
		return filter.filterIndex();
	}
	
	public int layerIndex() {
		return filter.layerIndex();
	}
	
	public ControlledFilter getFilter() {
		return filter;
	}
	
	public FilterWidget getFilterWidget() {
		return filterWidget;
	}

	public void setEmitSignal(Boolean emitSignal) {
		filterWidget.setEmitSignal(emitSignal);	
	}
	
	/*
	 * FEATURES
	 */
	public void updateParameterValues(LinkedHashMap<String, Float> parameters) {
		filterWidget.updateParameterValues(parameters);	
	}

	public String getName() {	
		return filter.getName();
	}
}
