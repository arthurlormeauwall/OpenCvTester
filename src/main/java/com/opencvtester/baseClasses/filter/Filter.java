package com.opencvtester.baseClasses.filter;

import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.frame.Frame;
import com.opencvtester.baseClasses.frame.FrameInterface;


public abstract class Filter extends Command
{
	protected FrameInterface source;
	protected FrameInterface dest;
	protected Boolean activate;
	
	public Filter() {
		activate= false;
		source = new Frame();
		dest   = new Frame();
	}
	
	public Filter(Id id) {
		super (id);
		activate= false;
		source = new Frame();
		dest   = new Frame();
	}
	
	public abstract void execute();

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
