package guiManager;

import java.util.LinkedHashMap;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.filter.Filter;
import baseClasses.filter.FilterControlledByFloat;
import gui.FilterWidget;

public class FilterManager extends Command {
	
	protected Filter filter;
	protected Id id;
	protected FilterWidget filterWidget;
	
	public FilterManager(FilterControlledByFloat filter, GuiManager guiManager){
		this.filter=filter;
		this.id=filter.getId();
		filterWidget=new FilterWidget(filter, guiManager);
	}
	
	public void setId(Id id) {
		this.id=id;
	}

	public Id getId() {
		return id;
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
