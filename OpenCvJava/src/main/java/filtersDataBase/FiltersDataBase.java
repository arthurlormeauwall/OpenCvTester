package filtersDataBase;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.filter.FilterControlledByFloat;

public class FiltersDataBase 
{
	protected Stack<FilterControlledByFloat> controls;
	protected OpacityFilter alpha;
	protected EmptyFilter emptyControl;

	public FiltersDataBase() {
		controls= new Stack<FilterControlledByFloat>();
		alpha = new OpacityFilter();
		emptyControl= new EmptyFilter();
	}
	
	public Command getControl(int index){
		if (controls.size()-1<index) {
			return emptyControl;
		}
		else {
			return controls.get(index).createNew();	
		}	
	}
	
	public void addAlgorithm(FilterControlledByFloat control) {
		controls.push(control);
	}
	
	public OpacityFilter getAlphaControl(){
		return alpha;
	}
	
	public FilterFlags<Stack<Float>> getFlags(int index){
		if (controls.size()-1<index) {
			return emptyControl.getFlags();
		}
		else {
			return controls.get(index).getFlags();
		}
	}
}