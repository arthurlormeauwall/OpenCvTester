package com.opencvtester.data;

import java.io.Serializable;
import java.util.Stack;

public class LayerData extends Command implements Serializable {

	
	private static final long serialVersionUID = 3016033995294210177L;
	
	 private Float opacityValue;
	 private Stack<String> filterNames;

	 public LayerData(int layerIndex,  Float opacityValue){
		 this.opacityValue=opacityValue;
          this.setFilterNames(null);
	 }
	 
	 public LayerData(int layerIndex,  Float opacityValue, Stack<String> filterNames){
		 this.opacityValue=opacityValue;
          this.setFilterNames(filterNames);
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
}
