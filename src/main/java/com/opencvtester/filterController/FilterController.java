package com.opencvtester.filterController;

import java.util.LinkedHashMap;

import com.opencvtester.controller.MainController;
import com.opencvtester.data.Command;
import com.opencvtester.gui.FilterWidget;
import com.opencvtester.renderer.entity.ControlledFilter;

public class FilterController extends Command {
	
	protected ControlledFilter filter;
	protected FilterWidget filterWidget;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public FilterController(ControlledFilter filter, MainController guiManager){
		this.filter=filter;
		setId(filter.layerIndex(), filter.filterIndex());
		filterWidget=new FilterWidget(filter, guiManager);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
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
