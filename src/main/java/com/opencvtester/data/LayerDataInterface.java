package com.opencvtester.data;

public interface LayerDataInterface extends IndexProvider {

	void setOpacity(Float opacity);
	
	Float getOpacity();

	int getNumberOfFilters();
	
	void setNumberOfFilters(int number);
}
