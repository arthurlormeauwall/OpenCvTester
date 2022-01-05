package com.opencvtester.baseClasses;

public abstract class Command
{
	protected Id id;
    protected Boolean isBypass;
    
	public Command() {    
		this.id = new Id ();   
		this.id.initNULL();
    } 
	
    public Command(Id id) {	
    	this.id = new Id();
    	this.id.set(id);

	    isBypass = false;
    } 


    public Id getId(){
    	return id;
    }
    
    public void setId(Id id) {
    	this.id=id;
    }
    
    public void setLayerIndex(int layerIndex) {
    	id.setLayerId(layerIndex);
    }
    public void setFilterIndex(int filterIndex) {
    	id.setFilterId(filterIndex);
    }
    public int getLayerIndex(int layerIndex) {
    	return id.layerIndex();
    }
    public int getFilterIndex(int filterIndex) {
    	return id.filterIndex();
    }


    public void updateId(int groupDeepnessIndex, int newValue) {
    	id.setControlOrLayer(groupDeepnessIndex, newValue);
    }
    
    public Boolean isbypass () {
    	return isBypass;
    }
    
    public void setBypass(Boolean bypassState) {
    	isBypass=bypassState;
    }   
}