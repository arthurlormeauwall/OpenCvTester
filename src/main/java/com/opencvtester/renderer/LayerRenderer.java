package com.opencvtester.renderer;

import java.util.Stack;

import com.opencvtester.renderer.entity.Layer;
import com.opencvtester.renderer.interfaces.IOFrame;

public class LayerRenderer extends Renderer {

	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayerRenderer(IOFrame mainFilters) {
		super(mainFilters);
	}

	
	/*
	 * GETTERS & SETTERS
	 */
	public IOFrame getLastFilter() {
		return ((Layer)mainFilter).getOpacityFilter(); 
	}

	public int getNumberOfFiltersPlusOpacity() {
		return chainOfIOFrame.size()+1;
	}
	
	/*
	 * FEATURES
	 */
	@Override
	public void execute(Stack<IOFrame> chainOfFilters) {
		setChain(chainOfFilters);
		render();
		((Layer)mainFilter).getOpacityFilter().execute();	
	}
}
