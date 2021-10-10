package algo;

import baseClasses.Id;
import baseClasses.history.historyParameters.FrameHistoryParameter;
import baseClasses.history.imp.ParameterHistory;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;

public abstract class AdjustControlFrame extends AdjustControl<Frame>
{

    public AdjustControlFrame(Id id) {
    	super (id);
    	m_history = new ParameterHistory<Frame>();
    	m_history.initFactory(new FrameHistoryParameter());
    	m_history.initState(new FrameHistoryParameter());
    } 
    public AdjustControlFrame(Id id, UndoHistory<Id> undoIdHistory,UndoHistory<Id> renderAtIdHistory) {
    	super (id, undoIdHistory, renderAtIdHistory);
    	m_history = new ParameterHistory<Frame>();
    } 
    
    public void setParameter(Frame p) {
		 
        m_history.setState(new FrameHistoryParameter(p));
        if (p.compareTo(m_flags.zeroEffectValues)) {
        	m_isBypass=true;
        }
        UpdateRender();
        UpdateUndo();
    }
};