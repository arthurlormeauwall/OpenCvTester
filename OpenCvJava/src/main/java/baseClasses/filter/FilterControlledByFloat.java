package baseClasses.filter;

import java.util.HashMap;


public abstract class FilterControlledByFloat extends FilterControlledBy<Float>
{
	public FilterControlledByFloat() {

		initFilterControlledByFloat();
	} 
	
	public void initFilterControlledByFloat() {
		flags.filterName = new String();
		flags.defaultValues= new HashMap<String, Float>();
		flags.zeroEffectValues= new HashMap<String, Float>();
		flags.numberOfParameters=0;
	
		setParameterFlags();

	}
	
	public abstract FilterControlledByFloat createNew();
	public abstract void setParameterFlags();

	public Float getParameter(String name) {
		return state.get(name);
	}
	
	public HashMap<String, Float> getParameters() {
		return state;
	}
	
	public void setParameter(HashMap<String, Float> parameter) {
		
		state=parameter;
		
		if (parameter==flags.zeroEffectValues) {
			isBypass=true;
		}
		UpdateRender();
	}
	
	public void addParameterFlag(String name, Float defaultValue, Float zeroEffectValue) {
	
		flags.defaultValues.put(name, defaultValue);
		flags.zeroEffectValues.put(name, zeroEffectValue);
		flags.numberOfParameters ++;
	}
	
	public void setEmptyFlags() {
		flags.numberOfParameters=0;
	}	
}