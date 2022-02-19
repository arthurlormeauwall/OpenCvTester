package com.opencvtester.data;

import java.io.Serializable;

import com.opencvtester.data.interfaces.IndexInterface;

public class Index implements IndexInterface, Serializable{
	
	private static final long serialVersionUID = -4386016043426852508L;
	protected int filterIndex;
	protected int layerIndex;	
	protected Boolean isActivate;
	
	public Index(int layerIndex, int filterIndex) {
		this.layerIndex=layerIndex;
		this.filterIndex=filterIndex;
		isActivate = true;
	}
	
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
