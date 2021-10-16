package algorithmsDataBase;

import baseClasses.Control;
import baseClasses.Id;
import baseClasses.adjustControl.AdjustControlFloat;

public class EmptyControl extends AdjustControlFloat {

	public EmptyControl(Id id) {
		super(id);
		setEmptyFlags();
	}

	public void compute() {
		dest.setFrame(source.getFrame());		
	}

	public Control clone() {
		return this;
	}

}
