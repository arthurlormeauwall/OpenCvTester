package com.opencvtester.renderer.entity;

import com.opencvtester.renderer.LayerRenderer;
import com.opencvtester.renderer.interfaces.FrameInterface;
import com.opencvtester.renderer.interfaces.IOFrame;

public class Layer extends IOFrame {

	protected FrameInterface background;
	protected LayerRenderer renderer;
	protected OpacityFilter opacityFilter;
	
	
	public FrameInterface getBackground() {
		return background;
	}

	public void setBackground(FrameInterface background) {
		this.background = background;
	}

	public void render() {	
		renderer.render();
	}

	public void dealFrames() {
		renderer.dealFrames(null);	
	}

	public OpacityFilter getOpacityFilter() {
		return opacityFilter;
	}

	public void deleteAllIntermediateFrames() {
		renderer.deleteAllIntermediateFrames();
	}
}
