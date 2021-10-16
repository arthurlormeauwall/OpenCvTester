package baseClasses.adjustControl;

import java.util.Stack;

import algorithmsDataBase.ControlFlags;
import baseClasses.Control;
import baseClasses.IoFrame;
import baseClasses.history.imp.ParameterHistory;
import baseClasses.openCvFacade.Frame;


public abstract class AdjustControl<T> extends Control implements IoFrame 
{
	protected ParameterHistory<T> history;
	protected ControlFlags<T> flags;
	protected Frame source;
	protected Frame dest;
	
	public AdjustControl() {
		initAdjsutControl();
	}
	
	private void initAdjsutControl() {
		flags = new ControlFlags<T>();
		isBypass=true;	
	}

	public abstract void setParameter(T p);
	    
	
	public void setNames(Stack<String> names) {
		flags.controlNames = names;
	}
	
	public void setNumberOfParamters (int n) {
		flags.numberOfParameters = n;
	}
	
	public void reset() {
		setParameter(flags.defaultValues);
	}
	
	public void setDefaultParameters(T p) {
		flags.defaultValues = p;
	}
	public void setZeroEffectValues(T p) {
		flags.zeroEffectValues = p;
	}
	 
	
	public ControlFlags<T> getFlags() {
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
}
