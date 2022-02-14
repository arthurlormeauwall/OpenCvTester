package com.opencvtester.controller.filter;

import java.util.LinkedHashMap;

import com.opencvtester.controller.MainController;
import com.opencvtester.entity.Command;
import com.opencvtester.entity.Filter;
import com.opencvtester.entity.FilterData;
import com.opencvtester.gui.FilterWidget;

public class FilterManager extends Command {
	
	protected Filter filter;
	protected FilterWidget filterWidget;
	private FilterData filterData;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public FilterManager(ControlledFilter filter, MainController guiManager){
		this.filterData=filter.getData();
		this.filter=filter;
		setId(filter.layerIndex(), filter.filterIndex());
		filterWidget=new FilterWidget(filter, guiManager);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public Filter getFilter() {
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

	public FilterData getData() {
		
		return filterData;
	}
}
