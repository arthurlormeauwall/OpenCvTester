package renderingEngine.renderer;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Executable;
import baseClasses.filter.Filter;
import baseClasses.frame.Frame;
import baseClasses.frame.FrameFactory;
import renderingEngine.CompositeFilter;

public abstract class Renderer {
	
	protected Stack<Frame> frames;
	protected Stack<Filter> chainOfFilters;
	protected CompositeFilter compositeFilters;
	private FrameFactory frameFactory;
	
	public Renderer(CompositeFilter compositeFilters) {
		this.compositeFilters=compositeFilters;	
		chainOfFilters=new Stack<Filter>();
		frames= new Stack<Frame>();
		frameFactory=new FrameFactory();
	}
	
	public abstract void execute(Stack<Command> chainOfFilters);
	public abstract Command getLastFilter();
	public abstract int getNumberOfFilters();
	
	protected void updateNumberOfFrames() {

		int numberOfFilters = getNumberOfFilters();
		int numberOfFrames = frames.size();
		int lastFrame = frames.size() - 1;

		if (numberOfFrames < numberOfFilters - 1) {
			for (int i = numberOfFrames; i < numberOfFilters - 1; i++)
			{
				frames.push(frameFactory.create());
				if (i==0) {		
					compositeFilters.getDest().copyTo(frames.get(i));
				}
				else {
					frames.get(i-1).copyTo(frames.get(i));
				}	
			}
		}
		else if (numberOfFrames > numberOfFilters - 1) {
			
				for (int i = lastFrame; i >= numberOfFilters-1; i--)
				{
					if(!frames.empty()) {
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
				((Filter)lastControl).setSource(compositeFilters.getSource());
				((Filter)lastControl).setDest(compositeFilters.getDest());
			}
			else if (numberOfControls >= 2) {

				((Filter)chainOfFilters.get(0)).setSource(compositeFilters.getSource());
				((Filter)chainOfFilters.get(0)).setDest(frames.get(0));

				for (int j = 1; j < numberOfControls - 1; j++) {
					((Filter)chainOfFilters.get(j)).setSource(frames.get(j - 1));
					((Filter)chainOfFilters.get(j)).setDest(frames.get(j));
				}
				((Filter)lastControl).setSource(frames.get(lastFrameIndex));
			}
		}
		else {
			compositeFilters.getSource().copyTo(compositeFilters.getDest());
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
