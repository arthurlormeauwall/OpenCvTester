package baseClasses.adjustControl;

import java.util.Stack;

import algorithmsDataBase.ControlFlags;
import baseClasses.history.historyParameters.FloatHistoryParameter;
import baseClasses.history.imp.ParameterHistory;
import baseClasses.openCvFacade.Frame;


public abstract class AdjustControlFloat extends AdjustControl<Stack<Float>>
{
	public AdjustControlFloat() {
		history = new ParameterHistory<Stack<Float>>();
		history.initFactory(new FloatHistoryParameter());
		history.initState(new FloatHistoryParameter());
		initAdjControlFloat();
	} 
	
	public void initAdjControlFloat() {
		flags.controlNames = new Stack<String>();
		flags.defaultValues= new Stack<Float>();
		flags.numberOfParameters=0;
		
		history.initState(new FloatHistoryParameter());
		setParameterFlags();
		history.setState(new FloatHistoryParameter(flags.defaultValues));
		history.store();
	}
	
	public abstract AdjustControlFloat createNew();

	public abstract void setParameterFlags();
	public void addParameterFlag(String name, Float defaultValue) {
		flags.controlNames.push(name);
		flags.defaultValues.push(defaultValue);
		flags.numberOfParameters ++;
	}
	
	public void setParameter(Stack<Float> p) {
		history.setState(new FloatHistoryParameter(p));
		if (p==flags.zeroEffectValues) {
			isBypass=true;
		}
		UpdateRender();
		UpdateUndo();
	}
	
	public Float getParameter(int index) {
		if (history.getState().getParameter().size()> index) {
			return history.getState().getParameter().get(index);
		}
		else 
		{
			return flags.zeroEffectValues.lastElement();
		}
	}
	
	public void setZeroEffectValues(Stack<Float> parameters) {
		flags.zeroEffectValues=parameters;
	}
	public void setEmptyFlags() {
		flags.numberOfParameters=0;
	}
	
	
}