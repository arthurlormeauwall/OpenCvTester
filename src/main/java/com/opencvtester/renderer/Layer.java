package com.opencvtester.renderer;

import java.util.Stack;

import com.opencvtester.controller.interfaces.IndexProvider;
import com.opencvtester.data.LayerData;
import com.opencvtester.data.interfaces.IndexInterface;
import com.opencvtester.data.interfaces.LayerDataInterface;

public class Layer extends RendererWithData {

	protected LayerDataInterface layerData;
	protected FilterChainRenderer chainOfRenderer;
	
	public Layer(int layerIndex) {
		chainOfRenderer= new FilterChainRenderer(layerIndex);
		chainOfRenderer.setFrameIn(frameIn);
		chainOfRenderer.setFrameOut(frameOut);
		layerData= new LayerData(layerIndex);
	}

	public void setFrameIn(FrameInterface frameIn){
		this.frameIn=frameIn;
		chainOfRenderer.setFrameIn(frameIn);
	}
	public void setFrameOut(FrameInterface frameOut){
		this.frameOut=frameOut;
		chainOfRenderer.setFrameOut(frameOut);
	}
	
	public OpacityFilter getOpacityFilter() {
		return chainOfRenderer.getOpacityFilter();
	}

	public void render() {
		chainOfRenderer.render();
	}

	public int getNumberOfFilters() {	
		return chainOfRenderer.getNumberOfFilters();
	}

	public LayerDataInterface getFilterData() {
		return layerData;
	}

	public ControlledFilter getFilter(int filterIndex) {
		return (ControlledFilter)chainOfRenderer.getFilter(filterIndex);
	}

	public void addFilter(ControlledFilter filter) {
		chainOfRenderer.addFilter(filter);	
	}

	public void removeFilter(int filterIndex) {
		chainOfRenderer.removeFilter(filterIndex);	
	}

	public Stack<ControlledFilter> getFilters() {	
		return chainOfRenderer.getFilters();
	}

	public IndexInterface getIndexData() {
		return layerData.getIndexData();
	}
	
	public IndexProvider getData() {
		return layerData;
	}

	public void setBackground(FrameInterface background) {
		chainOfRenderer.setBackground(background);
	}

	public void dealFrames() {
		chainOfRenderer.dealFrames();
		
	}
	
	public void deleteAllIntermediateFrames() {
		chainOfRenderer.deleteAllIntermediateFrames();	
	}
}
