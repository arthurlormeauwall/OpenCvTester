package com.opencvtester.renderer;

import java.util.Stack;

import com.opencvtester.renderer.entity.ControlledFilter;
import com.opencvtester.renderer.interfaces.FrameInterface;
import com.opencvtester.renderer.interfaces.IOFrame;

public abstract class Renderer {
	
	protected Stack<FrameInterface> frames;
	protected Stack<IOFrame> chainOfIOFrame;
	protected IOFrame mainFilter;

	/*
	 * CONSTRUCTOR & INITS
	 */
	public Renderer(IOFrame mainFilter) {
		this.mainFilter=mainFilter;	
		chainOfIOFrame=new Stack<IOFrame>();
		frames= new Stack<FrameInterface>();
	}
	
	public abstract void execute(Stack<IOFrame> chainOfFilters);
	public abstract IOFrame getLastFilter();
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
					mainFilter.getFrameOut().copyTo(frames.get(i));
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
	
	public void dealFrames(Stack<IOFrame> chainOfFilters) {
		setChain(chainOfFilters);
		
		int numberOfFilters = getNumberOfFiltersPlusOpacity();
		updateNumberOfFrames();

		int lastFrameIndex = frames.size() - 1;

		if (numberOfFilters>0) {
			IOFrame lastFilter =  getLastFilter();


			if (numberOfFilters == 1) {
				lastFilter.setFrameIn(mainFilter.getFrameIn());
				lastFilter.setFrameOut(mainFilter.getFrameOut());
			}
			else if (numberOfFilters >= 2) {

				chainOfFilters.get(0).setFrameIn(mainFilter.getFrameIn());
				chainOfFilters.get(0).setFrameOut(frames.get(0));

				for (int j = 1; j < numberOfFilters - 1; j++) {
					chainOfFilters.get(j).setFrameIn(frames.get(j - 1));
					chainOfFilters.get(j).setFrameOut(frames.get(j));
				}
				lastFilter.setFrameIn(frames.get(lastFrameIndex));
				lastFilter.setFrameOut(mainFilter.getFrameOut());
			}
			mainFilter.setFrameOut(((IOFrame)lastFilter).getFrameOut());
		}
		else {
			mainFilter.getFrameIn().copyTo(mainFilter.getFrameOut());
		}
	}
	
	public void render() {
		Boolean checkIfActivate=true;
		
		int numberOfFilters = chainOfIOFrame.size();
		
		for (int i =0; i < numberOfFilters; i++) {
			if (checkIfActivate) {
				if (chainOfIOFrame.get(i).isActivate())
				{
					((ControlledFilter)chainOfIOFrame.get(i)).execute();	
					chainOfIOFrame.get(i).desactivate();
					checkIfActivate=false;
				}
			}
			else {
				((ControlledFilter)chainOfIOFrame.get(i)).execute();	
			}		
		}	
	}
	
	protected void setChain(Stack<IOFrame> chainOfFilters) {
		this.chainOfIOFrame.clear();
		for (int i=0; i<chainOfFilters.size();i++) {
			this.chainOfIOFrame.push((IOFrame)chainOfFilters.get(i));
		}		
	}

	public void deleteAllIntermediateFrames() {
		frames.clear();	
	}
}
