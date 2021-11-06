package actions;

import java.util.Stack;

import application.Functionalities;
import application.Parameters;
import baseClasses.Id;
import gui.UIImp;

public abstract class Action 
{
	public Stack<Id> id;
	public Functionalities whatToDo;
	public Parameters parameters;
	protected UIImp mainWin;
	
	Action(UIImp mainWin){
		this.mainWin=mainWin;
	}
	
	public void execute() {
		mainWin.dealOrder(this);
	}
	
	public abstract void invert();
}
