package baseClasses.filter;

import java.util.Stack;
import baseClasses.history.historyParameters.FloatHistoryParameter;
import baseClasses.history.imp.ParametersHistory;


public abstract class FilterControlledByFloat extends Filter<Stack<Float>>
{
	public FilterControlledByFloat() {
		history = new ParametersHistory<Stack<Float>>();
		history.initFactory(new FloatHistoryParameter());
		history.initState(new FloatHistoryParameter());
		initFilterControlledByFloat();
	} 
	
	public void initFilterControlledByFloat() {
		flags.filterNames = new Stack<String>();
		flags.defaultValues= new Stack<Float>();
		flags.numberOfParameters=0;
		
		history.initState(new FloatHistoryParameter());
		setParameterFlags();
		history.setState(new FloatHistoryParameter(flags.defaultValues));
		history.store();
	}
	
	public abstract FilterControlledByFloat createNew();
	public abstract void setParameterFlags();

	public Float getParameter(int index) {
		if (history.getState().getParameter().size()> index) {
			return history.getState().getParameter().get(index);
		}
		else 
		{
			return flags.zeroEffectValues.lastElement();
		}
	}
	
	public void setParameter(Stack<Float> parameter) {
		Stack<Float> parameterPassedToHistory= new Stack<Float>();
		
		if (parameter.size() > flags.numberOfParameters) {
			for (int i=0;i<flags.numberOfParameters;i++) {
				parameterPassedToHistory.set(i, parameter.get(i));
			}			
		}
		else if (parameter.size() < flags.numberOfParameters) {
			for (int i=parameter.size();i<flags.numberOfParameters;i++) {
				parameter.push(flags.defaultValues.get(i));
			}
			parameterPassedToHistory=parameter;
		}
		else {
			parameterPassedToHistory=parameter;
		}
		
		history.setState(new FloatHistoryParameter(parameterPassedToHistory));
		
		if (parameterPassedToHistory==flags.zeroEffectValues) {
			isBypass=true;
		}
		UpdateRender();
		UpdateUndo();
	}
	
	public void addParameterFlag(String name, Float defaultValue) {
		flags.filterNames.push(name);
		flags.defaultValues.push(defaultValue);
		flags.numberOfParameters ++;
	}
	
	public void setZeroEffectValues(Stack<Float> parameters) {
		flags.zeroEffectValues=parameters;
	}
	public void setEmptyFlags() {
		flags.numberOfParameters=0;
	}	
}