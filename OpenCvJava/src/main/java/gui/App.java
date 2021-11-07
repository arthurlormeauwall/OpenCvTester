package gui;

public class App
{
	private OpenCvInit OpenCvInit;
	protected GuiManager guiManager;
	Gui mainWindow;
	
	public App(String fileName) {
		OpenCvInit= new OpenCvInit();
		guiManager= new GuiManager(OpenCvInit.init(fileName), this);
		Gui mainWindow = new Gui(guiManager);
	}
}
