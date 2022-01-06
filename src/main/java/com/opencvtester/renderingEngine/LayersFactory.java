package com.opencvtester.renderingEngine;

import java.util.Stack;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.frame.FrameInterface;
import com.opencvtester.filtersDataBase.FiltersDataBase;

public class LayersFactory {

	private Layer newLayer;
	private FiltersDataBase filtersDataBase;
	private FrameInterface background;
	private FrameInterface source;
	private FrameInterface dest;
	
	public LayersFactory(FrameInterface background, FrameInterface source, FrameInterface dest, FiltersDataBase filtersDataBase) {
		this.background=background;
		this.source=source;
		this.dest=dest;
		this.filtersDataBase=filtersDataBase;
	}
	
	protected Layer createLayer(Stack<Id> id, Stack<String> filterNames){
		newLayer= new Layer(filtersDataBase, id.get(0));
		newLayer.init(background, source, dest);
		
		if (filterNames!=null) {
			int numberOfFilterToAdd = filterNames.size();

			for (int i = 0; i < numberOfFilterToAdd; i++) {		
				newLayer.createAndAdd(id.get(i + 1), filterNames.get(i));
			}
		}
		
		return newLayer;
	}
}
