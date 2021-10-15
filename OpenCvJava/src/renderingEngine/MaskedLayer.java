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
	protected Frame m_background;
	protected AlphaControl m_alpha;
	protected DbControls m_dbControl;
	
	public MaskedLayer (Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id>  renderAtIdHistory) {
		super(id, undoIdHistory, renderAtIdHistory);
		
		m_dbControl = new DbControls();
		m_alpha = m_dbControl.getAlphaControl();
		m_alpha.setRenderAtId(m_renderAtIdHistory);
		m_alpha.setUndoId(m_undoIdHistory);
	}
	
	public void init(Frame background, Frame source, Frame dest) {
		setSource(source);
		setDest(dest);

		m_alpha.setSource(source);
		m_alpha.setDest(dest);

		int whiteValue = background.getSpecs().s_bitMax;
		m_alpha.setBackGround(background);
		m_alpha.setFlags();
		m_alpha.setAlpha(whiteValue);
		m_alpha.store();
		
		m_alpha.getId().set(m_id.get()[0], 1, m_id.getGroupId() + 1);
	}

	public AlphaControl getAlpha(){
		return m_alpha;
	}
	
	public void setAlpha(Frame alpha){
		m_alpha.setAlpha(alpha);
	}
	
	public void setAlpha(int opacity){
		m_alpha.setAlpha(opacity);
	}
	
	public void setBackGround(Frame background){
		m_background = background; 
	}
	
	public void setFloatParameters(int controlIndex, Stack<Float> parameters){
		
		((AdjustControlFloat)m_chainOfControls.getControl(controlIndex)).setParameter(parameters);
	}

	public Control getLastControl() {
		return m_alpha;
	}
	
	public Control createControl(Stack<Id> id, Stack<Integer> stackOfControlIndexInDataBase){
		Control newControl = (Control) m_dbControl.getControl(stackOfControlIndexInDataBase.get(0));
		newControl.getId().set(id.get(0));

		newControl.setRenderAtId(m_renderAtIdHistory);
		newControl.setUndoId(m_undoIdHistory);

		return newControl;
	}
	
	public int getNumberOfControl() {
		return m_chainOfControls.getSize() + 1;
	}
	
	public void compute() {
		render();
		m_alpha.compute();
	}
	
	public Boolean undo() {
		int undoControlId = m_undoIdHistory.getState().getParameter().get()[1];
		if (undoControlId == 0) {
			return m_chainOfControls.undo();
		}
		if (undoControlId == 1) {
			return m_alpha.undo();
		}
		else {
			return false;
		}	
	}
	
	public Boolean redo() {
		int undoControlId = m_undoIdHistory.getState().getParameter().get()[1];
		if (undoControlId == 0) {
			return m_chainOfControls.redo();
		}
		if (undoControlId == 1) {
			return m_alpha.redo();
		}
		else {
			return false;
		}
	}
	
	public void store(){
		int undoControlId = m_undoIdHistory.getState().getParameter().get()[1];
		if (undoControlId == 0) {
			m_chainOfControls.store();
		}
		if (undoControlId == 1) {
			m_alpha.store();
		}
	}
	
	public void updateId(int groupDeepnessIndex, int newValue){
		m_chainOfControls.updateId(groupDeepnessIndex, newValue);
	}

	public Control clone() {	
		MaskedLayer newMaskedLayer= new MaskedLayer(m_id, m_undoIdHistory, m_renderAtIdHistory);
		
		newMaskedLayer.setChainControl(m_chainOfControls.clone());
		newMaskedLayer.setAlpha(m_alpha.getAlpha());
		newMaskedLayer.setBackGround(m_background);
		
		return newMaskedLayer;
	}
}
