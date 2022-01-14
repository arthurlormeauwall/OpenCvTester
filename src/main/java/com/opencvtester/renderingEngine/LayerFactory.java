package com.opencvtester.renderingEngine;

import java.util.Stack;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.filtersDataBase.FiltersDataBase;

public class LayerFactory {

	private static FiltersDataBase filtersDataBase;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayerFactory(FiltersDataBase filtersDataBase) {
		this.filtersDataBase=filtersDataBase;
	}

	/*
	 * FEATURES
	 */
	protected static Layer createLayer(Stack<Id> id, Stack<String> filterNames,FiltersDataBase filtersDataBase){
		Layer newLayer= new Layer(filtersDataBase, id.get(0).layerIndex());
		
		if (filterNames!=null) {
			int numberOfFilterToAdd = filterNames.size();

			for (int i = 0; i < numberOfFilterToAdd; i++) {		
				newLayer.createAndAdd(id.get(i + 1), filterNames.get(i));
			}
		}	
		return newLayer;
	}
	protected Layer createLayer(Stack<Id> id, Stack<String> filterNames){
		Layer newLayer= new Layer(filtersDataBase, id.get(0).layerIndex());
		
		if (filterNames!=null) {
			int numberOfFilterToAdd = filterNames.size();

			for (int i = 0; i < numberOfFilterToAdd; i++) {		
				newLayer.createAndAdd(id.get(i + 1), filterNames.get(i));
			}
		}	
		return newLayer;
	}
	
	public Layer createEmptyLayer(int layerIndex){
		Layer newLayer= new Layer(filtersDataBase, layerIndex);
		return newLayer;
	}
}
