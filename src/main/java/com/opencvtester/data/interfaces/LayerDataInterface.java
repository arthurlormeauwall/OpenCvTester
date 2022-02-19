package com.opencvtester.data.interfaces;

import com.opencvtester.controller.interfaces.DataIndexProvider;

public interface LayerDataInterface extends DataIndexProvider {

	void setOpacity(Float opacity);
	
	Float getOpacity();

	int getNumberOfFilters();
	
	void setNumberOfFilters(int number);
}
