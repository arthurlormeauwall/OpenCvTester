package renderingEngine.renderer;

import java.util.Stack;

import baseClasses.filter.Filter;
import baseClasses.Command;
import baseClasses.frame.Frame;
import renderingEngine.CompositeFilter;
import renderingEngine.Layer;
import userFilters.BlueGreenRedMultiplierFilter;

public class ChainOfLayersRenderer extends Renderer {

	private Frame background;
	
	public ChainOfLayersRenderer(CompositeFilter compositeFilters, Frame background) {
		super(compositeFilters);
		this.background=background;
	}

	public void execute(Stack<Command> chainOfFilters) {
		dealFrames(chainOfFilters);	
		dealBackground();	
		dealFramesInLayers();
		BlueGreenRedMultiplierFilter f=new BlueGreenRedMultiplierFilter();
		int indexOfLastLayer= getNumberOfFilters()-1;
		int indexOfLastFilterInLastLayer=((Layer)chainOfFilters.get(indexOfLastLayer)).getNumberOfFilters()-2;
		if (indexOfLastFilterInLastLayer>=0) {
			compositeFilters.setDest(((Layer)chainOfFilters.get(indexOfLastLayer)).getFilter(indexOfLastFilterInLastLayer).getDest());
			//f.setSource(((Layer)chainOfFilters.get(indexOfLastLayer)).getFilter(indexOfLastFilterInLastLayer).getSource());
			//f.setDest(((Layer)chainOfFilters.get(indexOfLastLayer)).getFilter(indexOfLastFilterInLastLayer).getDest());
			//f.execute();
			
			//((Layer)compositeFilters.get(indexOfLastLayer)).getFilter(indexOfLastFilterInLastLayer).execute();
			
		}
		
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
