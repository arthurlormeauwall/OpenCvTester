package com.opencvtester.historyManager;

import java.util.LinkedList;
import java.util.Stack;

import com.opencvtester.historyManager.action.Action;

public class History {
	public Stack<Action> undoList;
	public Stack<Action> redoList;
	Action currentAction;
	public int currentIndex;
	public boolean firstUndo;
	public boolean firstRedo;
}
