package application;

import java.util.Stack;

import baseClasses.Id;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.openCvFacade.Frame;

public interface FunctionalitiesInterface 
{
	public abstract void addFilterInDatabase(String name, FilterControlledByFloat filter);
	public abstract void addFilterInLayer(Stack<Id> filterId, String filterName); 	
	public abstract void delFilterInLayer(Stack<Id> filterId)  ;	
	public abstract void addLayer(Stack<Id> filtersId, Stack<String> namesOfFilters) ; 	
	public abstract void delLayer(Stack<Id> filterId) ; 	
	public abstract void setAlpha(int layerIndex, Frame alpha);	
	public abstract void setAlpha(int layerIndex, int opacity)	;
	public abstract void setParameters(Id FilterId, Stack<Float> parameters);	
	public abstract void setBypass(Id filterId, Boolean bypass)  ;
	public abstract void play();
}
