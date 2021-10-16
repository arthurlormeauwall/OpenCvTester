package baseClasses.history.historyParameters;


import java.util.Stack;

import baseClasses.history.HistoryParameter;




public class FloatHistoryParameter implements HistoryParameter<Stack<Float>> 
{
	protected Stack<Float> stackOfFloats;
	
    public FloatHistoryParameter() {
    }
    
    public FloatHistoryParameter(Stack<Float> stackOfFloats) {
		 this.stackOfFloats=stackOfFloats;
	 }

	public FloatHistoryParameter getNew() {
		return new FloatHistoryParameter();
	}
	
	public void invert() {
	}
	
	public Stack<Float> getParameter() {
		return stackOfFloats;
	}

	public void set(Stack<Float> parameter) {
		stackOfFloats=parameter;		
	}
};