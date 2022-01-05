package com.opencvtester.app;

import java.io.IOException;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.baseClasses.frame.Frame;
import com.opencvtester.baseClasses.frame.FrameInterface;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.gui.MainWindow;
import com.opencvtester.guiManager.GuiManager;
import com.opencvtester.renderingEngine.ChainOfLayers;

public class App
{
	private GuiManager guiManager;
	private MainWindow mainWindow;
	private FrameInterface source;
	private FrameInterface background;
	private FrameInterface dest;
	
	public App (String fileName) throws IOException{
		// Init open cv library
		nu.pattern.OpenCV.loadLocally();
		guiManager= new GuiManager(chainOfLayersInitializer(fileName));
		mainWindow = new MainWindow(guiManager);
		guiManager.setGui(mainWindow);
	}

	public void addFilterInDataBase(String name, FilterControlledByFloat filter) {
		guiManager.addFilterInDatabase(name, filter);
	}
	
	public ChainOfLayers chainOfLayersInitializer(String fileName) throws IOException {	
		background = new Frame();
		dest =  new Frame();
		source =  new Frame();	
		
		source.readFromFile(fileName);
		background.createPlainGrayFrame(source.getSpecs().rows, source.getSpecs().cols, 0);
		source.copyTo(dest);
		
		Id chainOfLayersId = new Id();
		chainOfLayersId.initNULL();
		
		ChainOfLayers chainOfLayers = new ChainOfLayers(new FiltersDataBase(), background, chainOfLayersId);
		chainOfLayers.setSource(source);
		chainOfLayers.setDest(dest);
		
		return chainOfLayers;	
	}
}
