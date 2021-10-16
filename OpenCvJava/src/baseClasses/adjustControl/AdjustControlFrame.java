package baseClasses.adjustControl;

import baseClasses.history.historyParameters.FrameHistoryParameter;
import baseClasses.history.imp.ParameterHistory;
import baseClasses.openCvFacade.Frame;

public abstract class AdjustControlFrame extends AdjustControl<Frame>
{
	public AdjustControlFrame() {
		initAdjControlFrame();
	} 
	
	public void initAdjControlFrame() {
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
};