package renderingEngine;

import java.util.HashMap;
import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.filter.Filter;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.history.imp.UndoIdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;
import filtersDataBase.OpacityFilter;

public class Layer extends CompositeFilters
{
	protected Frame background;
	protected OpacityFilter alphaFilter;
	
	
	public Layer (FiltersDataBase filtersDatabase, Id id, UndoIdHistory<Id> undoIdHistory, UndoIdHistory<Id>  renderAtIdHistory) {
		super(filtersDatabase, id, undoIdHistory, renderAtIdHistory);
		
		filtersDatabase = new FiltersDataBase();
		alphaFilter = filtersDatabase.getAlphaFilter();
		alphaFilter.setRenderAtIdHistory(this.renderAtIdHistory);
		alphaFilter.setUndoIdHistory(this.undoIdHistory);
	}
	
	protected void init(Frame background, Frame source, Frame dest) {
		setSource(source);
		setDest(dest);

		alphaFilter.setSource(source);
		alphaFilter.setDest(dest);

		int whiteValue = background.getSpecs().bitMax;
		alphaFilter.init(background);
		alphaFilter.setAlpha(whiteValue);
		alphaFilter.store();
		
		alphaFilter.getId().set(id.get()[0], 1, id.getGroupId() + 1);
	}

	protected Filter createFilter(Stack<Id> id, Stack<String> commandsInDataBase){
		Filter newFilter = (Filter) dbControls.getFilter(commandsInDataBase.get(0));
		newFilter.getId().set(id.get(0));

		newFilter.setRenderAtIdHistory(renderAtIdHistory);
		newFilter.setUndoIdHistory(undoIdHistory);

		return newFilter;
	}
	
	public void setFloatParameters(int controlIndex, HashMap<String,Float> parameters){	
		((FilterControlledByFloat)chainOfFilters.getCommand(controlIndex)).setParameter(parameters);
	}
	
	public void execute() {	
		render();
		alphaFilter.execute();	
	}
	
	public Boolean undo() {
		int undoControlId = undoIdHistory.getState().getParameter().get()[1];
		if (undoControlId == 0) {
			return chainOfFilters.undo();
		}
		if (undoControlId == 1) {
			return alphaFilter.undo();
		}
		else {
			return false;
		}	
	}
	
	public Boolean redo() {
		int undoControlId = undoIdHistory.getState().getParameter().get()[1];
		if (undoControlId == 0) {
			return chainOfFilters.redo();
		}
		if (undoControlId == 1) {
			return alphaFilter.redo();
		}
		else {
			return false;
		}
	}
	
	public void store(){
		int undoControlId = undoIdHistory.getState().getParameter().get()[1];
		if (undoControlId == 0) {
			chainOfFilters.store();
		}
		if (undoControlId == 1) {
			alphaFilter.store();
		}
	}
	
	public void updateId(int groupDeepnessIndex, int newValue){
		chainOfFilters.updateId(groupDeepnessIndex, newValue);
	}

	public Command clone() {	
		Layer newMaskedLayer= new Layer(dbControls, id, undoIdHistory, renderAtIdHistory);
		
		newMaskedLayer.setChain(chainOfFilters.clone());
		newMaskedLayer.setAlpha(alphaFilter.getAlpha());
		newMaskedLayer.setBackGround(background);
		
		return newMaskedLayer;
	}

	public OpacityFilter getAlpha(){
		return alphaFilter;
	}
	
	public Command getLastFilter() {
		return alphaFilter;
	}
	
	public void setAlpha(Frame alpha){
		this.alphaFilter.setAlpha(alpha);
	}
	
	public void setAlpha(int opacity){
		alphaFilter.setAlpha(opacity);
	}
	
	public void setBackGround(Frame background){
		this.background = background; 
	}
	
	public int getNumberOfFilters() {
		return chainOfFilters.getSize() + 1;
	}
}
