package application;

import java.util.Stack;

import baseClasses.filter.FilterControlledByFloat;
import baseClasses.openCvFacade.Frame;

public class Parameters 
{
	public Stack<Float> floatParameters;
	public Stack<String> stringParameters;
	public Stack<Integer> intParameters;
	public Frame frameParameters;
	public Boolean boolParameters;
	public FilterControlledByFloat filterParameters;
}
