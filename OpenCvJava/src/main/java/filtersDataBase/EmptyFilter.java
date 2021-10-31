package filtersDataBase;

import baseClasses.filter.FilterControlledByFloat;

public class EmptyFilter extends FilterControlledByFloat {

	public EmptyFilter() {
	}

	public void execute() {
		dest.setFrame(source.getFrame());		
	}
	
	public EmptyFilter createNew() {
		return new EmptyFilter();
	}

	public void setParameterFlags() {
		setEmptyFlags();	
	}
}
