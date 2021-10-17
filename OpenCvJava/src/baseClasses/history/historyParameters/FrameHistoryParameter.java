package baseClasses.history.historyParameters;



import baseClasses.chain.ItemAndId;
import baseClasses.history.HistoryParameter;
import baseClasses.openCvFacade.Frame;

public class FrameHistoryParameter implements HistoryParameter<Frame> 
{
	protected Frame frame;
	
    public FrameHistoryParameter() {
    	frame=new Frame();
    }
    
    public FrameHistoryParameter(Frame frame) {
    	this.frame=frame;
    }
 
	public FrameHistoryParameter getNew() {
		return new FrameHistoryParameter();
	}
	
	public void invert() {   
	}
	
	public Frame getParameter() {
		return frame;
	}
    
	public void set(Frame parameter) {
		frame=parameter;		
	}
	public Frame clone() {
		Frame newFrame= new Frame();
		frame.copyTo(newFrame);
	
		return newFrame;
	}
};