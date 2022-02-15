package com.opencvtester.data;

import java.util.Stack;

import com.opencvtester.filterController.ControlledFilter;
import com.opencvtester.renderer.IOFrame;
import com.opencvtester.renderer.LayerRenderer;
import com.opencvtester.renderer.OpacityFilter;
import com.opencvtester.renderer.interfaces.FrameInterface;

public class Layer extends ChainOfCommands
{
	private static final long serialVersionUID = 3016033995294210177L;
	private Float opacityValue;
	private Stack<String> filterNames;
	
	public Layer() {
		super("filter",0, -2);
	}
	
	public Layer (int layerIndex) {
		super("filter", layerIndex, -2);
		opacityValue=1f;
		filterNames= new Stack<String>();
	}
	
	public Layer (int layerIndex, Stack<String> filterNames) {
		super("filter", layerIndex, -2);
		opacityValue=1f;
		this.filterNames=filterNames;
	}
	
	public Layer (int layerIndex, Float opacity) {
		super("filter", layerIndex, -2);
		opacityValue=opacity;
		this.filterNames=new Stack<String>();
	}
	
	
	public Layer (int layerIndex, Float opacity, Stack<String> filterNames) {
		super("filter", layerIndex, -2);
		opacityValue=opacity;
		this.filterNames=filterNames;
	}

	public Float getOpacityValue() {
		return opacityValue;
	}

	public void setOpacityValue(Float opacityValue) {
		this.opacityValue = opacityValue;
	}

	public Stack<String> getFilterNames() {
		return filterNames;
	}

	public void setFilterNames(Stack<String> filterNames) {
		this.filterNames = filterNames;
	}
	
	public int getNumberOfFilters() {
		return getSize();
	}

	public Boolean hasFilter() {
		return (getNumberOfFilters()!=0);
	}

	public FilterData getFilter(int index) {
		return (FilterData) getCommand(index);
	}

	public Layer clone() {	
		return new Layer(layerIndex(),opacityValue,filterNames);
	}
	
	public void addFilter(FilterData controlledFilter) {
		controlledFilter.setLayerIndex(layerIndex());
		add(controlledFilter);
	}

	public void deleteFilter(FilterData filter) {
		if (filter.layerIndex()==layerIndex()) {
			delete(filter.filterIndex());
		}
	}
	
	public void deleteAllFilters() {
		commands.clear();
	}
	
	public Command getFirstFilter() {
		if (commands!=null)	{
			return commands.get(0);
		}
		return null;
	}
}
