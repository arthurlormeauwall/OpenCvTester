package baseClasses.history;



import baseClasses.openCvFacade.Frame;

public class FrameHistoryParameter implements HistoryParameter<Frame>
{

    public FrameHistoryParameter() {
    	m_p=new Frame();
    }
    public FrameHistoryParameter(Frame p) {
    	m_p=p;
    }
 
    
	public FrameHistoryParameter getNew() {
		return new FrameHistoryParameter();
	}
	
	public void invert() {   
	}
	
	public Frame getParameter() {
		return m_p;
	}
    
	public void set(Frame p) {
		m_p=p;		
	}
	
	 protected Frame m_p;
	
};