package algorithmsDataBase;

import baseClasses.Control;
import baseClasses.adjustControl.AdjustControlFloat;

public class EmptyControl extends AdjustControlFloat {

	public EmptyControl() {
	}

	public void compute() {
		dest.setFrame(source.getFrame());		
	}

	public Control clone() {
		return this;
	}

	public void setFlags() {
		setEmptyFlags();	
	}

}
