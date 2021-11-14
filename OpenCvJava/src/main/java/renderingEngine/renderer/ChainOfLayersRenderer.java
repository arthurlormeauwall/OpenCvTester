package renderingEngine.renderer;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.openCvFacade.Frame;
import renderingEngine.CompositeFilters;
import renderingEngine.Layer;

public class ChainOfLayersRenderer extends Renderer {

	private Frame background;
	public ChainOfLayersRenderer(CompositeFilters compositeFilters, Frame background) {
		super(compositeFilters);
		this.background=background;
	}

	public void execute(Stack<Command> chainOfFilters) {
		dealFrames(chainOfFilters);	
		dealBackground();	
		dealFramesInLayers();	
		render();	
	}

	public void dealBackground(){
		int numberOfMaskedLayers = chainOfFilters.size();
		
		if (numberOfMaskedLayers>0) {
			((Layer)chainOfFilters.get(0)).setBackGround(background);
			for (int i = 1; i < numberOfMaskedLayers; i++) {
				((Layer)chainOfFilters.get(i)).setBackGround(((Layer)chainOfFilters.get(i - 1)).getDest());
			}
		}	
	}   
	
	public void dealFramesInLayers(){
		for (int i = 0; i < chainOfFilters.size(); i++) {
			 ((Layer)chainOfFilters.get(i)).dealFrames();	
		}
	}
	
	public Command getLastFilter(){
		return chainOfFilters.get(chainOfFilters.size() - 1);
	}   
	
	public int getNumberOfFilters() {
		return chainOfFilters.size();
	}
}
