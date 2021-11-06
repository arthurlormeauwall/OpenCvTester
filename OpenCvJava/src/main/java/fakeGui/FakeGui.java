package fakeGui;

import java.util.HashMap;
import java.util.Stack;

import actions.Action;
import actions.AddFilterInDataBase;
import actions.AddOrDeleteFilter;
import actions.AddOrDeleteLayer;
import actions.Play;
import actions.SetAlpha;
import actions.SetBypass;
import actions.SetOpacity;
import actions.SetParameters;
import application.App;
import application.Functionalities;
import application.Parameters;
import baseClasses.Id;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.history.ActionsHistory;
import baseClasses.openCvFacade.Frame;
import renderingEngine.GroupsId;


public class FakeGui 

{
	private App myApp;
	private ActionsHistory history;
	private Boolean readyToStore;
	
	public FakeGui(String fileName){	
		myApp= new App();
		myApp.init(fileName);
		history=new ActionsHistory();
		readyToStore=false;
	}
	
	public FakeGui(App app, String fileName){	
		myApp=app;
		myApp.init(fileName);		
	}
	
	public void addFilterInLayer(int layerIndex, int filterIndex, String filterName) {
		Id filterId = createFilterId(layerIndex, filterIndex);
		
		Stack<Id> filtersToAddIds = new Stack<Id>();
		filtersToAddIds.push(filterId);
		
		Action action = new AddOrDeleteFilter(myApp.getMainWin());
		action.parameters= new Parameters();
		action.parameters.stringParameters=new Stack<String>();
		
		action.id=filtersToAddIds;
		action.parameters.stringParameters.push(filterName);		
		action.whatToDo=Functionalities.ADD_FILTER;
		
		myApp.getMainWin().dealOrder(action);
		history.setState(action);
		history.store();
		play();
		
	}
	
	public void delFilterInLayer(int layerIndex, int filterIndex)  {
		Id filterId = createFilterId(layerIndex, filterIndex);
		
		Stack<Id> filterToDeleteIds = new Stack<Id>();
		filterToDeleteIds.push(filterId);
		
		Action action = new AddOrDeleteFilter(myApp.getMainWin());
		action.parameters= new Parameters();
		action.parameters.intParameters=new Stack<Integer>();
		
		action.id=filterToDeleteIds;
		action.parameters.intParameters.push(null);		
		action.whatToDo=Functionalities.DELETE_FILTER;
		
		myApp.getMainWin().dealOrder(action);
		history.setState(action);
		history.store();
		play();	
	}	
	
	public void addLayer(int layerIndex, Stack<String> filtersNames) {
		Id layerId = createLayerId(layerIndex);
		Stack<Id> filtersToAddIds = new Stack<Id>();
		filtersToAddIds.push(layerId);

		for (int i=0; i< filtersNames.size(); i++) {
			filtersToAddIds.push(createFilterId(layerIndex, i));
		}
			
		Action action = new AddOrDeleteLayer(myApp.getMainWin());
		action.parameters= new Parameters();
				
		action.id=filtersToAddIds;
		action.parameters.stringParameters=filtersNames;
		action.whatToDo=Functionalities.ADD_LAYER;
		
		myApp.getMainWin().dealOrder(action);
		history.setState(action);
		history.store();
		play();
		
	}	
	
	public void delLayer(int layerIndex) {
		Id layerId = createLayerId(layerIndex);
		
		Stack<Id> layersToDeleteIds = new Stack<Id>();
		layersToDeleteIds.push(layerId);
		
		Action action = new AddOrDeleteLayer(myApp.getMainWin());
		action.parameters= new Parameters();
				
		action.id=layersToDeleteIds;
		action.parameters.intParameters=null;
		action.whatToDo=Functionalities.DELETE_LAYER;
		
		myApp.getMainWin().dealOrder(action);
		history.setState(action);
		history.store();
		play();
		
	}	
	
	public void setAlpha(int layerIndex, Frame alpha) {
		Id layerId = createLayerId(layerIndex);
		
		Stack<Id> layersIds = new Stack<Id>();
		layersIds.push(layerId);
		
		Action action = new SetAlpha(myApp.getMainWin());
		action.parameters= new Parameters();
		
		action.id=layersIds;	
		action.parameters.frameParameters=alpha;
		action.whatToDo=Functionalities.SET_ALPHA_FRAME;
		
		myApp.getMainWin().dealOrder(action);	
		history.setState(action);
		history.store();
		play();
		
	}	
	
	public void setOpacity(int layerIndex, int opacity) {
		Id layerId = createLayerId(layerIndex);
		
		Stack<Id> layersIds = new Stack<Id>();
		Stack<Integer> opacityParameter = new Stack<Integer>();
		opacityParameter.push(opacity);
		layersIds.push(layerId);
		
		Action action = new SetOpacity(myApp.getMainWin());
		action.parameters= new Parameters();
		
		action.id=layersIds;	
		action.parameters.intParameters=opacityParameter;
		action.whatToDo=Functionalities.SET_ALPHA_OPACITY;
		
		myApp.getMainWin().dealOrder(action);
		history.setState(action);
		history.store();
		play();
		
	}	
	
	public void setParameters(int layerIndex, int filterIndex, HashMap<String,Float> parametersValues){
		if (readyToStore) {
			history.store();
			readyToStore=false;
		}
		Id filterId = createFilterId(layerIndex, filterIndex);
		
		Stack<Id> filtersToAdjustIds = new Stack<Id>();
		filtersToAdjustIds.push(filterId);
		
		Action action = new SetParameters(myApp.getMainWin());
		action.parameters= new Parameters();
		
		action.id=filtersToAdjustIds;	
		action.parameters.floatParameters=parametersValues;
		action.whatToDo=Functionalities.SET_PARAMETERS;
		
		myApp.getMainWin().dealOrder(action);	
		history.setState(action);
		readyToStore=true;
		play();	
	}	
	
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass) {
		Id filterId = createFilterId(layerIndex, filterIndex);
		
		Stack<Id> filtersIds = new Stack<Id>();
		filtersIds.push(filterId);
		
		Action action = new SetBypass(myApp.getMainWin());
		action.parameters= new Parameters();
		
		action.id=filtersIds;	
		action.parameters.boolParameters=bypass;
		action.whatToDo=Functionalities.SET_BYPASS;
		
		myApp.getMainWin().dealOrder(action);
		play();	
	}
	
	public void undo() {
		history.undo();
		
	}
	
	public void redo() {
		history.redo();
	}
	
	private void play() {
	
		Action action = new Play(myApp.getMainWin());
		action.parameters= new Parameters();
		
		action.id=null;	
		action.parameters.boolParameters=null;
		action.whatToDo=Functionalities.REFRESH;
		
		myApp.getMainWin().dealOrder(action);
	}
	
	public void addFilterInDataBase(String name, FilterControlledByFloat newfilter) {
		
		Action action = new AddFilterInDataBase(myApp.getMainWin());
		action.parameters= new Parameters();
		
		action.id=null;	
		action.parameters.filterParameters=newfilter;
		action.parameters.stringParameters=new Stack<String>();
		action.parameters.stringParameters.push(name);
		action.whatToDo=Functionalities.ADD_FILTER_IN_DATABASE;
		
		myApp.getMainWin().dealOrder(action);
	}
	
	private Id createLayerId(int layerIndex) {	
		Id id = new Id();
		id.set(layerIndex, 0, GroupsId.LAYER.ordinal());
		return id;
	}	
	
	private Id createFilterId(int layerIndex, int filterIndex) {
		Id id = new Id();
		id.set(layerIndex, filterIndex, GroupsId.CONTROL.ordinal());
		return id;
	}		
}
