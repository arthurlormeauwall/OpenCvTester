package com.opencvtester.renderer;

import java.util.Stack;

import com.opencvtester.appControllers.Renderer;

public class ChainOfFiltersRenderer extends ChainRenderer {

	protected OpacityFilter opacityFilter;

	public ChainOfFiltersRenderer(int layerIndex) {
		super(new Stack<ControlledFilter>());
		opacityFilter = new OpacityFilter("Opacity");
		opacityFilter.getData().getIndexData().setLayerIndex(layerIndex);
		opacityFilter.setBackGround(background);
	}

	public Renderer getLast() {
		return opacityFilter; 
	}

	public int getNumberOfFiltersPlusOpacity() {
		return chainOfRenderer.size()+1;
	}
	
	public void render() {
		execute();
		 opacityFilter.render();	
	}

	public OpacityFilter getOpacityFilter() {
	
		return opacityFilter;
	}

	public int getNumberOfFilters() {
		
		return chainOfRenderer.size();
	}



	public ControlledFilter getFilter(int filterIndex) {
		return (ControlledFilter)chainOfRenderer.get(filterIndex);
	}

	@SuppressWarnings("unchecked")
	public void addFilter(ControlledFilter filter) {
		((Stack<ControlledFilter>)chainOfRenderer).push(filter);	
	}

	@SuppressWarnings("unchecked")
	public void removeFilter(int filterIndex) {
		((Stack<ControlledFilter>)chainOfRenderer).remove(filterIndex);	
	}

	@SuppressWarnings("unchecked")
	public Stack<ControlledFilter> getFilters() {	
		return ((Stack<ControlledFilter>)chainOfRenderer);
	}
}
