package com.opencvtester.renderingEngine;

import java.util.Stack;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.filtersDataBase.FiltersDataBase;

public class LayersFactory {

	private Layer newLayer;
	private FiltersDataBase filtersDataBase;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayersFactory(FiltersDataBase filtersDataBase) {
		this.filtersDataBase=filtersDataBase;
	}

	/*
	 * FEATURES
	 */
	protected Layer createLayer(Stack<Id> id, Stack<String> filterNames){
		newLayer= new Layer(filtersDataBase, id.get(0));
		
		if (filterNames!=null) {
			int numberOfFilterToAdd = filterNames.size();

			for (int i = 0; i < numberOfFilterToAdd; i++) {		
				newLayer.createAndAdd(id.get(i + 1), filterNames.get(i));
			}
		}	
		return newLayer;
	}
	
	protected Layer createEmptyLayer(Id id){
		newLayer= new Layer(filtersDataBase, id);
		return newLayer;
	}
}
