package guiManager;

import java.io.IOException;

import baseClasses.filter.FilterControlledByFloat;
import gui.MainWindow;

public class App
{
	private OpenCvInit OpenCvInit;
	private ActionHistoryManager guiManager;
	private MainWindow mainWindow;
	
	public App(String fileName) throws IOException{
		OpenCvInit= new OpenCvInit();
		guiManager= new ActionHistoryManager(OpenCvInit.init(fileName), this);
		mainWindow = new MainWindow(guiManager);
		guiManager.setGui(mainWindow);
	}
	
	public void addFilterInDataBase(String name, FilterControlledByFloat filter) {
		guiManager.addFilterInDatabase(name, filter);
	}
	
	
	public MainWindow getGui() {
		return mainWindow;
	}
}
