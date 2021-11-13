package renderingEngine.renderer;

import java.util.Stack;

import baseClasses.Command;
import renderingEngine.CompositeFilters;
import renderingEngine.Layer;

public class LayerRenderer extends Renderer {

	public LayerRenderer(CompositeFilters compositeFilters) {
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

	public int getNumberOfFilters() {
		return chainOfFilters.size() + 1;
	}

}
