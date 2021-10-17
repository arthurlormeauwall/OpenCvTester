package renderingEngine;

import java.util.Stack;

import algorithmsDataBase.AlphaControl;
import algorithmsDataBase.DbControls;
import baseClasses.Control;
import baseClasses.Id;
import baseClasses.adjustControl.AdjustControlFloat;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;

public class MaskedLayer extends FrameLayer
{
	protected Frame background;
	protected AlphaControl alpha;
	
	
	public MaskedLayer (DbControls dbControls, Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id>  renderAtIdHistory) {
		super(dbControls, id, undoIdHistory, renderAtIdHistory);
		
		dbControls = new DbControls();
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

	public AlphaControl getAlpha(){
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
	
	public void setFloatParameters(int controlIndex, Stack<Float> parameters){
		
		((AdjustControlFloat)chainOfControls.getControl(controlIndex)).setParameter(parameters);
	}

	public Control getLastControl() {
		return alpha;
	}
	
	protected Control createControl(Stack<Id> id, Stack<Integer> stackOfControlIndexInDataBase){
		Control newControl = (Control) dbControls.getControl(stackOfControlIndexInDataBase.get(0));
		newControl.getId().set(id.get(0));

		newControl.setRenderAtId(renderAtIdHistory);
		newControl.setUndoId(undoIdHistory);

		return newControl;
	}
	
	public int getNumberOfControl() {
		return chainOfControls.getSize() + 1;
	}
	
	public void compute() {	
		render();
		alpha.compute();	
	}
	
	public Boolean undo() {
		int undoControlId = undoIdHistory.getState().getParameter().get()[1];
		if (undoControlId == 0) {
			return chainOfControls.undo();
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
			return chainOfControls.redo();
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
			chainOfControls.store();
		}
		if (undoControlId == 1) {
			alpha.store();
		}
	}
	
	public void updateId(int groupDeepnessIndex, int newValue){
		chainOfControls.updateId(groupDeepnessIndex, newValue);
	}

	
	public Control clone() {	
		MaskedLayer newMaskedLayer= new MaskedLayer(dbControls, id, undoIdHistory, renderAtIdHistory);
		
		newMaskedLayer.setChainControl(chainOfControls.clone());
		newMaskedLayer.setAlpha(alpha.getAlpha());
		newMaskedLayer.setBackGround(background);
		
		return newMaskedLayer;
	}
}
