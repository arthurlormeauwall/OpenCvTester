package baseClasses;

import baseClasses.history.historyParameters.IdHistoryParameter;
import baseClasses.history.imp.UndoIdHistory;

public abstract class Command extends Undoable
{
	protected Id id;
    protected UndoIdHistory<Id> undoIdHistory;
    protected UndoIdHistory<Id> renderAtIdHistory;
    protected Boolean isBypass;
    
	public Command() {    
		this.id = new Id ();   
		this.id.initNULL();
		undoIdHistory = new UndoIdHistory<Id>();
		renderAtIdHistory= new UndoIdHistory<Id>();  
 	
		initControl();
    } 
	
    public Command(Id id) {	
    	this.id = new Id();
    	this.id.set(id);
	    
	    undoIdHistory = new UndoIdHistory<Id>();
		renderAtIdHistory= new UndoIdHistory<Id>(); 

	    isBypass = false;
	    
	    initControl();
    } 
    
    public Command(Id id, UndoIdHistory<Id> undoIdHistory, UndoIdHistory<Id> renderAtIdHistory) {
    	this.id = new Id();
    	this.id.set(id);
	    
	    this.undoIdHistory = undoIdHistory;
		this.renderAtIdHistory= renderAtIdHistory;
	    
	    isBypass = false;
	    
	    initControl();
    }  
    
    private void initControl() { 	
    	undoIdHistory.initFactory(new IdHistoryParameter());
    	renderAtIdHistory.initFactory(new IdHistoryParameter());
      	undoIdHistory.initState(new IdHistoryParameter());
    	renderAtIdHistory.initState(new IdHistoryParameter());
    }
    
    public abstract void compute();
    
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

    public Id getId(){
    	return id;
    }
    
    public void setId(Id id) {
    	this.id=id;
    }

    public UndoIdHistory<Id> getUndoIdHistory(){
    	return undoIdHistory;
    }
    
    public void setUndoId(UndoIdHistory<Id> undoIdHistory){
    	this.undoIdHistory = undoIdHistory; 
    }
    
    public UndoIdHistory<Id> getRenderAtIdHistory(){
    	return renderAtIdHistory; 
    }
    
    public void setRenderAtId(UndoIdHistory<Id> renderAtIdHistory){
    	this.renderAtIdHistory = renderAtIdHistory;
    }
 
    public void updateId(int groupDeepnessIndex, int newValue) {
    	id.setControlOrLayer(groupDeepnessIndex, newValue);
    }
    
    public Boolean getBypassState () {
    	return isBypass;
    }
    
    public void setBypass(Boolean bypassState) {
    	isBypass=bypassState;
    }   
}