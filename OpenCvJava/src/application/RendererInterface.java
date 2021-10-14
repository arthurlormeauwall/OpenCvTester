package application;

import java.util.Stack;


import baseClasses.Id;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;
import renderingEngine.FrameLayer;

public abstract class RendererInterface extends FrameLayer {
	public RendererInterface (Frame background, Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id>  renderAtIdHistory) {
		super(id, undoIdHistory, renderAtIdHistory);
		m_background = background;
	}
	
	public abstract void addControlInLayer(Stack<Id> controlId, int controlIndexInDataBase); 	
	public abstract void delControlInLayer(Stack<Id> controlId)  ;	
	public abstract void addLayer(Stack<Id> controlId, Stack<Integer> stackOfindexInDataBase) ; 	
	public abstract void delLayer(Stack<Id> controlId) ; 	
	public abstract void setAlpha(int layerIndex, Frame alpha);	
	public abstract void setAlpha(int layerIndex, int opacity)	;
	public abstract void setParameters(Id ControlId, Stack<Float> parameters);	
	public abstract void setBypass(Id ControlId, Boolean p)  ;
	public abstract void dealBackground();  	
	public abstract void dealFramesInMaskedLayers()  ;
	public abstract void play();
	
	protected Frame m_background;
}
