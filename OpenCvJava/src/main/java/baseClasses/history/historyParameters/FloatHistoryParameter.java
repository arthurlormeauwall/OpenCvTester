package baseClasses.history.historyParameters;


import java.util.HashMap;

import baseClasses.history.HistoryParameter;

public class FloatHistoryParameter implements HistoryParameter<HashMap<String, Float>> 
{
	protected HashMap<String, Float> floatsParameters;
	
    public FloatHistoryParameter() {
    }
    
    public FloatHistoryParameter(HashMap<String, Float> parameters) {
		 this.floatsParameters=parameters;
	 }

	public FloatHistoryParameter getNew() {
		return new FloatHistoryParameter();
	}
	
	public void invert() {
	}
	
	public HashMap<String, Float> getParameter() {
		return floatsParameters;
	}

	public void set(HashMap<String, Float> parameter) {
		floatsParameters=parameter;		
	}
	
	public HashMap<String, Float> clone() {
		@SuppressWarnings("unchecked")
		HashMap<String, Float> newStackOfFloats= (HashMap<String, Float>)floatsParameters.clone();
		return newStackOfFloats;
	}
	public Boolean isEmptyObject() {
		if (floatsParameters.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	public void setToEmptyObject() {
		floatsParameters.clear();
	}

	
};