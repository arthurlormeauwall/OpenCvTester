package com.opencvtester.historyManager;

import java.util.Stack;

import com.opencvtester.historyManager.action.Action;

public class History {
	public Stack<Action> actions;
	public int currentIndex;
	public boolean indexIsLocked;
	
	public boolean firstUndo;
	public boolean firstRedo;
	
	public int undoCount;
	public int redoCount;
}
