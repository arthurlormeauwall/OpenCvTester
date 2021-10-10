package renderingEngine;

import baseClasses.Layer;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;

import java.util.Stack;

import baseClasses.Control;
import baseClasses.Id;
import baseClasses.IoFrame;

public abstract class FrameLayer  extends Layer implements IoFrame
{


	public FrameLayer(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id>  renderAtIdHistory) {
		super(id, undoIdHistory, renderAtIdHistory);
		m_frames = new Stack<Frame>();
		m_source = new Frame ();
		m_dest   = new Frame ();
	}
	
	public abstract Control getLastControl();
	public abstract int getNumberOfControl();
	
	public void updateNumberOfFrames() {

		int numberOfControls = getNumberOfControl();
		int numberOfFrames = m_frames.size();
		int lastFrame = m_frames.size() - 1;

		if (numberOfControls >= 1) {
			if (numberOfFrames < numberOfControls - 1) {
				for (int i = numberOfFrames; i < numberOfControls - 1; i++)
				{
					m_frames.push(new Frame());
					m_source.copyTo(m_frames.get(i));
				}
			}
			if (numberOfFrames > numberOfControls - 1) {
				for (int i = lastFrame; i >= numberOfControls - 1; i--)
				{
					m_frames.pop();
				}
			}
		}
	}
	
	public void dealFrames() {
		
		int numberOfControls = getNumberOfControl();
		updateNumberOfFrames();

		int lastFrameIndex = m_frames.size() - 1;

		if (numberOfControls>0) {
			Control lastControl =  getLastControl();

			if (numberOfControls == 1) {
				((IoFrame)lastControl).setSource(m_source);
				((IoFrame)lastControl).setDest(m_dest);
			}
			else if (numberOfControls >= 2) {
				((IoFrame)m_chainOfControls.getControl(0)).setSource(m_source);
				((IoFrame)m_chainOfControls.getControl(0)).setDest(m_frames.get(0));

				for (int j = 1; j < numberOfControls - 1; j++) {
					((IoFrame)m_chainOfControls.getControl(j)).setSource(m_frames.get(j - 1));
					((IoFrame)m_chainOfControls.getControl(j)).setDest(m_frames.get(j));
				}
				((IoFrame)lastControl).setSource(m_frames.get(lastFrameIndex));
				((IoFrame)lastControl).setDest(m_dest);
			}
		}
		else {
			m_dest.setFrame(m_source.getFrame());
		}
	}
	
	public void render() {
		int size = m_chainOfControls.getSize();
		int firstControl = m_chainOfControls.getControlIndex(m_renderAtIdHistory);

		for (int i = firstControl; i < size; i++) {
			m_chainOfControls.getControl(i).compute();
		}	
	}

	public void setSource(Frame s){
		  m_source=s;
	}
	
	public void setDest(Frame d)  {
		  m_dest=d;
	}
	
	public Frame getSource()      {
		  return m_source;
	}
	
	public Frame getDest()        {
		  return m_dest;
	}
	
	protected Stack<Frame> m_frames;
	protected Frame m_source;
	protected Frame m_dest;
};
