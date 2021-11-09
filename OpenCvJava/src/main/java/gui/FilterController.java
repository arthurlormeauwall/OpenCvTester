package gui;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.filter.Filter;
import baseClasses.filter.FilterControlledByFloat;
import gui.widget.FilterWidget;

public class FilterController extends Command {
	
	protected Filter filter;
	protected Id id;
	protected FilterWidget filterWidget;
	
	public FilterController(FilterControlledByFloat filter){
		this.filter=filter;
	
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

}
