package com.opencvtester.baseClasses.frame;

import java.util.HashMap;

public class FrameFactory {
	static String libraryOption;
	static HashMap <String, Frame> frameType=new HashMap <String, Frame>();
	static Boolean hasBeenInit=false;
	
	public FrameFactory(){
		frameType.put("Default", new DefaultFrame());
	}
	
	@SuppressWarnings("static-access")
	public void setFrameType(String libraryOption) {
		if (!hasBeenInit) {
			this.libraryOption=libraryOption;
			hasBeenInit=true;
		}
	}
	
	public void putNewFrameType(String libraryOption, Frame newType) {
		FrameFactory.frameType.put(libraryOption, newType);
	}

	public Frame create() {
		if (libraryOption==null) {
			return FrameFactory.frameType.get("Default").create();
		}
		else {
			return FrameFactory.frameType.get(libraryOption).create();
		}
	}
}
