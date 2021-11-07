package baseClasses;

import baseClasses.history.IdHistory;
import baseClasses.history.IdHistoryParameter;

public abstract class Command
{
	protected Id id;
    protected IdHistory<Id> renderAtIdHistory;
    protected Boolean isBypass;
    
	public Command() {    
		this.id = new Id ();   
		this.id.initNULL();
		renderAtIdHistory= new IdHistory<Id>();  
 	
		initControl();
    } 
	
    public Command(Id id) {	
    	this.id = new Id();
    	this.id.set(id);

		renderAtIdHistory= new IdHistory<Id>(); 

	    isBypass = false;
	    
	    initControl();
    } 
    
    public Command(Id id, IdHistory<Id> renderAtIdHistory) {
    	this.id = new Id();
    	this.id.set(id);
	    
		this.renderAtIdHistory= renderAtIdHistory;
	    
	    isBypass = false;
	    
	    initControl();
    }  
    
    private void initControl() { 	
    
    	renderAtIdHistory.initFactory(new IdHistoryParameter());
    	renderAtIdHistory.initState(new IdHistoryParameter());
    }

    
    public void UpdateRender(){
		IdHistoryParameter tempParameter= new IdHistoryParameter();
		tempParameter.set(id);
		renderAtIdHistory.setState(tempParameter);
    }

    public void storeIdHistory(){
	    renderAtIdHistory.store();	
    }

    public Id getId(){
    	return id;
    }
    
    public void setId(Id id) {
    	this.id=id;
    }

    public IdHistory<Id> getRenderAtIdHistory(){
    	return renderAtIdHistory; 
    }
    
    public void setRenderAtIdHistory(IdHistory<Id> renderAtIdHistory){
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