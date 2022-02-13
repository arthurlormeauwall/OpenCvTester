package com.opencvtester.guiManager;

import java.util.LinkedHashMap;

import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.ControlledFilter;
import com.opencvtester.dataAccess.FilterData;
import com.opencvtester.gui.FilterWidget;

public class FilterManager extends Command {
	
	protected Filter filter;
	protected FilterWidget filterWidget;
	private FilterData filterData;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public FilterManager(ControlledFilter filter, GuiManager guiManager){
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
