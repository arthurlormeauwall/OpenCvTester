package com.opencvtester.historyManager.action;


public interface Action 
{
	public abstract boolean addOrDeleteAction();
	public abstract void execute();
	public abstract void invert();
	public abstract Action clone();
}
