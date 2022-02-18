package com.opencvtester.renderer;

import java.util.List;

import com.opencvtester.controller.interfaces.DataProvider;
import com.opencvtester.controller.interfaces.Renderer;
import com.opencvtester.data.LayerData;
import com.opencvtester.data.interfaces.LayerDataInterface;
import com.opencvtester.renderer.interfaces.ChainRenderer;
import com.opencvtester.renderer.interfaces.FrameInterface;

public class Layer extends ChainRenderer implements DataProvider {

	protected LayerDataInterface layerData;
	protected FrameInterface background;
	protected OpacityFilter opacityFilter;
	
	
	public Layer(List<? extends Renderer> chainOfRenderer) {
		super(chainOfRenderer);
		layerData= new LayerData();
		opacityFilter = new OpacityFilter("Opacity");
	}
	
	public FrameInterface getBackground() {
		return background;
	}

	public void setBackground(FrameInterface background) {
		this.background = background;
	}

	public OpacityFilter getOpacityFilter() {
		return opacityFilter;
	}

	public Renderer getLast() {
		return getOpacityFilter(); 
	}

	public int getNumberOfFiltersPlusOpacity() {
		return chainOfRenderer.size()+1;
	}
	
	public void render() {
		execute();
		getOpacityFilter().render();	
	}

	public int getNumberOfFilters() {
	
		return layerData.getNumberOfFilters();
	}

	public Renderer getFirstFilter() {
		
		return null;
	}
	
	public LayerDataInterface getData() {
		return layerData;
	}

}
