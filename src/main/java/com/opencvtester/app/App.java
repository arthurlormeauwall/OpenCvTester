package com.opencvtester.app;

import java.io.IOException;

import com.opencvtester.renderer.ControlledFilter;
import com.opencvtester.renderer.FiltersDataBase;

public class App
{
	private String fileName;
	private FiltersDataBase filtersDataBase;
	
	public App (String fileName) throws IOException{
		// Init open cv library
		nu.pattern.OpenCV.loadLocally();
		this.fileName=fileName;
		filtersDataBase = new FiltersDataBase();
	}	

	public void addFilterInDataBase(ControlledFilter filter) {
		filtersDataBase.addFilter(filter.getFilterName(), filter);
	}
	
	public void launch() {
		try {
			new MainController(fileName, filtersDataBase);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
