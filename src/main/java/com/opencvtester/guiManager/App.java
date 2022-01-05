package com.opencvtester.guiManager;

import java.io.IOException;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.baseClasses.frame.Frame;
import com.opencvtester.baseClasses.frame.FrameInterface;
import com.opencvtester.baseClasses.frame.FrameFactory;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.gui.MainWindow;
import com.opencvtester.renderingEngine.ChainOfLayers;

public class App
{
	private GuiManager guiManager;
	private MainWindow mainWindow;
	private FrameInterface source;
	private FrameInterface background;
	private FrameInterface dest;
	private FrameFactory frameFactory;
	
	public App () throws IOException{
		nu.pattern.OpenCV.loadLocally();
		frameFactory = new FrameFactory();
		addFrameType("OpenCv", new Frame());
		setFrameType("OpenCv");
	}
	
	public void initialize(String fileName) throws IOException {
		guiManager= new GuiManager(chainOfLayersInitializer(fileName));
		mainWindow = new MainWindow(guiManager);
		guiManager.setGui(mainWindow);
	}
	
	public void addFrameType(String name, FrameInterface frameType) {
		frameFactory.putNewFrameType(name, frameType);
	}
	
	public void setFrameType(String frameType) {
		frameFactory.setFrameType(frameType);	
	}

	public void addFilterInDataBase(String name, FilterControlledByFloat filter) {
		guiManager.addFilterInDatabase(name, filter);
	}
	
	public ChainOfLayers chainOfLayersInitializer(String fileName) throws IOException {	
		background = frameFactory.create();
		dest =  frameFactory.create();
		source =  frameFactory.create();	
		
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
	
	public void initOpenCv() {
		addFrameType("OpenCv", new Frame());
		setFrameType("OpenCv");
	}	
}
