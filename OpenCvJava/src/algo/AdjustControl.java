package algo;

import java.util.Stack;

import baseClasses.Control;
import baseClasses.Id;
import baseClasses.IoFrame;
import baseClasses.enums_structs.ControlFlags;
import baseClasses.history.imp.ParameterHistory;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;


public abstract class AdjustControl<N> extends Control implements IoFrame 
{
	public AdjustControl(Id id) {
		super (id);
		m_flags = new ControlFlags<N>();
		m_isBypass=true;
	}
	 
	public AdjustControl(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) {
		super (id, undoIdHistory, renderAtIdHistory);
		m_flags = new ControlFlags<N>();
    }


	public abstract void setParameter(N p);
	    
	
	public void setNames(Stack<String> names) {
		m_flags.controlNames = names;
	}
	
	public void setNumberOfParamters (int n) {
		m_flags.numberOfParameters = n;
	}
	
	public void reset() {
		setParameter(m_flags.defaultValues);
	}
	
	public void setDefaultParameters(N p) {
		m_flags.defaultValues = p;
	}
	public void setZeroEffectValues(N p) {
		m_flags.zeroEffectValues = p;
	}
	 
	
	public ControlFlags<N> getFlags() {
		return m_flags;
	}
	
	public void updateId(int groupDeepnessIndex, int newValue) {
	
		m_id.setControlOrLayer(groupDeepnessIndex, newValue);
	}
	
	public void store() {
		m_history.store();
	}
	
	public Boolean undo() {
		if (!m_history.isUndoEmpty()) {
			m_history.undo();
		    return true;
		}
		else {
			return false;
		}
	}
	 
	public Boolean redo() {
		if (!m_history.isRedoEmpty()) {
		    m_history.redo();
		    return true;
		}
		else {
		return false;
		}
	}
	 
	public void setSource(Frame s){
		m_source=s;
	}
	public void setDest(Frame d)  {
		m_dest=d;
	}
	  
	public Frame getSource()      {
		return m_source;
	}
	public Frame getDest()        {
		return m_dest;
	}
	   
	protected ParameterHistory<N> m_history;
	protected ControlFlags<N> m_flags;
	protected Frame m_source;
	protected Frame m_dest;
};
