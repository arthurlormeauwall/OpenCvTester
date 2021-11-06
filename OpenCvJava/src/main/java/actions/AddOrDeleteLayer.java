package actions;


import application.Functionalities;
import gui.UIImp;

public class AddOrDeleteLayer extends Action {

	public AddOrDeleteLayer(UIImp mainWin){
		super(mainWin);
	}
	
	@Override
	public void invert() {
		if (whatToDo== Functionalities.ADD_LAYER) {
			whatToDo=Functionalities.DELETE_LAYER;
		}
		if (whatToDo== Functionalities.DELETE_LAYER) {
			whatToDo=Functionalities.ADD_LAYER;
		}
	}

}
