package baseClasses.adjustControl;



import java.util.Stack;

import baseClasses.history.historyParameters.FrameHistoryParameter;
import baseClasses.history.imp.ParameterHistory;
import baseClasses.openCvFacade.Frame;

public abstract class AdjustControlFrame extends AdjustControl<Frame>
{
	public AdjustControlFrame() {
		initAdjControlFrame();
	} 
	
	public void initAdjControlFrame() {
		flags.controlNames = new Stack<String>();
		flags.defaultValues= new Frame();
		flags.numberOfParameters=0;
		history = new ParameterHistory<Frame>();
		history.initFactory(new FrameHistoryParameter());
		history.initState(new FrameHistoryParameter());
	}
	    
	public void setParameter(Frame p) {			 
		history.setState(new FrameHistoryParameter(p));
		if (p.compareTo(flags.zeroEffectValues)) {
			isBypass=true;
		}
		UpdateRender();
		UpdateUndo();
	}
	public void addParameter(String name, Frame defaultValue) {
		flags.controlNames.push(name);
		flags.defaultValues = defaultValue;
		flags.numberOfParameters ++;
	}
	public void setZeroEffectValues(Frame parameter) {
		flags.zeroEffectValues=parameter;
	}
};