package baseClasses.filter;

import java.util.HashMap;
import baseClasses.history.historyParameters.FloatHistoryParameter;
import baseClasses.history.imp.ParametersHistory;


public abstract class FilterControlledByFloat extends FilterControlledBy<Float>
{
	public FilterControlledByFloat() {
		history = new ParametersHistory<HashMap<String, Float>>();
		history.initFactory(new FloatHistoryParameter());
		history.initState(new FloatHistoryParameter());
		initFilterControlledByFloat();
	} 
	
	public void initFilterControlledByFloat() {
		flags.filterName = new String();
		flags.defaultValues= new HashMap<String, Float>();
		flags.zeroEffectValues= new HashMap<String, Float>();
		flags.numberOfParameters=0;
		
		history.initState(new FloatHistoryParameter());
		setParameterFlags();
		history.setState(new FloatHistoryParameter(flags.defaultValues));
		history.store();
	}
	
	public abstract FilterControlledByFloat createNew();
	public abstract void setParameterFlags();

	public Float getParameter(String name) {
		return history.getState().getParameter().get(name);
	}
	
	public void setParameter(HashMap<String, Float> parameter) {
		
		history.setState(new FloatHistoryParameter(parameter));
		if (parameter==flags.zeroEffectValues) {
			isBypass=true;
		}
		UpdateRender();
		UpdateUndo();
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