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
	
	protected Stack<Frame> frames;
	protected Frame source;
	protected Frame dest;
	
	public FrameLayer(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id>  renderAtIdHistory) {
		super(id, undoIdHistory, renderAtIdHistory);
		frames = new Stack<Frame>();
		source = new Frame ();
		dest   = new Frame ();
	}
	
	public abstract Control getLastControl();
	public abstract int getNumberOfControl();
	
	public void updateNumberOfFrames() {

		int numberOfControls = getNumberOfControl();
		int numberOfFrames = frames.size();
		int lastFrame = frames.size() - 1;

		if (numberOfControls >= 1) {
			if (numberOfFrames < numberOfControls - 1) {
				for (int i = numberOfFrames; i < numberOfControls - 1; i++)
				{
					frames.push(new Frame());
					source.copyTo(frames.get(i));
				}
			}
			if (numberOfFrames > numberOfControls - 1) {
				for (int i = lastFrame; i >= numberOfControls - 1; i--)
				{
					frames.pop();
				}
			}
		}
	}
	
	public void dealFrames() {
		
		int numberOfControls = getNumberOfControl();
		updateNumberOfFrames();

		int lastFrameIndex = frames.size() - 1;

		if (numberOfControls>0) {
			Control lastControl =  getLastControl();

			if (numberOfControls == 1) {
				((IoFrame)lastControl).setSource(source);
				((IoFrame)lastControl).setDest(dest);
			}
			else if (numberOfControls >= 2) {
				((IoFrame)chainOfControls.getControl(0)).setSource(source);
				((IoFrame)chainOfControls.getControl(0)).setDest(frames.get(0));

				for (int j = 1; j < numberOfControls - 1; j++) {
					((IoFrame)chainOfControls.getControl(j)).setSource(frames.get(j - 1));
					((IoFrame)chainOfControls.getControl(j)).setDest(frames.get(j));
				}
				((IoFrame)lastControl).setSource(frames.get(lastFrameIndex));
				((IoFrame)lastControl).setDest(dest);
			}
		}
		else {
			dest.setFrame(source.getFrame());
		}
	}
	
	public void render() {
		int size = chainOfControls.getSize();
		int firstControl = chainOfControls.getControlIndex(renderAtIdHistory);

		for (int i = firstControl; i < size; i++) {
			chainOfControls.getControl(i).compute();
		}	
	}

	public void setSource(Frame s){
		  source=s;
	}
	
	public void setDest(Frame d)  {
		  dest=d;
	}
	
	public Frame getSource()      {
		  return source;
	}
	
	public Frame getDest()        {
		  return dest;
	}
};
