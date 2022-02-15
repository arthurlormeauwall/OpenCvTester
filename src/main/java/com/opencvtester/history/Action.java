package com.opencvtester.history;

public interface Action 
{
	public abstract NatureOfAction natureOfAction();
	public abstract void execute();
	public abstract void invert();
	public abstract Action clone();
}
