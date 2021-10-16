package fakeGui;

import java.util.Stack;

import application.Action;
import application.App;
import application.Functionalities;
import application.Parameters;
import baseClasses.Id;
import baseClasses.openCvFacade.Frame;
import renderingEngine.GroupsId;


public class EventGenerator 

{
	private App myApp;
	
	public EventGenerator(App app, String fileName){
		
		myApp=app;
		myApp.init(fileName);	
		
	
		
		
		/*
		
		
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

		/*
		action.id=stackOfIds;
		action.parameters.intParameters=controlIndexToAdd;
		action.whatToDo=Functionalities.ADD_LAYER;
		myApp.getMainWin().dealOrder(action);

		action.id=null;
		action.parameters.intParameters=null;
		action.whatToDo=Functionalities.STORE;
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.boolParameters=null;
		action.whatToDo=Functionalities.REFRESH;
		myApp.getMainWin().dealOrder(action);
		
		stackOfIds.clear();
		stackOfIds.push(Control1_id);
		
		action.id=stackOfIds;
		action.parameters.boolParameters=false;
		action.whatToDo=Functionalities.SET_BYPASS;	
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.boolParameters=null;
		action.whatToDo=Functionalities.REFRESH;
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.boolParameters=null;
		action.whatToDo=Functionalities.UNDO;
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.boolParameters=null;
		action.whatToDo=Functionalities.REFRESH;
		myApp.getMainWin().dealOrder(action);
	
		action.id=null;
		action.parameters.boolParameters=null;
		action.whatToDo=Functionalities.REDO;	
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.boolParameters=null;
		action.whatToDo=Functionalities.REFRESH;	
		myApp.getMainWin().dealOrder(action);

		Stack<Float> floatParameters=new Stack<Float>();
		floatParameters.push(0.6f);
		floatParameters.push(2f);
		floatParameters.push(0.9f);
		
		action.id=stackOfIds;
		action.parameters.floatParameters=floatParameters;
		action.whatToDo=Functionalities.SET_PARAMETERS;	
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.intParameters=null;
		action.whatToDo=Functionalities.STORE;		
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.boolParameters=null;
		action.whatToDo=Functionalities.REFRESH;
		myApp.getMainWin().dealOrder(action);
		
		action.id=null;
		action.parameters.boolParameters=null;
		action.whatToDo=Functionalities.UNDO;	
		myApp.getMainWin().dealOrder(action);	*/
	}
	public void addControlInLayer(int maskedLayerIndex, int controlIndex, int controlIndexInDataBase) {
		Id controlId = createControlId(maskedLayerIndex, controlIndex);
		
		Stack<Id> stackOfIds = new Stack<Id>();
		stackOfIds.push(controlId);
		
		Action action = new Action();
		action.parameters= new Parameters();
		action.parameters.intParameters=new Stack<Integer>();
		
		action.id=stackOfIds;
		action.parameters.intParameters.push(controlIndexInDataBase);		
		action.whatToDo=Functionalities.ADD_CONTROL;
		
		myApp.getMainWin().dealOrder(action);
		
	}	
	public void delControlInLayer(int maskedLayerIndex, int controlIndex)  {
		Id controlId = createControlId(maskedLayerIndex, controlIndex);
		
		Stack<Id> stackOfIds = new Stack<Id>();
		stackOfIds.push(controlId);
		
		Action action = new Action();
		action.parameters= new Parameters();
		action.parameters.intParameters=new Stack<Integer>();
		
		action.id=stackOfIds;
		action.parameters.intParameters.push(null);		
		action.whatToDo=Functionalities.DELETE_CONTROL;
		
		myApp.getMainWin().dealOrder(action);
	}	
	public void addLayer(int maskedLayerIndex, Stack<Integer> stackOfindexInDataBase) {
		Id maskedLayerId = createMaskedLayerId(maskedLayerIndex);
		Stack<Id> stackOfIds = new Stack<Id>();
		stackOfIds.push(maskedLayerId);

		for (int i=0; i< stackOfindexInDataBase.size(); i++) {
			stackOfIds.push(createControlId(maskedLayerIndex, i));
		}
		
		
		Action action = new Action();
		action.parameters= new Parameters();
				
		action.id=stackOfIds;
		action.parameters.intParameters=stackOfindexInDataBase;
		action.whatToDo=Functionalities.ADD_LAYER;
		
		myApp.getMainWin().dealOrder(action);
	}	
	public void delLayer(int maskedLayerIndex) {
		Id maskedLayerId = createMaskedLayerId(maskedLayerIndex);
		
		Stack<Id> stackOfIds = new Stack<Id>();
		stackOfIds.push(maskedLayerId);
		
		Action action = new Action();
		action.parameters= new Parameters();
				
		action.id=stackOfIds;
		action.parameters.intParameters=null;
		action.whatToDo=Functionalities.DELETE_LAYER;
		
		myApp.getMainWin().dealOrder(action);
	}	
	public void setAlpha(int maskedLayerIndex, Frame alpha) {
		Id maskedLayerId = createMaskedLayerId(maskedLayerIndex);
		
		Stack<Id> stackOfIds = new Stack<Id>();
		Stack<Frame> stackOfFrames = new Stack<Frame>();
		stackOfFrames.push(alpha);
		stackOfIds.push(maskedLayerId);
		
		Action action = new Action();
		action.parameters= new Parameters();
		
		action.id=stackOfIds;	
		action.parameters.frameParameters=stackOfFrames;
		action.whatToDo=Functionalities.SET_ALPHA_FRAME;
		
		myApp.getMainWin().dealOrder(action);		
	}		
	public void setAlpha(int maskedLayerIndex, int opacity) {
		Id maskedLayerId = createMaskedLayerId(maskedLayerIndex);
		
		Stack<Id> stackOfIds = new Stack<Id>();
		Stack<Integer> stackOfInteger = new Stack<Integer>();
		stackOfInteger.push(opacity);
		stackOfIds.push(maskedLayerId);
		
		Action action = new Action();
		action.parameters= new Parameters();
		
		action.id=stackOfIds;	
		action.parameters.intParameters=stackOfInteger;
		action.whatToDo=Functionalities.SET_ALPHA_OPACITY;
		
		myApp.getMainWin().dealOrder(action);		
	}	
	public void setParameters(int maskedLayerIndex, int controlIndex, Stack<Float> parameters){
		Id controlId = createControlId(maskedLayerIndex, controlIndex);
		
		Stack<Id> stackOfIds = new Stack<Id>();
		stackOfIds.push(controlId);
		
		Action action = new Action();
		action.parameters= new Parameters();
		
		action.id=stackOfIds;	
		action.parameters.floatParameters=parameters;
		action.whatToDo=Functionalities.SET_PARAMETERS;
		
		myApp.getMainWin().dealOrder(action);	
	}	
	public void setBypass(int maskedLayerIndex, int controlIndex, Boolean parameter) {
		Id controlId = createControlId(maskedLayerIndex, controlIndex);
		
		Stack<Id> stackOfIds = new Stack<Id>();
		stackOfIds.push(controlId);
		
		Action action = new Action();
		action.parameters= new Parameters();
		
		action.id=stackOfIds;	
		action.parameters.boolParameters=parameter;
		action.whatToDo=Functionalities.SET_BYPASS;
		
		myApp.getMainWin().dealOrder(action);
	}	
	public void undo() {
		Action action = new Action();
		action.parameters= new Parameters();
		
		action.id=null;	
		action.parameters.boolParameters=null;
		action.whatToDo=Functionalities.UNDO;
		
		myApp.getMainWin().dealOrder(action);
	}
	public void redo() {
		Action action = new Action();
		action.parameters= new Parameters();
		
		action.id=null;	
		action.parameters.boolParameters=null;
		action.whatToDo=Functionalities.REDO;
		
		myApp.getMainWin().dealOrder(action);
	}
	public void store() {
		Action action = new Action();
		action.parameters= new Parameters();
		
		action.id=null;	
		action.parameters.boolParameters=null;
		action.whatToDo=Functionalities.STORE;
		
		myApp.getMainWin().dealOrder(action);
	}
	public void play() {
	
		Action action = new Action();
		action.parameters= new Parameters();
		
		action.id=null;	
		action.parameters.boolParameters=null;
		action.whatToDo=Functionalities.REFRESH;
		
		myApp.getMainWin().dealOrder(action);
	}	
	public Id createMaskedLayerId(int maskedLayerIndex) {	
		Id id = new Id();
		id.set(maskedLayerIndex, 0, GroupsId.MASKED_LAYER.ordinal());
		return id;
	}	
	public Id createControlId(int maskedLayerIndex, int controlIndex) {
		Id id = new Id();
		id.set(maskedLayerIndex, controlIndex, GroupsId.CONTROL.ordinal());
		return id;
	}	
	
	
	
}
