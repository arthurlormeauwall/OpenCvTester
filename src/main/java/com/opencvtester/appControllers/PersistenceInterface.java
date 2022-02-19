package com.opencvtester.appControllers;

import com.opencvtester.app.MainController;

public interface PersistenceInterface {
	void saveSession();
	
	void saveSessionAs(String fileName);

	void openSession(String fileName, MainController mainController);
}
