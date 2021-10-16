package application;

import algorithmsDataBase.DbControls;
import baseClasses.Id;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;
import renderingEngine.FrameLayer;

public abstract class RendererInterface extends FrameLayer implements FunctionalitiesInterface
{
	protected Frame m_background;
	
	public RendererInterface (DbControls dbControls, Frame background, Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id>  renderAtIdHistory) {
		super(dbControls, id, undoIdHistory, renderAtIdHistory);
		m_background = background;
	}		
}
