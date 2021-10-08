package algo;

import baseClasses.Id;

import baseClasses.history.FrameHistoryParameter;
import baseClasses.history.imp.StateHistory;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;

public abstract class AdjustControlFrame extends AdjustControl<Frame>
{

    public AdjustControlFrame(Id id) {
    	super (id);
    	m_history = new StateHistory<Frame>();
    	m_history.setFactory(new FrameHistoryParameter());
    	m_history.setState(new FrameHistoryParameter());
    } 
    public AdjustControlFrame(Id id, UndoHistory<Id> undoIdHistory,UndoHistory<Id> renderAtIdHistory) {
    	super (id, undoIdHistory, renderAtIdHistory);
    	m_history = new StateHistory<Frame>();
    } 
    
    public void setParameter(Frame p) {
		 
        m_history.setLast(new FrameHistoryParameter(p));
        if (p.compareTo(m_flags.zeroEffectValues)) {
        	bypass=true;
        }
        UpdateRender();
        UpdateUndo();
    }
};