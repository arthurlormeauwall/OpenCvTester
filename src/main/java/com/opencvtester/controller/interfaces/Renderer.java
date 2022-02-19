package com.opencvtester.controller.interfaces;

import javax.xml.crypto.Data;

import com.opencvtester.renderer.Frame;
import com.opencvtester.renderer.interfaces.FrameInterface;

public abstract class Renderer implements DataProvider
{
	
	protected FrameInterface frameIn;
	protected FrameInterface frameOut;
	protected Boolean isActivate;
	

	/*
	 * CONSTRUCTOR & INITS & ABSTRACT
	 */
	public Renderer() {
		frameIn = new Frame();
		frameOut   = new Frame();
	}
	
	public abstract void render();
	
	public FrameInterface getFrameIn(){
		return frameIn;
	}
	
	public void setFrameIn(FrameInterface frameIn){
		this.frameIn=frameIn;
	}
	
	public void setFrameIn(String fileName){
		try {
			this.frameIn.readFromFile(fileName);
			frameIn.setSpecs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FrameInterface getFrameOut()        {
		return frameOut;
	}
	
	public void setFrameOut(FrameInterface frameOut)  {	
		this.frameOut=frameOut;
	}

	public abstract void openImage(String fileName);

}
