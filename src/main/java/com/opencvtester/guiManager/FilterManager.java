package com.opencvtester.guiManager;

import java.util.LinkedHashMap;

import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.gui.FilterWidget;

public class FilterManager extends Command {
	
	protected Filter filter;
	protected FilterWidget filterWidget;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public FilterManager(FilterControlledByFloat filter, GuiManager guiManager){
		this.filter=filter;
		this.id.set(filter);
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
}
