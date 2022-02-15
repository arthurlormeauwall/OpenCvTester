package com.opencvtester.renderer;

import java.util.Stack;

import com.opencvtester.data.Command;
import com.opencvtester.data.CompositeFilter;
import com.opencvtester.data.Layer;

public class LayerRenderer extends Renderer {

	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayerRenderer(CompositeFilter compositeFilters) {
		super(compositeFilters);
	}

	
	/*
	 * GETTERS & SETTERS
	 */
	public Command getLastFilter() {
		return ((Layer)compositeFilters).getOpacityFilter(); 
	}

	public int getNumberOfFiltersPlusOpacity() {
		return chainOfFilters.size()+1;
	}
	
	/*
	 * FEATURES
	 */
	@Override
	public void execute(Stack<Command> chainOfFilters) {
		setChain(chainOfFilters);
		render();
		((Layer)compositeFilters).getOpacityFilter().execute();	
	}
}
