package main;

import java.util.Stack;

import renderingEngine.Renderer;

import baseClasses.Id;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;
import gui.UIInterface;

class App 
{
	public App() {

		m_undoIdHistory = new UndoHistory<Id>();
		m_renderAtIdHistory = new UndoHistory<Id>();
		m_background = new Frame();
		m_dest = new Frame();
		m_source = new Frame();
	}
	
	public void test() {
	
		Id idL = new Id();
		Id idC = new Id();
		
		idL.set(0, 0, 2);
		idC.set(0, 0, 4);

		Stack<Id> ids = new Stack<Id>();
		ids.push(idL);
		ids.push(idC);
		
		Stack<Integer> temp= new Stack<Integer>();
		temp.push(1);

		m_renderer.addLayer(ids, temp);
		m_renderer.store();
		m_renderer.setBypass(idC, false);
		m_renderer.play();
		m_renderer.undo();
		m_renderer.undo();
		m_renderer.undo();
		m_renderer.undo();
		m_renderer.undo();
		m_renderer.play();
		m_renderer.redo();
		m_renderer.redo();
		m_renderer.redo();
		m_renderer.redo();
		m_renderer.redo();
		m_renderer.play();
		
		
		
		Stack<Float> tempF=new Stack<Float>();
		tempF.push(0.6f);
		tempF.push(2f);
		tempF.push(0.9f);
		m_renderer.setParameters(idC, tempF);
		m_renderer.store();
		m_renderer.play();
		m_renderer.undo();
		m_renderer.play();
	}




	public void init() {
		
		m_source.readFromFile("assets/20210717_203824.jpg");
		m_background.Create1DFrame(m_source.getFrame().rows(), m_source.getFrame().cols(), 0);
		m_source.copyTo(m_dest);

		Id id = new Id();
		id.initNULL();
		id.setGroupId(0);
	
		m_renderer = new Renderer(m_background, id, m_undoIdHistory, m_renderAtIdHistory);
		//m_mainWin = new UIManagerQt (m_renderer, id, m_undoIdHistory, m_renderAtIdHistory);

		m_renderer.setSource(m_source);
		m_renderer.setDest(m_dest);

		test();
		m_renderer.play();
	}

	


	protected Stack<Id> m_layersId;
	protected Stack<Id> m_controlsId;
	protected Renderer m_renderer;
	protected UndoHistory<Id> m_undoIdHistory;
	protected UndoHistory<Id> m_renderAtIdHistory;
	protected Frame m_source;
	protected Frame m_background;
	protected Frame m_dest;
 
	protected UIInterface m_mainWin;



};
