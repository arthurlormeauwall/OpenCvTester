package baseClasses.filter;


import baseClasses.Command;
import baseClasses.Executable;
import baseClasses.Id;
import baseClasses.IoFrame;
import baseClasses.frame.FrameCv;



public abstract class Filter extends Command implements IoFrame, Executable
{
	protected FrameCv source;
	protected FrameCv dest;
	protected Boolean activate;
	
	public Filter() {
		activate= false;
		source = new FrameCv ();
		dest   = new FrameCv ();
	}
	
	public Filter(Id id) {
		super (id);
		activate= false;
		source = new FrameCv ();
		dest   = new FrameCv ();
	}

	public FrameCv getSource(){
		return source;
	}
	
	public void setSource(FrameCv source){
		this.source=source;
	}
	
	public FrameCv getDest()        {
		return dest;
	}
	
	public void setDest(FrameCv dest)  {	
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
