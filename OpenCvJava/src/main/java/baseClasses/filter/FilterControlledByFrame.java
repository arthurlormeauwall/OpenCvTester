package baseClasses.filter;



import java.util.Stack;

import baseClasses.history.historyParameters.FrameHistoryParameter;
import baseClasses.history.imp.ParametersHistory;
import baseClasses.openCvFacade.Frame;

public abstract class FilterControlledByFrame extends Filter<Frame>
{
	public FilterControlledByFrame() {
		initFilterControlledByFrame();
	} 
	
	public void initFilterControlledByFrame() {
		flags.filterNames = new Stack<String>();
		flags.defaultValues= new Frame();
		flags.numberOfParameters=0;
		history = new ParametersHistory<Frame>();
		history.initFactory(new FrameHistoryParameter());
		history.initState(new FrameHistoryParameter());
	}
	    
	public Frame getParameter() {
		return history.getState().getParameter();
	}
	
	public void setParameter(Frame frame) {			 
		history.setState(new FrameHistoryParameter(frame));
		if (frame.compareTo(flags.zeroEffectValues)) {
			isBypass=true;
		}
		UpdateRender();
		UpdateUndo();
	}
	
	public void addParameterFlag(String name, Frame defaultValue) {
		flags.filterNames.push(name);
		flags.defaultValues = defaultValue;
		flags.numberOfParameters ++;
	}
	public void setZeroEffectValues(Frame parameter) {
		flags.zeroEffectValues=parameter;
	}
}