package com.opencvtester.guiManager;

import java.util.LinkedHashMap;

import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.gui.FilterWidget;

public class FilterManager extends Command {
	
	protected Filter filter;
	protected FilterWidget filterWidget;
	
	public FilterManager(FilterControlledByFloat filter, GuiManager guiManager){
		this.filter=filter;
		this.id=filter.getId();
		filterWidget=new FilterWidget(filter, guiManager);
	}
	
	public Filter getFilter() {
		return filter;
	}
	
	public FilterWidget getFilterWidget() {
		return filterWidget;
	}

	public void setEmitSignal(Boolean emitSignal) {
		filterWidget.setEmitSignal(emitSignal);	
	}

	public void updateParameterValues(LinkedHashMap<String, Float> parameters) {
		filterWidget.updateParameterValues(parameters);	
	}
}
