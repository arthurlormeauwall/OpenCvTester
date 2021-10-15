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
		m_history = new ParameterHistory<Stack<Float>>();
		m_history.initFactory(new FloatHistoryParameter());
		m_history.initState(new FloatHistoryParameter());
	} 
	 
	public AdjustControlFloat(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) {
		super (id, undoIdHistory, renderAtIdHistory);
		m_history = new ParameterHistory<Stack<Float>>();
	} 
	
	public void setParameter(Stack<Float> p) {
		m_history.setState(new FloatHistoryParameter(p));
		if (p==m_flags.zeroEffectValues) {
			m_isBypass=true;
		}
		UpdateRender();
		UpdateUndo();
	}
};