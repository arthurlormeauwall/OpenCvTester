package com.opencvtester.data;

public interface IndexInterface {
	public int filterIndex();
	
	public void setFilterIndex(int filterIndex) ;
	
	public int layerIndex() ;
	
	public void setLayerIndex(int layerIndex) ;
	
	public void setIndex(String indexType, int index) ;

	public int getIndex(String indexType);
	
	public Boolean isActivate();
	
	public void activate() ;
	
	public void desactivate() ;
}
