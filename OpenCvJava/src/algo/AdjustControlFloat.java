package algo;

import java.util.Stack;

import baseClasses.Id;
import baseClasses.history.FloatHistoryParameter;
import baseClasses.history.imp.StateHistory;
import baseClasses.history.imp.UndoHistory;

public abstract class AdjustControlFloat extends AdjustControl<Stack<Float>>
{

	 public AdjustControlFloat(Id id) {
	    	super (id);
	    	m_history = new StateHistory<Stack<Float>>();
	    	m_history.setFactory(new FloatHistoryParameter());
	    	m_history.setState(new FloatHistoryParameter());
	    } 
	 
    public AdjustControlFloat(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) {
    	super (id, undoIdHistory, renderAtIdHistory);
    	m_history = new StateHistory<Stack<Float>>();
    } 
    public void setParameter(Stack<Float> p) {
    	
        m_history.setLast(new FloatHistoryParameter(p));
        if (p==m_flags.zeroEffectValues) {
        	bypass=true;
        }
        UpdateRender();
        UpdateUndo();
    }
};