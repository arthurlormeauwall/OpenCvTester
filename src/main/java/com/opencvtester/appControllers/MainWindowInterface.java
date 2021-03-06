package com.opencvtester.appControllers;

public interface MainWindowInterface {
	
	void clearAll();

	void launchSaveSessionAs();
	
	void addFilter(int layerIndex, int filterIndex);
	void addLayer(int layerIndex);
	void deleteFilter(int layerIndex, int filterIndex);
	void deleteLayer(int layerIndex);
	void updateFilter(int layerIndex, int filterIndex);
}
