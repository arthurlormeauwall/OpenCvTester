package baseClasses.filter;


import baseClasses.Command;
import baseClasses.Executable;
import baseClasses.Id;
import baseClasses.frame.Frame;
import baseClasses.frame.FrameFactory;



public abstract class Filter extends Command implements Executable
{
	protected Frame source;
	protected Frame dest;
	protected Boolean activate;
	private FrameFactory frameFactory;
	
	public Filter() {
		frameFactory=new FrameFactory();
		activate= false;
		source = frameFactory.create();
		dest   = frameFactory.create();
	}
	
	public Filter(Id id) {
		super (id);
		frameFactory=new FrameFactory();
		activate= false;
		source = frameFactory.create ();
		dest   = frameFactory.create ();
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
