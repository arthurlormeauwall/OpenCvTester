package com.opencvtester.renderingEngine;

import java.util.Stack;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.filtersDataBase.FiltersDataBase;

public class LayerFactory {
	public static Layer createLayer(Stack<Id> id, Stack<String> filterNames,FiltersDataBase filtersDataBase){
		Layer newLayer= new Layer(filtersDataBase, id.get(0).layerIndex());
		
		if (filterNames!=null) {
			int numberOfFilterToAdd = filterNames.size();

			for (int i = 0; i < numberOfFilterToAdd; i++) {		
				newLayer.createAndAdd(id.get(i + 1), filterNames.get(i));
			}
		}	
		return newLayer;
	}
	
	public static Layer createEmptyLayer(int layerIndex, FiltersDataBase filterDataBase){
		Layer newLayer= new Layer(filterDataBase, layerIndex);
		return newLayer;
	}
}
