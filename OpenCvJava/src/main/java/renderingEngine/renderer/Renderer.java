package renderingEngine.renderer;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Executable;
import baseClasses.IoFrame;
import baseClasses.filter.Filter;
import baseClasses.openCvFacade.Frame;
import renderingEngine.CompositeFilters;

public abstract class Renderer {
	
	protected Stack<Frame> frames;
	protected Stack<Filter> chainOfFilters;
	protected CompositeFilters compositeFilters;
	
	public Renderer(CompositeFilters compositeFilters) {
		this.compositeFilters=compositeFilters;	
		chainOfFilters=new Stack<Filter>();
		frames= new Stack<Frame>();
	}
	
	public abstract void execute(Stack<Command> chainOfFilters);
	public abstract Command getLastFilter();
	public abstract int getNumberOfFilters();
	
	protected void updateNumberOfFrames() {

		int numberOfControls = getNumberOfFilters();
		int numberOfFrames = frames.size();
		int lastFrame = frames.size() - 1;

		if (numberOfControls >= 1) {
			if (numberOfFrames < numberOfControls - 1) {
				for (int i = numberOfFrames; i < numberOfControls - 1; i++)
				{
					frames.push(new Frame());
					compositeFilters.getSource().copyTo(frames.get(i));
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
	
	public void dealFrames(Stack<Command> chainOfFilters) {
		
		setChain(chainOfFilters);
		
		int numberOfControls = getNumberOfFilters();
		updateNumberOfFrames();

		int lastFrameIndex = frames.size() - 1;

		if (numberOfControls>0) {
			Command lastControl =  getLastFilter();

			if (numberOfControls == 1) {
				((IoFrame)lastControl).setSource(compositeFilters.getSource());
				((IoFrame)lastControl).setDest(compositeFilters.getDest());
			}
			else if (numberOfControls >= 2) {

				((IoFrame)chainOfFilters.get(0)).setSource(compositeFilters.getSource());
				((IoFrame)chainOfFilters.get(0)).setDest(frames.get(0));

				for (int j = 1; j < numberOfControls - 1; j++) {
					((IoFrame)chainOfFilters.get(j)).setSource(frames.get(j - 1));
					((IoFrame)chainOfFilters.get(j)).setDest(frames.get(j));
				}
				((IoFrame)lastControl).setSource(frames.get(lastFrameIndex));
				((IoFrame)lastControl).setDest(compositeFilters.getDest());
			}
		}
		else {
			compositeFilters.getDest().setMat(compositeFilters.getSource().getMat());
		}
	}
	
	public void render() {
		Boolean checkIfActivate=true;
		
		int size = chainOfFilters.size();
		
		for (int i =0; i < size; i++) {
			if (checkIfActivate) {
				if (chainOfFilters.get(i).isActivate())
				{
					((Executable)chainOfFilters.get(i)).execute();	
					chainOfFilters.get(i).desactivate();
					checkIfActivate=false;
				}
			}
			else {
				((Executable)chainOfFilters.get(i)).execute();	
			}		
		}	
	}
	
	protected void setChain(Stack<Command> chainOfFilters) {
		this.chainOfFilters.clear();
		for (int i=0; i<chainOfFilters.size();i++) {
			this.chainOfFilters.push((Filter)chainOfFilters.get(i));
		}		
	}
}
