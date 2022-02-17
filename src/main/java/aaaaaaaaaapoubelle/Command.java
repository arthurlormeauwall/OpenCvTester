package aaaaaaaaaapoubelle;

public class Command 
{
    protected Boolean isBypass;
    protected Boolean isBypassLocked;
    
    protected Boolean isActivate;
    
	private int layerIndex;
	private int filterIndex;
    
	/*
	 * CONSTRUCTOR & INITS
	 */
	public Command() {    
		isBypassLocked=false;
		isActivate = true;
    } 
	
	public Command(int layerIndex, int filterIndex) {
		isActivate = true;
		isBypassLocked=false;
		setId(layerIndex, filterIndex);
	}

	/*
	 * GETTERS & SETTERS
	 */
	public void setId(int layerIndex, int filterIndex) {
		this.layerIndex=layerIndex;
		this.filterIndex=filterIndex;
	}
    
    public void setLayerIndex(int layerIndex) {
    	this.layerIndex=layerIndex;
    }
    
    public void setFilterIndex(int filterIndex) {
    	this.filterIndex=filterIndex;
    }
    
    public int layerIndex() {
    	return layerIndex;
    }
    
    public int filterIndex() {
    	return filterIndex;
    }
    
    public int getIndex(String indexType) {
    	if (indexType=="layer") {
    		return layerIndex;
    	}
    	else {
    		return filterIndex;
    	}
    }

    public void setIndex(String indexType, int newValue) {
    	if (indexType=="layer") {
    		layerIndex=newValue;
    	}
    	else {
    		filterIndex=newValue;
    	}
    }
    
    public Boolean isbypassLocked () {
    	return isBypassLocked;
    }
    
    public Boolean isbypass() {
    	return isBypass;
    }
    
    public void setBypass(Boolean bypassState) {
    	isBypass=bypassState;
        isBypassLocked=bypassState;    
    }   
    
    public void activate() {	
    	isActivate=true;
    }
    
    public void desactivate() {	
    	isActivate=false;
    }
    
    public Boolean isActivate() {
    	
    	return isActivate;
    }
}