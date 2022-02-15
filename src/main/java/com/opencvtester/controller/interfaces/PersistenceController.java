package com.opencvtester.controller.interfaces;

import com.opencvtester.controller.MainController;

public interface PersistenceController {
	void saveSession();
	
	void saveSessionAs(String fileName);

	void openSession(String fileName, MainController mainController);
}
