package application;

import java.util.HashMap;
import java.util.Stack;

import baseClasses.filter.FilterControlledByFloat;
import baseClasses.openCvFacade.Frame;
import renderingEngine.Layer;

public class Parameters 
{
	public HashMap<String,Float> floatParameters;
	public Stack<String> stringParameters;
	public Stack<Integer> intParameters;
	public Frame frameParameters;
	public Boolean boolParameters;
	public FilterControlledByFloat filterParameters;
	public Layer layer;
}
