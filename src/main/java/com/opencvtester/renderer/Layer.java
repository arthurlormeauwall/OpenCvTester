package com.opencvtester.renderer;

import java.util.List;

import com.opencvtester.controller.interfaces.DataProvider;
import com.opencvtester.controller.interfaces.Renderer;
import com.opencvtester.data.interfaces.LayerDataInterface;
import com.opencvtester.renderer.interfaces.ChainRenderer;
import com.opencvtester.renderer.interfaces.FrameInterface;

public class Layer extends ChainRenderer implements DataProvider {

	protected LayerDataInterface layerData;
	protected FrameInterface background;
	protected OpacityFilter opacityFilter;
	
	
	public Layer(List<? extends Renderer> chainOfRenderer) {
		super(chainOfRenderer);
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
		// TODO Auto-generated method stub
		return 0;
	}

	public Renderer getFirstFilter() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public LayerDataInterface getData() {
		return layerData;
	}

}
