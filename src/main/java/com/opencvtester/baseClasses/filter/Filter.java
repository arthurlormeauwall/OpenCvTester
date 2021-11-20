package com.opencvtester.baseClasses.filter;

import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.Executable;
import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.frame.Frame;
import com.opencvtester.baseClasses.frame.FrameFactory;


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
