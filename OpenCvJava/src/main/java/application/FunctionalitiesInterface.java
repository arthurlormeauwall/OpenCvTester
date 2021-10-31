package application;

import java.util.Stack;

import baseClasses.Id;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.openCvFacade.Frame;

public interface FunctionalitiesInterface 
{
	public abstract void addAlgorithm(String name, FilterControlledByFloat algorithm);
	public abstract void addControlInLayer(Stack<Id> controlId, String controlIndexInDataBase); 	
	public abstract void delControlInLayer(Stack<Id> controlId)  ;	
	public abstract void addLayer(Stack<Id> controlId, Stack<String> stackOfindexInDataBase) ; 	
	public abstract void delLayer(Stack<Id> controlId) ; 	
	public abstract void setAlpha(int layerIndex, Frame alpha);	
	public abstract void setAlpha(int layerIndex, int opacity)	;
	public abstract void setParameters(Id ControlId, Stack<Float> parameters);	
	public abstract void setBypass(Id ControlId, Boolean p)  ;
	public abstract void play();
}
