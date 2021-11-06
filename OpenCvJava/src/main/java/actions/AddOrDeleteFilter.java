package actions;

import application.Functionalities;
import gui.UIImp;

public class AddOrDeleteFilter extends Action {

	public AddOrDeleteFilter(UIImp mainWin){
		super(mainWin);
	}

	@Override
	public void invert() {
		if (whatToDo== Functionalities.ADD_FILTER) {
			whatToDo=Functionalities.DELETE_FILTER;
		}
		if (whatToDo== Functionalities.DELETE_FILTER) {
			whatToDo=Functionalities.ADD_FILTER;
		}
		
	}

}
