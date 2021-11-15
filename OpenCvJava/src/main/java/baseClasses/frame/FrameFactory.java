package baseClasses.frame;

import org.opencv.core.Core;

public class FrameFactory {
	static LibraryOption libraryOption;
	static Boolean isInitialized = false;
	
	@SuppressWarnings("static-access")
	public FrameFactory(){
		if (!isInitialized) {
			this.libraryOption=libraryOption.DEFAULT;
			initializeLibrary();
		}	
	}
	
	@SuppressWarnings("static-access")
	public FrameFactory(LibraryOption libraryOption){
		if (!isInitialized) {
			this.libraryOption=libraryOption;
			initializeLibrary();
		}	
	}

	public Frame create() {
		switch (libraryOption) {
		case DEFAULT : 	
			return new defaultFrame();
		case OPENCV:
			return new CvFrame();	
		}
		return null;
	}
	
	@SuppressWarnings("incomplete-switch")
	private void initializeLibrary() {
		switch (libraryOption) {	
		case OPENCV:
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			isInitialized=true;
		}
	}

}
