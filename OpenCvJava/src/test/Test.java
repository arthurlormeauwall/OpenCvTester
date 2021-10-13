package test;

import java.util.Stack;

import baseClasses.Id;
import baseClasses.enums_structs.Action;
import baseClasses.enums_structs.Functionalities;
import baseClasses.enums_structs.GroupsId;
import baseClasses.enums_structs.Parameters;
import main.App;


public class Test {
	
	public Test(App myApp){
		// change here the path to the image you want to work with
		myApp.init("assets/20210717_203824.jpg");	

		Id Layer1_id = new Id();
		Id Control1_id = new Id();
		
		Layer1_id.set(0, 0, GroupsId.MASKED_LAYER.ordinal());
		Control1_id.set(0, 0, GroupsId.CONTROL.ordinal());

		Stack<Id> stackOfIds = new Stack<Id>();
		stackOfIds.push(Layer1_id);
		stackOfIds.push(Control1_id);

		Stack<Integer> controlIndexToAdd= new Stack<Integer>();
		controlIndexToAdd.push(1);
		
		Action action = new Action();
		action.id=new Stack<Id>();
		action.parameters= new Parameters();

				
		action.id=stackOfIds;
		action.parameters.int_parameters=controlIndexToAdd;
		action.whatToDo=Functionalities.ADD_LAYER;
		myApp.getMainWin().dealOrder(action);

		action.id=null;
		action.parameters.int_parameters=null;
		action.whatToDo=Functionalities.STORE;
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.bool_parameters=null;
		action.whatToDo=Functionalities.REFRESH;
		myApp.getMainWin().dealOrder(action);
		
		stackOfIds.clear();
		stackOfIds.push(Control1_id);
		
		action.id=stackOfIds;
		action.parameters.bool_parameters=false;
		action.whatToDo=Functionalities.SET_BYPASS;	
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.bool_parameters=null;
		action.whatToDo=Functionalities.REFRESH;
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.bool_parameters=null;
		action.whatToDo=Functionalities.UNDO;
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.bool_parameters=null;
		action.whatToDo=Functionalities.REFRESH;
		myApp.getMainWin().dealOrder(action);
	
		action.id=null;
		action.parameters.bool_parameters=null;
		action.whatToDo=Functionalities.REDO;	
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.bool_parameters=null;
		action.whatToDo=Functionalities.REFRESH;	
		myApp.getMainWin().dealOrder(action);

		Stack<Float> floatParameters=new Stack<Float>();
		floatParameters.push(0.6f);
		floatParameters.push(2f);
		floatParameters.push(0.9f);
		
		action.id=stackOfIds;
		action.parameters.float_parameters=floatParameters;
		action.whatToDo=Functionalities.SET_PARAMETERS;	
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.int_parameters=null;
		action.whatToDo=Functionalities.STORE;		
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.bool_parameters=null;
		action.whatToDo=Functionalities.REFRESH;
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.bool_parameters=null;
		action.whatToDo=Functionalities.UNDO;	
		myApp.getMainWin().dealOrder(action);	
	}
}
