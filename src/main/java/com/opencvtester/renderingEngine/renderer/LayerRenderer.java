package com.opencvtester.renderingEngine.renderer;

import java.util.Stack;

import com.opencvtester.baseClasses.Command;
import com.opencvtester.renderingEngine.CompositeFilter;
import com.opencvtester.renderingEngine.Layer;

public class LayerRenderer extends Renderer {

	public LayerRenderer(CompositeFilter compositeFilters) {
		super(compositeFilters);
	}

	@Override
	public void execute(Stack<Command> chainOfFilters) {
		setChain(chainOfFilters);
		render();
		((Layer)compositeFilters).getOpacityFilter().execute();	
		
	}

	public Command getLastFilter() {
		return ((Layer)compositeFilters).getOpacityFilter(); 
	}

	public int getNumberOfFiltersPlusOpacity() {
		return chainOfFilters.size()+1;
	}


}
