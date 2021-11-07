package gui;

public class App
{
	private OpenCvInit OpenCvInit;
	protected GuiManager guiManager;
	Gui mainWindow;
	
	public App(String fileName) {
		OpenCvInit= new OpenCvInit();
		OpenCvInit.init(fileName);
		guiManager= new GuiManager(OpenCvInit.getChainOfLayers(), this);
		Gui mainWindow = new Gui(guiManager);
	}
	
	private void play() {
		OpenCvInit.getChainOfLayers().play();
	}	
}
