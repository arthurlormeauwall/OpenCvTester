package filtersDataBase;

import baseClasses.filter.FilterControlledByFloat;

public class EmptyFilter extends FilterControlledByFloat {

	public EmptyFilter() {
	}

	public void execute() {
		source.copyTo(dest);
	}
	
	public EmptyFilter createNew() {
		return new EmptyFilter();
	}

	public void setParameterFlags() {
		setEmptyFlags();	
	}
}
