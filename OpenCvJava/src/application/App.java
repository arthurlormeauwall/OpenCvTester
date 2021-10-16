package application;

import renderingEngine.GroupsId;
import renderingEngine.Renderer;

import org.opencv.core.Core;

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
	
	protected Renderer m_renderer;
	protected UIImp m_mainWin;
	
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
		background.Create1DFrame(source.getFrame().rows(), source.getFrame().cols(), 0);
		source.copyTo(dest);

		Id rendererId = new Id();
		rendererId.initNULL();
		rendererId.setGroupId(GroupsId.RENDERER.ordinal());
	
		m_renderer = new Renderer(background, rendererId, undoIdHistory, renderAtIdHistory);

		m_renderer.setSource(source);
		m_renderer.setDest(dest);
		m_mainWin= new UIImp(m_renderer);	
	}
	
	public void setImage(String fileName) {
		source.readFromFile(fileName);
	}
	
	public UIImp getMainWin() {
		return m_mainWin;
	}
};
