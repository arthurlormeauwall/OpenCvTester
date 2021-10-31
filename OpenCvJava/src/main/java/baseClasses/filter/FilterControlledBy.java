package baseClasses.filter;

import java.util.Stack;

import baseClasses.history.imp.ParametersHistory;
import filtersDataBase.FilterFlags;

public abstract class FilterControlledBy<T> extends Filter
{
	protected ParametersHistory<T> history;
	protected FilterFlags<T> flags;
	
	public FilterControlledBy() {
		initFilterControlledBy();
	}
	
	private void initFilterControlledBy() {
		flags = new FilterFlags<T>();
		isBypass=true;	
	}

	
	public abstract void setParameter(T p);
	
	public Boolean undo() {
		if (!history.isUndoEmpty()) {
			history.undo();
		    return true;
		}
		else {
			return false;
		}
	}
	 
	public Boolean redo() {
		if (!history.isRedoEmpty()) {
		    history.redo();
		    return true;
		}
		else {
			return false;
		}
	}
	
	public void store() {
		history.store();
	}
	
	public void updateId(int groupDeepnessIndex, int newValue) {
		id.setControlOrLayer(groupDeepnessIndex, newValue);
	}
	
	public void setNames(Stack<String> names) {
		flags.filterNames = names;
	}
	
	public void setNumberOfParamters (int n) {
		flags.numberOfParameters = n;
	}
	
	public void setDefaultParameters(T p) {
		flags.defaultValues = p;
	}
	
	public void setZeroEffectValues(T p) {
		flags.zeroEffectValues = p;
	}
	
	public void reset() {
		setParameter(flags.defaultValues);
	}
	
	public FilterFlags<T> getFlags() {
		return flags;
	}

}