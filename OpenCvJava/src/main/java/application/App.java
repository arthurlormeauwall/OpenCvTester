package application;

import renderingEngine.GroupsId;
import renderingEngine.ChainOfLayers;

import org.opencv.core.Core;

import baseClasses.Id;
import baseClasses.history.imp.UndoIdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;
import gui.UIImp;


public class App 
{
	protected UndoIdHistory<Id> undoIdHistory;
	protected UndoIdHistory<Id> renderAtIdHistory;
	protected Frame source;
	protected Frame background;
	protected Frame dest;
	
	protected ChainOfLayers renderer;
	protected UIImp mainWin;
	
	public App() {	
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		undoIdHistory = new UndoIdHistory<Id>();
		renderAtIdHistory = new UndoIdHistory<Id>();
		background = new Frame();
		dest = new Frame();
		source = new Frame();	
	}
	
	public void init(String fileName) {
		
		setImage(fileName);
		background.createPlainGrayFrame(source.getFrame().rows(), source.getFrame().cols(), 0);
		source.copyTo(dest);
		
		Id rendererId = new Id();
		rendererId.initNULL();
		rendererId.setGroupId(GroupsId.RENDERER.ordinal());
	
		renderer = new ChainOfLayers(new FiltersDataBase(), background, rendererId, undoIdHistory, renderAtIdHistory);

		renderer.setSource(source);
		renderer.setDest(dest);
		renderer.play();
		mainWin= new UIImp(renderer);	
	}
	
	private void setImage(String fileName) {
		source.readFromFile(fileName);
	}
	
	public UIImp getMainWin() {
		return mainWin;
	}
}
