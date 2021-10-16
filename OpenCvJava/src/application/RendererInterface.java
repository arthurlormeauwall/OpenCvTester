package application;

import java.util.Stack;


import baseClasses.Id;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;
import renderingEngine.FrameLayer;

public abstract class RendererInterface extends FrameLayer implements FunctionalitiesInterface
{
	protected Frame m_background;
	
	public RendererInterface (Frame background, Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id>  renderAtIdHistory) {
		super(id, undoIdHistory, renderAtIdHistory);
		m_background = background;
	}
	
		
}
