package com.opencvtester.app;

import java.io.IOException;

import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.guiManager.GuiManager;

public class App
{
	private GuiManager guiManager;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public App (String fileName) throws IOException{
		// Init open cv library
		nu.pattern.OpenCV.loadLocally();
		
		guiManager= new GuiManager(fileName);
	}	
	
	/*
	 * FEATURES
	 */
	public void addFilterInDataBase(String name, FilterControlledByFloat filter) {
		guiManager.addFilterInDatabase(name, filter);
	}
}
