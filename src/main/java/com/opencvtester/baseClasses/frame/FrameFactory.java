package com.opencvtester.baseClasses.frame;

import java.util.HashMap;

public class FrameFactory {
	static String libraryOption;
	static HashMap <String, FrameInterface> frameType=new HashMap <String, FrameInterface>();
	static Boolean hasBeenInit=false;
	
	public FrameFactory(){
		frameType.put("Default", new Frame());
	}
	
	@SuppressWarnings("static-access")
	public void setFrameType(String libraryOption) {
		if (!hasBeenInit) {
			this.libraryOption=libraryOption;
			hasBeenInit=true;
		}
	}
	
	public void putNewFrameType(String libraryOption, FrameInterface newType) {
		FrameFactory.frameType.put(libraryOption, newType);
	}

	public FrameInterface create() {
		if (libraryOption==null) {
			return FrameFactory.frameType.get("Default").create();
		}
		else {
			return FrameFactory.frameType.get(libraryOption).create();
		}
	}
}
