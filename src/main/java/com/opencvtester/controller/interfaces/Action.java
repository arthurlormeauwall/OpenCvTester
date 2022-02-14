package com.opencvtester.controller.interfaces;

import com.opencvtester.entity.enums.NatureOfAction;

public interface Action 
{
	public abstract NatureOfAction natureOfAction();
	public abstract void execute();
	public abstract void invert();
	public abstract Action clone();
}
