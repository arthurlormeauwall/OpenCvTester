package baseClasses.history.historyParameters;


import java.util.Stack;

import baseClasses.history.HistoryParameter;




public class FloatHistoryParameter implements HistoryParameter<Stack<Float>>
{

    public FloatHistoryParameter() {
    }
    
    public FloatHistoryParameter(Stack<Float> parameter) {
		 m_p=parameter;
	 }

	public FloatHistoryParameter getNew() {
		return new FloatHistoryParameter();
	}
	
	public void invert() {
	}
	
	public Stack<Float> getParameter() {
		return m_p;
	}

	public void set(Stack<Float> parameter) {
		m_p=parameter;		
	}
    
	protected Stack<Float> m_p;
};