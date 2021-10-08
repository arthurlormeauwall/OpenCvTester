package renderingEngine;

import java.util.Stack;

import algo.AdjustControlFloat;
import algo.dataBase.AlphaControl;
import algo.dataBase.DbControls;
import baseClasses.Control;
import baseClasses.Id;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;

public class MaskedLayer extends FrameLayer
{
	
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
	public void setBackGround(Frame bg){
		m_backGround = bg; 
	}
	public void setFloatParameters(int controlIndex, Stack<Float> parameter){
		
		((AdjustControlFloat)m_controls.getControl(controlIndex)).setParameter(parameter);
	}


	// FrameLayer implementation
	public Control getLastControl() {
		return m_alpha;
	}
	public Control createControl(Stack<Id> id, Stack<Integer> controlNumber){
		Control control = (Control) m_dbControl.getControl(controlNumber.get(0));
		control.getId().set(id.get(0));

		control.setRenderAtId(m_renderAtIdHistory);
		control.setUndoId(m_undoIdHistory);

		return control;
	}
	public int getNumberOfControl() {
	
		return m_controls.getSize() + 1;
	}
	
	// Control implementation
	public void compute() {
		render();
		m_alpha.compute();
	}
	public Boolean undo() {
		int undoControlId = m_undoIdHistory.getLast().getParameter().get()[1];
		if (undoControlId == 0) {
			return m_controls.undo();
		}
		if (undoControlId == 1) {
			return m_alpha.undo();
		}
		else {
			return false;
		}
		
	}
	public Boolean redo() {
		int undoControlId = m_undoIdHistory.getLast().getParameter().get()[1];
		if (undoControlId == 0) {
			return m_controls.redo();
		}
		if (undoControlId == 1) {
			return m_alpha.redo();
		}
		else {
			return false;
		}
	}
	public void store(){
		int undoControlId = m_undoIdHistory.getLast().getParameter().get()[1];
		if (undoControlId == 0) {
			m_controls.store();
		}
		if (undoControlId == 1) {
			m_alpha.store();
		}
	}
	public void updateId(int groupDeepnessIndex, int newValue){
		m_controls.updateId(groupDeepnessIndex, newValue);
	}

	public Control clone() {
		
		MaskedLayer temp= new MaskedLayer(m_id, m_undoIdHistory, m_renderAtIdHistory);
		
		temp.setChainControl(m_controls.clone());
		temp.setAlpha(m_alpha.getAlpha());
		temp.setBackGround(m_backGround);
		
		return temp;
	}

	protected Frame m_backGround;
	protected AlphaControl m_alpha;
	protected DbControls m_dbControl;


	
}
