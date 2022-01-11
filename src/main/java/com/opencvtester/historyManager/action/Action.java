package com.opencvtester.historyManager.action;

public interface Action 
{
	public abstract NatureOfAction natureOfAction();
	public abstract void execute();
	public abstract void invert();
	public abstract Action clone();
}
