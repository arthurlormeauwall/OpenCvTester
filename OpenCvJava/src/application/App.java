package application;

import renderingEngine.GroupsId;
import renderingEngine.Renderer;


import org.opencv.core.Core;

import algorithmsDataBase.DbControls;
import baseClasses.Id;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;
import gui.UIImp;

public class App 
{
	protected UndoHistory<Id> undoIdHistory;
	protected UndoHistory<Id> renderAtIdHistory;
	protected Frame source;
	protected Frame background;
	protected Frame dest;
	
	protected Renderer renderer;
	protected UIImp mainWin;
	
	public App() {
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		undoIdHistory = new UndoHistory<Id>();
		renderAtIdHistory = new UndoHistory<Id>();
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
	
		renderer = new Renderer(new DbControls(), background, rendererId, undoIdHistory, renderAtIdHistory);

		renderer.setSource(source);
		renderer.setDest(dest);
		mainWin= new UIImp(renderer);	
	}
	
	private void setImage(String fileName) {
		source.readFromFile(fileName);
	}
	
	public UIImp getMainWin() {
		return mainWin;
	}
}
