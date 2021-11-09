package renderingEngine;

import java.util.HashMap;
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

public class Layer extends CompositeFilters
{
	protected Frame background;
	protected OpacityFilter opacityFilter;
	
	
	public Layer (FiltersDataBase filtersDatabase, Id id, IdHistory<Id>  renderAtIdHistory) {
		super(filtersDatabase, id, renderAtIdHistory);
		
		// filtersDatabase = new FiltersDataBase();
		opacityFilter = filtersDatabase.getAlphaFilter();
		opacityFilter.setRenderAtIdHistory(this.renderAtIdHistory);

	}
	
	protected void init(Frame background, Frame source, Frame dest) {
		setSource(source);
		setDest(dest);

		opacityFilter.setSource(source);
		opacityFilter.setDest(dest);

		int whiteValue = background.getSpecs().bitMax;
		opacityFilter.init(background);
		opacityFilter.setOpacity(whiteValue);
		
		opacityFilter.getId().set(id.get()[0], 1, id.getGroupId() + 1);
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
		render();
		opacityFilter.execute();	
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
	
	public Command getLastFilter() {
		return opacityFilter;
	}
	
	public void setOpacity(int opacity){
		opacityFilter.setOpacity(opacity);
	}
	
	public void setBackGround(Frame background){
		this.background = background; 
	}
	
	public int getNumberOfFilters() {
		return chainOfFilters.getSize() + 1;
	}
}
