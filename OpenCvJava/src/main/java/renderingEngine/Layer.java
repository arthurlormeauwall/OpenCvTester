package renderingEngine;

import java.util.Stack;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.filter.FilterControlledByFloat;
import baseClasses.history.imp.UndoIdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;
import filtersDataBase.OpacityFilter;

public class Layer extends ChainOfFilters
{
	protected Frame background;
	protected OpacityFilter alpha;
	
	public Layer (FiltersDataBase dbControls, Id id, UndoIdHistory<Id> undoIdHistory, UndoIdHistory<Id>  renderAtIdHistory) {
		super(dbControls, id, undoIdHistory, renderAtIdHistory);
		
		dbControls = new FiltersDataBase();
		alpha = dbControls.getAlphaControl();
		alpha.setRenderAtId(this.renderAtIdHistory);
		alpha.setUndoId(this.undoIdHistory);
	}
	
	protected void init(Frame background, Frame source, Frame dest) {
		setSource(source);
		setDest(dest);

		alpha.setSource(source);
		alpha.setDest(dest);

		int whiteValue = background.getSpecs().bitMax;
		alpha.init(background);
		alpha.setAlpha(whiteValue);
		alpha.store();
		
		alpha.getId().set(id.get()[0], 1, id.getGroupId() + 1);
	}

	protected Command createFilter(Stack<Id> id, Stack<String> commandsInDataBase){
		Command newControl = (Command) dbControls.getCommand(commandsInDataBase.get(0));
		newControl.getId().set(id.get(0));

		newControl.setRenderAtId(renderAtIdHistory);
		newControl.setUndoId(undoIdHistory);

		return newControl;
	}
	
	public void setFloatParameters(int controlIndex, Stack<Float> parameters){	
		((FilterControlledByFloat)chainOfCommands.getCommand(controlIndex)).setParameter(parameters);
	}
	
	public void execute() {	
		render();
		alpha.execute();	
	}
	
	public Boolean undo() {
		int undoControlId = undoIdHistory.getState().getParameter().get()[1];
		if (undoControlId == 0) {
			return chainOfCommands.undo();
		}
		if (undoControlId == 1) {
			return alpha.undo();
		}
		else {
			return false;
		}	
	}
	
	public Boolean redo() {
		int undoControlId = undoIdHistory.getState().getParameter().get()[1];
		if (undoControlId == 0) {
			return chainOfCommands.redo();
		}
		if (undoControlId == 1) {
			return alpha.redo();
		}
		else {
			return false;
		}
	}
	
	public void store(){
		int undoControlId = undoIdHistory.getState().getParameter().get()[1];
		if (undoControlId == 0) {
			chainOfCommands.store();
		}
		if (undoControlId == 1) {
			alpha.store();
		}
	}
	
	public void updateId(int groupDeepnessIndex, int newValue){
		chainOfCommands.updateId(groupDeepnessIndex, newValue);
	}

	public Command clone() {	
		Layer newMaskedLayer= new Layer(dbControls, id, undoIdHistory, renderAtIdHistory);
		
		newMaskedLayer.setChain(chainOfCommands.clone());
		newMaskedLayer.setAlpha(alpha.getAlpha());
		newMaskedLayer.setBackGround(background);
		
		return newMaskedLayer;
	}

	public OpacityFilter getAlpha(){
		return alpha;
	}
	
	public Command getLastControl() {
		return alpha;
	}
	
	public void setAlpha(Frame alpha){
		this.alpha.setAlpha(alpha);
	}
	
	public void setAlpha(int opacity){
		alpha.setAlpha(opacity);
	}
	
	public void setBackGround(Frame background){
		this.background = background; 
	}
	
	public int getNumberOfControl() {
		return chainOfCommands.getSize() + 1;
	}
}
