package com.opencvtester.baseClasses.filter;

import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.Executable;
import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.frame.FrameInterface;
import com.opencvtester.baseClasses.frame.FrameFactory;


public abstract class Filter extends Command implements Executable
{
	protected FrameInterface source;
	protected FrameInterface dest;
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

	public FrameInterface getSource(){
		return source;
	}
	
	public void setSource(FrameInterface source){
		this.source=source;
	}
	
	public FrameInterface getDest()        {
		return dest;
	}
	
	public void setDest(FrameInterface dest)  {	
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
