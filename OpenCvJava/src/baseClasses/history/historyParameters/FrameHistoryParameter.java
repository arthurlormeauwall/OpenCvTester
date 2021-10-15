package baseClasses.history.historyParameters;



import baseClasses.history.HistoryParameter;
import baseClasses.openCvFacade.Frame;

public class FrameHistoryParameter implements HistoryParameter<Frame> 
{
	protected Frame m_p;
	
    public FrameHistoryParameter() {
    	m_p=new Frame();
    }
    
    public FrameHistoryParameter(Frame parameter) {
    	m_p=parameter;
    }
 
	public FrameHistoryParameter getNew() {
		return new FrameHistoryParameter();
	}
	
	public void invert() {   
	}
	
	public Frame getParameter() {
		return m_p;
	}
    
	public void set(Frame parameter) {
		m_p=parameter;		
	}
};