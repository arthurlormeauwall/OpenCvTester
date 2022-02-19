package com.opencvtester.data.interfaces;

import com.opencvtester.controller.interfaces.IndexProvider;

public interface LayerDataInterface extends IndexProvider {

	void setOpacity(Float opacity);
	
	Float getOpacity();

	int getNumberOfFilters();
	
	void setNumberOfFilters(int number);
}
