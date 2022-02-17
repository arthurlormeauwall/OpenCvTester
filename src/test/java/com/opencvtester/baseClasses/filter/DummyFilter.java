package com.opencvtester.baseClasses.filter;

import com.opencvtester.renderer.ControlledFilter;

public class DummyFilter extends ControlledFilter{

	@SuppressWarnings("unused")
	private String log;
	
	public DummyFilter(String fileName) {
		super(fileName);
	}
	@Override
	public ControlledFilter createNew() {
		
		return new DummyFilter(getFilterName());
	}

	@Override
	public void setParameterFlags() {
		addParameterFlag("BlueMult", 1f, 1f, 130);
		addParameterFlag("GreenMult", 1f, 1f, 130);
		addParameterFlag("RedMult", 1f, 1f, 130);	
	}

	@Override
	public void render() {
		log = "dymmy filter is executing";	
	}

}
