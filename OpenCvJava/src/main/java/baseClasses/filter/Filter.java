package baseClasses.filter;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.IoFrame;
import baseClasses.history.imp.UndoIdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;



public abstract class Filter extends Command implements IoFrame
{
	
	protected Frame source;
	protected Frame dest;
	
	public Filter() {
		source = new Frame ();
		dest   = new Frame ();
	}
	
	public Filter(Id id, UndoIdHistory<Id> undoIdHistory, UndoIdHistory<Id>  renderAtIdHistory) {
		super (id, undoIdHistory, renderAtIdHistory);
		source = new Frame ();
		dest   = new Frame ();
	}

	public Frame getSource()      {
		return source;
	}
	
	public void setSource(Frame s){
		source=s;
	}
	
	public Frame getDest()        {
		return dest;
	}
	
	public void setDest(Frame d)  {
		dest=d;
	}
}
