package renderingEngine;

import baseClasses.Id;
import baseClasses.history.IdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;

public abstract class ChainOfLayersInterface extends CompositeFilters 
{
	protected Frame m_background;
	
	public ChainOfLayersInterface (FiltersDataBase dbControls, Frame background, Id id, IdHistory<Id>  renderAtIdHistory) {
		super(dbControls, id, renderAtIdHistory);
		m_background = background;
	}		
}
