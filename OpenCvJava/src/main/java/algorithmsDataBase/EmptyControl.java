package algorithmsDataBase;

import baseClasses.adjustControl.AdjustControlFloat;

public class EmptyControl extends AdjustControlFloat {

	public EmptyControl() {
	}

	public void compute() {
		dest.setFrame(source.getFrame());		
	}
	
	public EmptyControl createNew() {
		return new EmptyControl();
	}

	public void setParameterFlags() {
		setEmptyFlags();	
	}
}
