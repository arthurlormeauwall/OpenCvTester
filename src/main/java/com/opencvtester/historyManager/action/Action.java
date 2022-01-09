package com.opencvtester.historyManager.action;

import com.opencvtester.historyManager.HistoryReader;

public interface Action 
{
	public abstract boolean lockedSystem();
	public abstract void execute();
	public abstract void invert();
	public abstract Action clone();
	public abstract HistoryReader getHistoryReader();
}
