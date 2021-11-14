package baseClasses.filter;


import baseClasses.Command;
import baseClasses.Executable;
import baseClasses.Id;
import baseClasses.IoFrame;
import baseClasses.openCvFacade.Frame;



public abstract class Filter extends Command implements IoFrame, Executable
{
	protected Frame source;
	protected Frame dest;
	protected Boolean activate;
	
	public Filter() {
		activate= false;
		source = new Frame ();
		dest   = new Frame ();
	}
	
	public Filter(Id id) {
		super (id);
		activate= false;
		source = new Frame ();
		dest   = new Frame ();
	}

	public Frame getSource(){
		return source;
	}
	
	public void setSource(Frame source){
		this.source=source;
	}
	
	public Frame getDest()        {
		return dest;
	}
	
	public void setDest(Frame dest)  {
		
		this.dest=dest;
	}

	public Boolean isActivate() {
		return activate;
	}
	
	public void desactivate() {
		activate=false;
	}

	public void activate() {
		activate=true;
		
	}


}
