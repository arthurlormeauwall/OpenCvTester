package baseClasses.adjustControl;

import java.util.Stack;

import algorithmsDataBase.ControlFlags;
import baseClasses.Control;
import baseClasses.Id;
import baseClasses.IoFrame;
import baseClasses.history.imp.ParameterHistory;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;


public abstract class AdjustControl<N> extends Control implements IoFrame 
{
	protected ParameterHistory<N> history;
	protected ControlFlags<N> flags;
	protected Frame source;
	protected Frame dest;
	
	public AdjustControl(Id id) {
		super (id);
		flags = new ControlFlags<N>();
		isBypass=true;
	}
	 
	public AdjustControl(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) {
		super (id, undoIdHistory, renderAtIdHistory);
		flags = new ControlFlags<N>();
    }


	public abstract void setParameter(N p);
	    
	
	public void setNames(Stack<String> names) {
		flags.controlNames = names;
	}
	
	public void setNumberOfParamters (int n) {
		flags.numberOfParameters = n;
	}
	
	public void reset() {
		setParameter(flags.defaultValues);
	}
	
	public void setDefaultParameters(N p) {
		flags.defaultValues = p;
	}
	public void setZeroEffectValues(N p) {
		flags.zeroEffectValues = p;
	}
	 
	
	public ControlFlags<N> getFlags() {
		return flags;
	}
	
	public void updateId(int groupDeepnessIndex, int newValue) {
		id.setControlOrLayer(groupDeepnessIndex, newValue);
	}
	
	public void store() {
		history.store();
	}
	
	public Boolean undo() {
		if (!history.isUndoEmpty()) {
			history.undo();
		    return true;
		}
		else {
			return false;
		}
	}
	 
	public Boolean redo() {
		if (!history.isRedoEmpty()) {
		    history.redo();
		    return true;
		}
		else {
		return false;
		}
	}
	 
	public void setSource(Frame s){
		source=s;
	}
	public void setDest(Frame d)  {
		dest=d;
	}
	  
	public Frame getSource()      {
		return source;
	}
	public Frame getDest()        {
		return dest;
	}
};
