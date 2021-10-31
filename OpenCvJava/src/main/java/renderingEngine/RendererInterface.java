package renderingEngine;

import application.FunctionalitiesInterface;
import baseClasses.Id;
import baseClasses.history.imp.UndoIdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;

public abstract class RendererInterface extends ChainOfFilters implements FunctionalitiesInterface
{
	protected Frame m_background;
	
	public RendererInterface (FiltersDataBase dbControls, Frame background, Id id, UndoIdHistory<Id> undoIdHistory, UndoIdHistory<Id>  renderAtIdHistory) {
		super(dbControls, id, undoIdHistory, renderAtIdHistory);
		m_background = background;
	}		
}
