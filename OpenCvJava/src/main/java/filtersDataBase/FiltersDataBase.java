package filtersDataBase;

import java.util.HashMap;
import java.util.Stack;

import baseClasses.Command;
import baseClasses.filter.FilterControlledByFloat;

public class FiltersDataBase 
{
	protected HashMap<String, FilterControlledByFloat> controls;
	protected OpacityFilter alpha;
	protected EmptyFilter emptyControl;

	public FiltersDataBase() {
		controls= new HashMap<String, FilterControlledByFloat>();
		alpha = new OpacityFilter();
		emptyControl= new EmptyFilter();
	}
	
	public Command getCommand(String name){
		if (controls.get(name)== null) {
			return emptyControl;
		}
		else {
			return controls.get(name).createNew();	
		}	
	}
	
	public void addFilter(String name, FilterControlledByFloat control) {
		controls.put(name, control);
	}
	
	public OpacityFilter getAlphaControl(){
		return alpha;
	}
	
	public FilterFlags<Stack<Float>> getFlags(String name){
		if (controls.get(name)== null) {
			return emptyControl.getFlags();
		}
		else {
			return controls.get(name).getFlags();
		}
	}
}