package baseClasses.history.historyParameters;


import baseClasses.Control;
import baseClasses.history.HistoryParameter;


public class ControlHistoryParameter implements HistoryParameter<Control>
{
	 public ControlHistoryParameter(Control parameter) {
		 m_p=parameter;
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

	public void set(Control parameter) {
		m_p=parameter.clone();		
	}
    
	protected Control m_p;
};