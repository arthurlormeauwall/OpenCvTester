package com.opencvtester.data;

import java.util.Stack;

import com.opencvtester.data.interfaces.LayerDataInterface;

public class LayerData extends Index implements LayerDataInterface
{
	private Float opacityValue;
	private Stack<String> filterNames;
	
	public LayerData() {
	
	}
	
	public LayerData (int layerIndex) {
		opacityValue=1f;
		filterNames= new Stack<String>();
	}
	
	public LayerData (int layerIndex, Stack<String> filterNames) {
	
		opacityValue=1f;
		this.filterNames=filterNames;
	}
	
	public LayerData (int layerIndex, Float opacity) {
		
		opacityValue=opacity;
		this.filterNames=new Stack<String>();
	}
	
	
	public LayerData (int layerIndex, Float opacity, Stack<String> filterNames) {

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
		return filterNames.size();
	}

	public Boolean hasFilter() {
		return (getNumberOfFilters()!=0);
	}

	public LayerData clone() {	
		return new LayerData(layerIndex(),opacityValue,filterNames);
	}

}
