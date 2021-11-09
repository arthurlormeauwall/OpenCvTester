package gui;

import java.io.IOException;

import baseClasses.filter.FilterControlledByFloat;

public class App
{
	private OpenCvInit OpenCvInit;
	private GuiManager guiManager;
	private Gui mainWindow;
	
	public App(String fileName) throws IOException{
		OpenCvInit= new OpenCvInit();
		guiManager= new GuiManager(OpenCvInit.init(fileName), this);
		
	}
	
	public void addFilterInDataBase(String name, FilterControlledByFloat filter) {
		guiManager.addFilterInDatabase(name, filter);
	}
	
	public void execute() {
		Gui mainWindow = new Gui(guiManager);
	}
	
	public Gui getGui() {
		return mainWindow;
	}
}
