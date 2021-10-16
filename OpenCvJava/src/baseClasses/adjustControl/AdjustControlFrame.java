package baseClasses.adjustControl;

import baseClasses.Id;
import baseClasses.history.historyParameters.FrameHistoryParameter;
import baseClasses.history.imp.ParameterHistory;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;

public abstract class AdjustControlFrame extends AdjustControl<Frame>
{
	public AdjustControlFrame(Id id) {
		super (id);
		history = new ParameterHistory<Frame>();
		history.initFactory(new FrameHistoryParameter());
		history.initState(new FrameHistoryParameter());
	} 
	public AdjustControlFrame(Id id, UndoHistory<Id> undoIdHistory,UndoHistory<Id> renderAtIdHistory) {
		super (id, undoIdHistory, renderAtIdHistory);
		history = new ParameterHistory<Frame>();
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