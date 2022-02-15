package com.opencvtester.renderer;

import com.opencvtester.data.Command;
import com.opencvtester.renderer.interfaces.FrameInterface;

public abstract class IOFrame extends Command
{
	
	protected FrameInterface frameIn;
	protected FrameInterface frameOut;
	protected Boolean activate;
	
	/*
	 * CONSTRUCTOR & INITS & ABSTRACT
	 */
	public IOFrame() {
		activate= false;
		frameIn = new Frame();
		frameOut   = new Frame();
	}
	
	public abstract void execute();
	
	/*
	 * GETTERS & SETTERS
	 */
	public FrameInterface getFrameIn(){
		return frameIn;
	}
	
	public void setFrameIn(FrameInterface frameIn){
		this.frameIn=frameIn;
	}
	
	public FrameInterface getFrameOut()        {
		return frameOut;
	}
	
	public void setFrameOut(FrameInterface frameOut)  {	
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
