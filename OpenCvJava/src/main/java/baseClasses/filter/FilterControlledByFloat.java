package baseClasses.filter;


import java.util.LinkedHashMap;


public abstract class FilterControlledByFloat extends FilterControlledBy<Float>
{
	public FilterControlledByFloat() {
		initFilterControlledByFloat();
	} 
	
	@SuppressWarnings("unchecked")
	public void initFilterControlledByFloat() {
		flags.filterName = new String();
		flags.defaultValues= new LinkedHashMap<String, Float>();
		flags.zeroEffectValues= new LinkedHashMap<String, Float>();
		flags.numberOfParameters=0;
		state= new LinkedHashMap<String, Float>();
	
		setParameterFlags();
		state=(LinkedHashMap<String, Float>)flags.defaultValues.clone();
	}
	
	public abstract FilterControlledByFloat createNew();
	public abstract void setParameterFlags();

	public Float getParameter(String name) {
		return state.get(name);
	}
	
	public LinkedHashMap<String, Float> getParameters() {
		return state;
	}
	
	public void setParameter(LinkedHashMap<String, Float> parameter) {
		
		state=parameter;
		
		if (parameter==flags.zeroEffectValues) {
			isBypass=true;
		}
		
		activate=true;
	}
	
	public void setParameter(String name, Float parameterValue) {		
		state.put(name, parameterValue);
		activate();	
	}
	
	public void setParameter(String name, Integer value) {
		state.put(name, value.floatValue());
		activate();
	}	
	
	public void addParameterFlag(String name, Float defaultValue, Float zeroEffectValue) {
	
		flags.defaultValues.put(name, defaultValue);
		flags.zeroEffectValues.put(name, zeroEffectValue);
		flags.numberOfParameters ++;
	}
	
	public void setFilterName(String name) {
		flags.filterName=name;
	}
	
	public String getFilterName() {
		return flags.filterName;
	}
	
	public void setEmptyFlags() {
		flags.numberOfParameters=0;
	}

	
}