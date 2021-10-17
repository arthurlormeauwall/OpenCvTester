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
	
	public Stack<Float> clone() {
		Stack<Float> newStackOfFloats= new Stack<Float>();
		for (int i = 0; i<stackOfFloats.size();i++) {
			newStackOfFloats.push(stackOfFloats.get(i));
		}
		
		return newStackOfFloats;
	}
};