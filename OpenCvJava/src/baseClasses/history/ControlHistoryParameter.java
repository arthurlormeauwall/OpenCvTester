package baseClasses.history;


import baseClasses.Control;


public class ControlHistoryParameter implements HistoryParameter<Control>
{
	 public ControlHistoryParameter(Control p) {
		 m_p=p;
	 }
	 
    public ControlHistoryParameter() {
    	
    }
 
    
	public ControlHistoryParameter getNew() {
		return new ControlHistoryParameter();
	}
	
	public void invert() {
	}
	
	public Control getParameter() {
		return m_p;
	}

	public void set(Control p) {
		m_p=p.clone();		
	}
    
	protected Control m_p;
};