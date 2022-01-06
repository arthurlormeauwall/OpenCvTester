package com.opencvtester.baseClasses.filter;

import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.frame.Frame;
import com.opencvtester.baseClasses.frame.FrameInterface;


public abstract class Filter extends Command
{
	protected FrameInterface frameIn;
	protected FrameInterface frameOut;
	protected Boolean activate;
	
	public Filter() {
		activate= false;
		frameIn = new Frame();
		frameOut   = new Frame();
	}
	
	public Filter(Id id) {
		super (id);
		activate= false;
		frameIn = new Frame();
		frameOut   = new Frame();
	}
	
	public abstract void execute();

	public FrameInterface getFrameIn(){
		return frameIn;
	}
	
	public void setSource(FrameInterface frameIn){
		this.frameIn=frameIn;
	}
	
	public FrameInterface getFrameOut()        {
		return frameOut;
	}
	
	public void setDest(FrameInterface frameOut)  {	
		this.frameOut=frameOut;
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
