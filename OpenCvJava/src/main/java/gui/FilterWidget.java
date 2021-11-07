package gui;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.filter.Filter;

public class FilterWidget extends Command {
	
	protected Filter filter;
	protected Id id;
	
	public FilterWidget(Filter filter){
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

	
	public void execute() {
	}

}
