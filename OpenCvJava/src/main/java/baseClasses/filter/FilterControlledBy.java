package baseClasses.filter;

import java.util.HashMap;

import filtersDataBase.FilterFlags;



public abstract class FilterControlledBy<T> extends Filter
{
	protected FilterFlags<T> flags;
	protected HashMap<String, T> parametersState;
	
	public FilterControlledBy() {		
		flags.zeroEffectValues= new HashMap<String, T>();
		flags.defaultValues= new HashMap<String, T>();
		parametersState = new HashMap<String, T>();
		flags.numberOfParameters=0;	
		setParameterFlags();
	} 
	
	public abstract void setParameterFlags();

	public T getParameter(String name) {
		return parametersState.get(name);	
	}
	
	public void setParameter(String parameterName, T value) {
		parametersState.put(parameterName, value);
	}
	
	public void addParameterFlag(String name, T defaultValue, T zeroEffectValue) {
		flags.defaultValues.put(name, defaultValue);
		flags.zeroEffectValues.put(name, zeroEffectValue);
		flags.numberOfParameters ++;
	}
	
	public void setEmptyFlags() {
		flags.numberOfParameters=0;
	}	
	
	public FilterFlags<T> getFlags(){
		return flags;
	}

}public void store() {
		history.store();
	}
	
	public void updateId(int groupDeepnessIndex, int newValue) {
		id.setControlOrLayer(groupDeepnessIndex, newValue);
	}
	
	public void setNames(String name) {
		flags.filterName = name;
	}
	
	public void setNumberOfParamters (int n) {
		flags.numberOfParameters = n;
	}
	
	public void setDefaultParameters(String name, T p) {
		flags.defaultValues.put(name, p);
	}
	
	public void setZeroEffectValues(String name, T p) {
		flags.zeroEffectValues.put(name, p);
	}
	
	public void reset() {
		setParameter(flags.defaultValues);
	}
	
	public FilterFlags<T> getFlags() {
		return flags;
	}

}
