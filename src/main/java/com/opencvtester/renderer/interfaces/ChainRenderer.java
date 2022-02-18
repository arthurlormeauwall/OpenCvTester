package com.opencvtester.renderer.interfaces;

import java.util.List;
import java.util.Stack;

import com.opencvtester.controller.interfaces.DataProvider;
import com.opencvtester.controller.interfaces.Renderer;
import com.opencvtester.data.interfaces.IndexInterface;
import com.opencvtester.renderer.Frame;

public abstract class ChainRenderer extends Renderer {
	
	protected Stack<FrameInterface> IntermediatesFrames;
	protected List<? extends Renderer> chainOfRenderer;

	/*
	 * CONSTRUCTOR & INITS
	 */
	public ChainRenderer(List<? extends Renderer> chainOfRenderer) {
		this.chainOfRenderer=chainOfRenderer;
		IntermediatesFrames= new Stack<FrameInterface>();
	}
	
	public abstract Renderer getLast();
	public abstract int getNumberOfFiltersPlusOpacity();
	
	protected void updateNumberOfFrames() {
		int numberOfFilters = getNumberOfFiltersPlusOpacity();
		int numberOfFrames = IntermediatesFrames.size();
		int lastFrame = IntermediatesFrames.size() - 1;

		if (numberOfFrames < numberOfFilters - 1) {
			for (int i = numberOfFrames; i < numberOfFilters - 1; i++)
			{
				IntermediatesFrames.push(new Frame());
				if (i==0) {		
					getFrameOut().copyTo(IntermediatesFrames.get(i));
				}
				else {
					IntermediatesFrames.get(i-1).copyTo(IntermediatesFrames.get(i));
				}	
			}
		}
		else if (numberOfFrames > numberOfFilters - 1) {
			
				for (int i = lastFrame; i >= numberOfFilters-1; i--)
				{
					if(!IntermediatesFrames.empty()) {
						IntermediatesFrames.pop();
					}
				}
			}		
		}
	
	public void dealFrames() {
		
		int numberOfFilters = getNumberOfFiltersPlusOpacity();
		updateNumberOfFrames();

		int lastFrameIndex = IntermediatesFrames.size() - 1;

		if (numberOfFilters>0) {
			Renderer lastFilter = this.getLast();

			if (numberOfFilters == 1) {
				lastFilter.setFrameIn(getFrameIn());
				lastFilter.setFrameOut(getFrameOut());
			}
			else if (numberOfFilters >= 2) {

				chainOfRenderer.get(0).setFrameIn(getFrameIn());
				chainOfRenderer.get(0).setFrameOut(IntermediatesFrames.get(0));

				for (int j = 1; j < numberOfFilters - 1; j++) {
					chainOfRenderer.get(j).setFrameIn(IntermediatesFrames.get(j - 1));
					chainOfRenderer.get(j).setFrameOut(IntermediatesFrames.get(j));
				}
				lastFilter.setFrameIn(IntermediatesFrames.get(lastFrameIndex));
				lastFilter.setFrameOut(getFrameOut());
			}
			setFrameOut(((Renderer)lastFilter).getFrameOut());
		}
		else {
			getFrameIn().copyTo(getFrameOut());
		}
	}
	
	public void execute() {
		Boolean checkIfActivate=true;
		
		int numberOfFilters = chainOfRenderer.size();
		
		for (int i =0; i < numberOfFilters; i++) {
			IndexInterface data = ((DataProvider)chainOfRenderer.get(i)).getData();
			
			if (checkIfActivate) {
				if (data.isActivate())
				{
					chainOfRenderer.get(i).render();			
					data.desactivate();
					checkIfActivate=false;
				}
			}
			else {
				chainOfRenderer.get(i).render();	
			}		
		}	
	}

	public void deleteAllIntermediateFrames() {
		IntermediatesFrames.clear();	
	}
}
