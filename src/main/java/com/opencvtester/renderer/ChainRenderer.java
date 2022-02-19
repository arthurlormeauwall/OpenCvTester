package com.opencvtester.renderer;

import java.util.Stack;

import com.opencvtester.controller.interfaces.Renderer;
import com.opencvtester.data.interfaces.IndexInterface;

public abstract class ChainRenderer extends Renderer {
	
	protected Stack<FrameInterface> IntermediatesFrames;
	protected Stack<? extends RendererWithData> chainOfRenderer;
	protected FrameInterface background;

	public ChainRenderer(Stack<? extends RendererWithData> chainOfRenderer) {
		this.chainOfRenderer=chainOfRenderer;
		this.background = new Frame();
		IntermediatesFrames= new Stack<FrameInterface>();
	}
	
	public abstract Renderer getLast();
	public abstract int getNumberOfFiltersPlusOpacity();
	
	public FrameInterface getBackground() {
		return background;
	}

	public void setBackground(FrameInterface background) {
		this.background.setBufferedImage(background.getBufferedImage());
	}
	
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
			Renderer lastRenderer = this.getLast();

			if (numberOfFilters == 1) {
				lastRenderer.setFrameIn(getFrameIn());
				lastRenderer.setFrameOut(getFrameOut());
			}
			else if (numberOfFilters >= 2) {

				chainOfRenderer.get(0).setFrameIn(getFrameIn());
				chainOfRenderer.get(0).setFrameOut(IntermediatesFrames.get(0));

				for (int j = 1; j < numberOfFilters - 1; j++) {
					chainOfRenderer.get(j).setFrameIn(IntermediatesFrames.get(j - 1));
					chainOfRenderer.get(j).setFrameOut(IntermediatesFrames.get(j));
				}
				lastRenderer.setFrameIn(IntermediatesFrames.get(lastFrameIndex));
				lastRenderer.setFrameOut(getFrameOut());
			}
			setFrameOut(lastRenderer.getFrameOut());
		}
		else {
			getFrameIn().copyTo(getFrameOut());
		}
	}
	
	public void execute() {
		Boolean checkIfActivate=true;
		
		int numberOfFilters = chainOfRenderer.size();
		
		for (int i =0; i < numberOfFilters; i++) {
			IndexInterface data = chainOfRenderer.get(i).getData().getIndexData();
			
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
	
	public void openImage(String fileName) {
		setFrameIn(fileName);
		frameIn.copyTo(frameOut);
		background.createPlainGrayFrame(frameIn.getSpecs().rows, frameIn.getSpecs().cols, 127);
		deleteAllIntermediateFrames();
	}

	public void deleteAllIntermediateFrames() {
		IntermediatesFrames.clear();	
	}		
}
