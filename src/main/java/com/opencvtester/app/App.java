package com.opencvtester.app;

import java.io.IOException;

import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.guiManager.GuiManager;

public class App
{
	private String fileName;
	private FiltersDataBase filtersDataBase;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public App (String fileName) throws IOException{
		// Init open cv library
		nu.pattern.OpenCV.loadLocally();
		this.fileName=fileName;
		filtersDataBase = new FiltersDataBase();
	}	
	
	/*
	 * FEATURES
	 */
	public void addFilterInDataBase(FilterControlledByFloat filter) {
		filtersDataBase.addFilter(filter.getFilterName(), filter);
	}
	
	public void launch() {
		try {
			new GuiManager(fileName, filtersDataBase);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
