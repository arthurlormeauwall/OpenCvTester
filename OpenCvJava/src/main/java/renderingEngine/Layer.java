package renderingEngine;

import java.util.LinkedHashMap;
import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.filter.Filter;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.history.IdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;
import filtersDataBase.OpacityFilter;
import renderingEngine.renderer.LayerRenderer;

public class Layer extends CompositeFilters
{
	protected Frame background;
	protected OpacityFilter opacityFilter;	
	
	public Layer (FiltersDataBase filtersDatabase, Id id, IdHistory<Id>  renderAtIdHistory) {
		super(filtersDatabase, id, renderAtIdHistory);
		
		opacityFilter = filtersDatabase.getAlphaFilter();
		opacityFilter.setRenderAtIdHistory(this.renderAtIdHistory);
		
		renderer= new LayerRenderer(this);
	}
	
	protected void init(Frame background, Frame source, Frame dest) {
		setSource(source);
		setDest(dest);

		opacityFilter.setSource(source);
		opacityFilter.setDest(dest);

		opacityFilter.init(background);
	
		
		opacityFilter.getId().set(id.get()[0], 1);
	}

	protected Filter create(Stack<Id> id, Stack<String> commandsInDataBase){
		Filter newFilter = (Filter) filtersDataBase.getFilter(commandsInDataBase.get(0));
		newFilter.getId().set(id.get(0));
		newFilter.setRenderAtIdHistory(renderAtIdHistory);
		return newFilter;
	}
	
	public void setFloatParameters(int controlIndex, LinkedHashMap<String,Float> parameters){	
		((FilterControlledByFloat)chainOfFilters.getCommand(controlIndex)).setParameter(parameters);
	}
	
	public void execute() {	
		renderer.execute(chainOfFilters.getChain());
	}
	
	
	public void updateId(int groupDeepnessIndex, int newValue){
		chainOfFilters.updateId(groupDeepnessIndex, newValue);
	}

	public Command clone() {	
		Layer newMaskedLayer= new Layer(filtersDataBase, id, renderAtIdHistory);
		
		newMaskedLayer.setChain(chainOfFilters.clone());
		newMaskedLayer.setOpacity(opacityFilter.getOpacity());
		newMaskedLayer.setBackGround(background);
		
		return newMaskedLayer;
	}

	public OpacityFilter getOpacityFilter(){
		return opacityFilter;
	}
	
	public void setOpacity(Float opacity){
		opacityFilter.setOpacity(opacity);
	}
	
	public void setBackGround(Frame background){
		this.background = background;
		opacityFilter.setBackGround(background);
	}
	
	public int getNumberOfFilters() {
		return chainOfFilters.getSize() + 1;
	}

	public FilterControlledByFloat getFilter(int i) {
		return (FilterControlledByFloat)chainOfFilters.getCommand(i);
	}

	@Override
	public int groupDeepnessIndex() {
		// TODO Auto-generated method stub
		return 1;
	}

	public void dealFrames() {
		renderer.dealFrames(chainOfFilters.getChain());		
	}
}
