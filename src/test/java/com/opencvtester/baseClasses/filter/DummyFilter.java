package com.opencvtester.baseClasses.filter;

public class DummyFilter extends FilterControlledByFloat{

	@SuppressWarnings("unused")
	private String log;
	
	public DummyFilter() {
		setFilterName("test");
	}
	@Override
	public FilterControlledByFloat createNew() {
		
		return new DummyFilter();
	}

	@Override
	public void setParameterFlags() {
		addParameterFlag("BlueMult", 1f, 1f, 130);
		addParameterFlag("GreenMult", 1f, 1f, 130);
		addParameterFlag("RedMult", 1f, 1f, 130);	
	}

	@Override
	public void execute() {
		log = "dymmy filter is executing";	
	}

}
