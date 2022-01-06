package com.opencvtester.filtersDataBase;

import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;

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
