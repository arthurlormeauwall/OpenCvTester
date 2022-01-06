package com.opencvtester.baseClasses;

public class Command
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
    	id.setLayerIndex(layerIndex);
    }
    
    public void setFilterIndex(int filterIndex) {
    	id.setFilterIndex(filterIndex);
    }
    
    public int getLayerIndex() {
    	return id.layerIndex();
    }
    
    public int getFilterIndex() {
    	return id.filterIndex();
    }
    
    public int getFilterOrLayerIndex(String key) {
    	return id.get(key);
    }

    public void updateId(String groupDeepnessIndex, int newValue) {
    	id.setFilterOrLayer(groupDeepnessIndex, newValue);
    }
    
    public Boolean isbypass () {
    	return isBypass;
    }
    
    public void setBypass(Boolean bypassState) {
    	isBypass=bypassState;
    }   
}