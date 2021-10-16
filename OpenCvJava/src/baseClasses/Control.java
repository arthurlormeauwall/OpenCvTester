package baseClasses;

import baseClasses.history.historyParameters.IdHistoryParameter;
import baseClasses.history.imp.UndoHistory;


public abstract class Control extends Command
{
	protected Id id;
    protected UndoHistory<Id> undoIdHistory;
    protected UndoHistory<Id> renderAtIdHistory;
    protected Boolean isBypass;
    
	public Control() {    
		this.id = new Id ();   
		this.id.initNULL();
		undoIdHistory = new UndoHistory<Id>();
		renderAtIdHistory= new UndoHistory<Id>();  
 	
		initControl();
    }
    
    public Control(Id id) {	
    	this.id = new Id();
    	this.id.set(id);
	    
	    undoIdHistory = new UndoHistory<Id>();
		renderAtIdHistory= new UndoHistory<Id>(); 

	    isBypass = false;
	    
	    initControl();
    }
    
    public Control(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) {
    	this.id = new Id();
    	this.id.set(id);
	    
	    undoIdHistory = new UndoHistory<Id>();
		renderAtIdHistory= new UndoHistory<Id>(); 

	    this.undoIdHistory = undoIdHistory;
	    this.renderAtIdHistory = renderAtIdHistory;
	    isBypass = false;
	    
	    initControl();
    }
    
    public void initControl() { 	
    	undoIdHistory.initFactory(new IdHistoryParameter());
    	renderAtIdHistory.initFactory(new IdHistoryParameter());
      	undoIdHistory.initState(new IdHistoryParameter());
    	renderAtIdHistory.initState(new IdHistoryParameter());
    }
    
    public abstract void compute();

    public Id getId(){
    	return id;
    }
    
    public void setId(Id id) {
    	this.id=id;
    }

    public UndoHistory<Id> getUndoIdHistory(){
    	return undoIdHistory;
    }
    
    public UndoHistory<Id> getRenderAtIdHistory(){
    	return renderAtIdHistory; 
    }
    
    public void setUndoId(UndoHistory<Id> undoIdHistory){
    	this.undoIdHistory = undoIdHistory; 
    }
    
    public void setRenderAtId(UndoHistory<Id> renderAtIdHistory){
    	this.renderAtIdHistory = renderAtIdHistory;
    }
 
    public void updateId(int groupDeepnessIndex, int newValue) {
    	id.setControlOrLayer(groupDeepnessIndex, newValue);
    }
    
    public void UpdateRender(){
		IdHistoryParameter tempParameter= new IdHistoryParameter();
		tempParameter.set(id);
		renderAtIdHistory.setState(tempParameter);
    }
    public void UpdateUndo(){
		IdHistoryParameter temparameter= new IdHistoryParameter();
		temparameter.set(id);
		undoIdHistory.setState(temparameter);
    }
    public void storeIdHistory(){
		undoIdHistory.store();
	    renderAtIdHistory.store();	
    }
    public abstract Control clone();
    
    public Boolean getBypassState () {
    	return isBypass;
    }
    public void setBypass(Boolean bypassState) {
    	isBypass=bypassState;
    }
}