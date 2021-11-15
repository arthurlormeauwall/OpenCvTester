package guiManager;

import java.io.IOException;

import baseClasses.Id;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.frame.Frame;
import baseClasses.frame.FrameFactory;
import baseClasses.frame.LibraryOption;
import filtersDataBase.FiltersDataBase;
import gui.MainWindow;
import renderingEngine.ChainOfLayers;

public class App
{
	private ActionHistoryManager guiManager;
	private MainWindow mainWindow;
	protected Frame source;
	protected Frame background;
	protected Frame dest;
	private FrameFactory frameFactory;

	
	public App(String fileName, LibraryOption libraryOption) throws IOException{
		frameFactory = new FrameFactory(libraryOption);
		guiManager= new ActionHistoryManager(chainOfLayersInitializer(fileName), mainWindow);
		mainWindow = new MainWindow(guiManager);
		guiManager.setGui(mainWindow);
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
}
