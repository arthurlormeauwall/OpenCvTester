package guiManager;

import java.io.IOException;


import baseClasses.Id;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.frame.CvFrame;
import baseClasses.frame.Frame;
import baseClasses.frame.FrameFactory;
import filtersDataBase.FiltersDataBase;
import gui.MainWindow;
import renderingEngine.ChainOfLayers;

public class App
{
	private ActionHistoryManager guiManager;
	private MainWindow mainWindow;
	private Frame source;
	private Frame background;
	private Frame dest;
	private FrameFactory frameFactory;

	
	public App () throws IOException{
		frameFactory = new FrameFactory();
	}
	
	public App (Library libraryOption) throws IOException{		
		frameFactory = new FrameFactory();
		if (libraryOption == Library.OPENCV) {
			initOpenCv();
		}
	}
	
	public void initialize(String fileName) throws IOException {
		guiManager= new ActionHistoryManager(chainOfLayersInitializer(fileName), mainWindow);
		mainWindow = new MainWindow(guiManager);
		guiManager.setGui(mainWindow);
	}
	
	public void addFrameType(String name, Frame frameType) {
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
		addFrameType("OpenCv", new CvFrame());
		setFrameType("OpenCv");
	}	
}
