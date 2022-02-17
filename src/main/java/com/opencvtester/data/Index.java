package com.opencvtester.data;

import com.opencvtester.data.interfaces.IndexInterface;

public class Index implements IndexInterface{
	
	protected int filterIndex;
	protected int layerIndex;	
	protected Boolean isActivate;
	
	public int filterIndex() {
		return filterIndex;
	}
	
	public void setFilterIndex(int filterIndex) {
		this.filterIndex = filterIndex;
	}
	
	public int layerIndex() {
		return layerIndex;
	}
	
	public void setLayerIndex(int layerIndex) {
		this.layerIndex = layerIndex;
	}
	
	public void setIndex(String indexType, int index) {
		
	}

	public int getIndex(String indexType) {
		
		return 0;
	}
	
	
	
	public Boolean isActivate() {
		return isActivate;
	}
	
	public void activate() {
		isActivate=true;
	}
	
	public void desactivate() {
		isActivate=false;
	}
	
	
}
