package baseClasses.history.historyParameters;


import baseClasses.Control;
import baseClasses.history.HistoryParameter;


public class ControlHistoryParameter implements HistoryParameter<Control> 
{
	protected Control control;
	
	public ControlHistoryParameter(Control control) {
		this.control=control;
	}
	 
    public ControlHistoryParameter() { 	
    }
  
	public ControlHistoryParameter getNew() {
		return new ControlHistoryParameter();
	}
	
	public void invert() {
	}
	
	public Control getParameter() {
		return control;
	}

	public void set(Control parameter) {
		control=parameter.clone();		
	}
}