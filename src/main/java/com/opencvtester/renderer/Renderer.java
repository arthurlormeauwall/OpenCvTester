package com.opencvtester.renderer;

import java.util.Stack;

import com.opencvtester.data.Command;
import com.opencvtester.data.CompositeFilter;
import com.opencvtester.renderer.interfaces.FrameInterface;

public abstract class Renderer {
	
	protected Stack<FrameInterface> frames;
	protected Stack<IOFrame> chainOfFilters;
	protected CompositeFilter compositeFilters;

	/*
	 * CONSTRUCTOR & INITS
	 */
	public Renderer(CompositeFilter compositeFilters) {
		this.compositeFilters=compositeFilters;	
		chainOfFilters=new Stack<IOFrame>();
		frames= new Stack<FrameInterface>();
	}
	
	public abstract void execute(Stack<Command> chainOfFilters);
	public abstract Command getLastFilter();
	public abstract int getNumberOfFiltersPlusOpacity();

	/*
	 * FEATURES
	 */
	protected void updateNumberOfFrames() {

		int numberOfFilters = getNumberOfFiltersPlusOpacity();
		int numberOfFrames = frames.size();
		int lastFrame = frames.size() - 1;

		if (numberOfFrames < numberOfFilters - 1) {
			for (int i = numberOfFrames; i < numberOfFilters - 1; i++)
			{
				frames.push(new Frame());
				if (i==0) {		
					compositeFilters.getFrameOut().copyTo(frames.get(i));
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
		
		int numberOfFilters = getNumberOfFiltersPlusOpacity();
		updateNumberOfFrames();

		int lastFrameIndex = frames.size() - 1;

		if (numberOfFilters>0) {
			Command lastFilter =  getLastFilter();


			if (numberOfFilters == 1) {
				((IOFrame)lastFilter).setFrameIn(compositeFilters.getFrameIn());
				((IOFrame)lastFilter).setFrameOut(compositeFilters.getFrameOut());
			}
			else if (numberOfFilters >= 2) {

				((IOFrame)chainOfFilters.get(0)).setFrameIn(compositeFilters.getFrameIn());
				((IOFrame)chainOfFilters.get(0)).setFrameOut(frames.get(0));

				for (int j = 1; j < numberOfFilters - 1; j++) {
					((IOFrame)chainOfFilters.get(j)).setFrameIn(frames.get(j - 1));
					((IOFrame)chainOfFilters.get(j)).setFrameOut(frames.get(j));
				}
				((IOFrame)lastFilter).setFrameIn(frames.get(lastFrameIndex));
				((IOFrame)lastFilter).setFrameOut(compositeFilters.getFrameOut());
			}
			compositeFilters.setFrameOut(((IOFrame)lastFilter).getFrameOut());
		}
		else {
			compositeFilters.getFrameIn().copyTo(compositeFilters.getFrameOut());
		}
	}
	
	public void render() {
		Boolean checkIfActivate=true;
		
		int numberOfFilters = chainOfFilters.size();
		
		for (int i =0; i < numberOfFilters; i++) {
			if (checkIfActivate) {
				if (chainOfFilters.get(i).isActivate())
				{
					((IOFrame)chainOfFilters.get(i)).execute();	
					chainOfFilters.get(i).desactivate();
					checkIfActivate=false;
				}
			}
			else {
				((IOFrame)chainOfFilters.get(i)).execute();	
			}		
		}	
	}
	
	protected void setChain(Stack<Command> chainOfFilters) {
		this.chainOfFilters.clear();
		for (int i=0; i<chainOfFilters.size();i++) {
			this.chainOfFilters.push((IOFrame)chainOfFilters.get(i));
		}		
	}

	public void deleteAllIntermediateFrames() {
		frames.clear();	
	}
}
