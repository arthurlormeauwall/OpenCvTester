package guiManager;

import java.io.IOException;

import org.opencv.core.Core;

import baseClasses.Id;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.frame.FrameCv;
import filtersDataBase.FiltersDataBase;
import gui.MainWindow;
import renderingEngine.ChainOfLayers;

public class App
{
	private ActionHistoryManager guiManager;
	private MainWindow mainWindow;
	protected FrameCv source;
	protected FrameCv background;
	protected FrameCv dest;
	
	public App(String fileName) throws IOException{
		openCvInit();
		guiManager= new ActionHistoryManager(chainOfLayersInitializer(fileName), mainWindow);
		mainWindow = new MainWindow(guiManager);
		guiManager.setGui(mainWindow);
	}
	
	private void openCvInit() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);			
	}

	public void addFilterInDataBase(String name, FilterControlledByFloat filter) {
		guiManager.addFilterInDatabase(name, filter);
	}
	
	public ChainOfLayers chainOfLayersInitializer(String fileName) throws IOException {	
		background = new FrameCv();
		dest = new FrameCv();
		source = new FrameCv();	
		
		source.readFromFile(fileName);
		background.createPlainGrayFrame(source.getMat().rows(), source.getMat().cols(), 0);
		source.copyTo(dest);
		
		Id chainOfLayersId = new Id();
		chainOfLayersId.initNULL();
		
		ChainOfLayers chainOfLayers = new ChainOfLayers(new FiltersDataBase(), background, chainOfLayersId);
		chainOfLayers.setSource(source);
		chainOfLayers.setDest(dest);
		
		return chainOfLayers;	
	}
}
