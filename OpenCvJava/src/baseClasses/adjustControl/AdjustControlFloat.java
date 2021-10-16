package baseClasses.adjustControl;

import java.util.Stack;

import baseClasses.Id;
import baseClasses.history.historyParameters.FloatHistoryParameter;
import baseClasses.history.imp.ParameterHistory;
import baseClasses.history.imp.UndoHistory;

public abstract class AdjustControlFloat extends AdjustControl<Stack<Float>>
{
	public AdjustControlFloat(Id id) {
		super (id);
		history = new ParameterHistory<Stack<Float>>();
		history.initFactory(new FloatHistoryParameter());
		history.initState(new FloatHistoryParameter());
	} 
	 
	public AdjustControlFloat(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) {
		super (id, undoIdHistory, renderAtIdHistory);
		history = new ParameterHistory<Stack<Float>>();
	} 
	
	public void setParameter(Stack<Float> p) {
		history.setState(new FloatHistoryParameter(p));
		if (p==flags.zeroEffectValues) {
			isBypass=true;
		}
		UpdateRender();
		UpdateUndo();
	}
	public void addParameterToFlags(String name, Float defaultValue) {
		flags.controlNames.push(name);
		flags.defaultValues.push(defaultValue);
		flags.numberOfParameters ++;
	}
	public void setEmptyFlags() {
		flags.numberOfParameters=0;
	}
};