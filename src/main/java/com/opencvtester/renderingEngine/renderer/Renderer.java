package com.opencvtester.renderingEngine.renderer;

import java.util.Stack;

import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.frame.Frame;
import com.opencvtester.baseClasses.frame.FrameInterface;
import com.opencvtester.renderingEngine.CompositeFilter;

public abstract class Renderer {
	
	protected Stack<FrameInterface> frames;
	protected Stack<Filter> chainOfFilters;
	protected CompositeFilter compositeFilters;

	/*
	 * CONSTRUCTOR & INITS
	 */
	public Renderer(CompositeFilter compositeFilters) {
		this.compositeFilters=compositeFilters;	
		chainOfFilters=new Stack<Filter>();
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
				((Filter)lastFilter).setFrameIn(compositeFilters.getFrameIn());
				((Filter)lastFilter).setFrameOut(compositeFilters.getFrameOut());
			}
			else if (numberOfFilters >= 2) {

				((Filter)chainOfFilters.get(0)).setFrameIn(compositeFilters.getFrameIn());
				((Filter)chainOfFilters.get(0)).setFrameOut(frames.get(0));

				for (int j = 1; j < numberOfFilters - 1; j++) {
					((Filter)chainOfFilters.get(j)).setFrameIn(frames.get(j - 1));
					((Filter)chainOfFilters.get(j)).setFrameOut(frames.get(j));
				}
				((Filter)lastFilter).setFrameIn(frames.get(lastFrameIndex));
				((Filter)lastFilter).setFrameOut(compositeFilters.getFrameOut());
			}
			compositeFilters.setFrameOut(((Filter)lastFilter).getFrameOut());
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
					((Filter)chainOfFilters.get(i)).execute();	
					chainOfFilters.get(i).desactivate();
					checkIfActivate=false;
				}
			}
			else {
				((Filter)chainOfFilters.get(i)).execute();	
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
