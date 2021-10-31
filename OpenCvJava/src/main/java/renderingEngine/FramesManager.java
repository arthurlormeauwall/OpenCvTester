package renderingEngine;

import baseClasses.history.imp.UndoIdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.IoFrame;
import baseClasses.chain.ChainOfCommandsManager;

public abstract class FramesManager  extends ChainOfCommandsManager implements IoFrame
{
	protected Stack<Frame> frames;
	protected Frame source;
	protected Frame dest;
	protected FiltersDataBase dbControls;
	
	public FramesManager(FiltersDataBase dbControls, Id id, UndoIdHistory<Id> undoIdHistory, UndoIdHistory<Id>  renderAtIdHistory) {
		super(id, undoIdHistory, renderAtIdHistory);
		frames = new Stack<Frame>();
		source = new Frame ();
		dest   = new Frame ();
		this.dbControls = dbControls;
	}
	
	public abstract Command getLastControl();
	public abstract int getNumberOfControl();
	
	protected void updateNumberOfFrames() {

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
			else if (numberOfFrames > numberOfControls - 1) {
				for (int i = lastFrame; i >= numberOfControls - 1; i--)
				{
					frames.pop();
				}
			}
		}
	}
	
	protected void dealFrames() {
		
		int numberOfControls = getNumberOfControl();
		updateNumberOfFrames();

		int lastFrameIndex = frames.size() - 1;

		if (numberOfControls>0) {
			Command lastControl =  getLastControl();

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
	
	public void setDest(Frame d){
		  dest=d;
	}
	
	public Frame getSource(){
		  return source;
	}
	
	public Frame getDest(){
		  return dest;
	}
}