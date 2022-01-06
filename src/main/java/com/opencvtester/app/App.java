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
	private FrameInterface frameIn;
	private FrameInterface backgroundFrame;
	private FrameInterface frameOut;
	
	public App (String fileName) throws IOException{
		// Init open cv library
		nu.pattern.OpenCV.loadLocally();
		
		guiManager= new GuiManager(chainOfLayersInitializer(fileName));
		mainWindow = new MainWindow(guiManager);
		guiManager.setMainWindow(mainWindow);
	}

	public void addFilterInDataBase(String name, FilterControlledByFloat filter) {
		guiManager.addFilterInDatabase(name, filter);
	}
	
	public ChainOfLayers chainOfLayersInitializer(String fileName) throws IOException {	
		backgroundFrame = new Frame();
		frameOut =  new Frame();
		frameIn =  new Frame();	
		
		frameIn.readFromFile(fileName);
		backgroundFrame.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 0);
		frameIn.copyTo(frameOut);
		
		Id chainOfLayersId = new Id();
		chainOfLayersId.initNULL();
		
		ChainOfLayers chainOfLayers = new ChainOfLayers(new FiltersDataBase(), backgroundFrame, chainOfLayersId);
		chainOfLayers.setSource(frameIn);
		chainOfLayers.setDest(frameOut);
		
		return chainOfLayers;	
	}
}
