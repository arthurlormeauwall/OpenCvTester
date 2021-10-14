package application;

import renderingEngine.Renderer;

import baseClasses.Id;
import baseClasses.enums_structs.GroupsId;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;
import gui.UIImp;

public class App 
{
	public App() {

		m_undoIdHistory = new UndoHistory<Id>();
		m_renderAtIdHistory = new UndoHistory<Id>();
		m_background = new Frame();
		m_dest = new Frame();
		m_source = new Frame();	
	}
	
	public void init(String fileName) {
		
		setImage(fileName);
		m_background.Create1DFrame(m_source.getFrame().rows(), m_source.getFrame().cols(), 0);
		m_source.copyTo(m_dest);

		Id rendererId = new Id();
		rendererId.initNULL();
		rendererId.setGroupId(GroupsId.RENDERER.ordinal());
	
		m_renderer = new Renderer(m_background, rendererId, m_undoIdHistory, m_renderAtIdHistory);

		m_renderer.setSource(m_source);
		m_renderer.setDest(m_dest);
		m_mainWin= new UIImp(m_renderer);	
	}
	
	public void setImage(String fileName) {
		m_source.readFromFile(fileName);
	}
	
	public UIImp getMainWin() {
		return m_mainWin;
	}
	
	protected UndoHistory<Id> m_undoIdHistory;
	protected UndoHistory<Id> m_renderAtIdHistory;
	protected Frame m_source;
	protected Frame m_background;
	protected Frame m_dest;
	
	protected Renderer m_renderer;
	protected UIImp m_mainWin;
};
