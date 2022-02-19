package com.opencvtester.renderer;

import java.util.Stack;

import com.opencvtester.controller.interfaces.DataIndexProvider;
import com.opencvtester.controller.interfaces.DataProvider;
import com.opencvtester.controller.interfaces.Renderer;
import com.opencvtester.data.LayerData;
import com.opencvtester.data.interfaces.IndexInterface;
import com.opencvtester.data.interfaces.LayerDataInterface;
import com.opencvtester.renderer.interfaces.ChainRenderer;
import com.opencvtester.renderer.interfaces.FrameInterface;

public class Layer extends ChainRenderer implements DataProvider  {

	protected LayerDataInterface layerData;
	protected OpacityFilter opacityFilter;

	
	public Layer(int layerIndex) {
		super(new Stack<ControlledFilter>());
		layerData= new LayerData(layerIndex);
		opacityFilter = new OpacityFilter("Opacity");
		opacityFilter.getData().getIndexData().setLayerIndex(layerIndex);
		opacityFilter.setBackGround(background);
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
		return chainOfRenderer.size();
	}

	public LayerDataInterface getFilterData() {
		return layerData;
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


	public IndexInterface getIndexData() {
		return layerData.getIndexData();
	}
	
	public DataIndexProvider getData() {
		return layerData;
	}
}
