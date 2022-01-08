package com.opencvtester.baseClasses;

public class Command
{
	protected Id id;
    protected Boolean isBypass;
    protected Boolean isBypassLocked;
    
    
	/*
	 * CONSTRUCTOR & INITS
	 */
	public Command() {    
		this.id = new Id ();   
		this.id.initNULL();
		isBypassLocked=false;
    } 
	
    public Command(Id id) {	
    	this.id = new Id();
    	this.id.set(id);
	    isBypass = false;
	    isBypassLocked=false;
    } 
	
	/*
	 * GETTERS & SETTERS
	 */
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
    
    public int getIndex(String key) {
    	return id.get(key);
    }

    public void setIndex(String indexType, int newValue) {
    	id.setFilterOrLayer(indexType, newValue);
    }
    
    public Boolean isbypass () {
    	return isBypassLocked;
    }
    
    public void setBypass(Boolean bypassState) {
    	isBypass=bypassState;
        isBypassLocked=bypassState;    
    }   
}