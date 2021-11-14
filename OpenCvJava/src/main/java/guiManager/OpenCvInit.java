package guiManager;

import renderingEngine.ChainOfLayers;

import java.io.IOException;

import org.opencv.core.Core;

import baseClasses.Id;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;

public class OpenCvInit 
{
	protected Frame source;
	protected Frame background;
	protected Frame dest;
	protected ChainOfLayers chainOfLayers;	
	
	public OpenCvInit() {	
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		background = new Frame();
		dest = new Frame();
		source = new Frame();		
	}
	
	public ChainOfLayers init(String fileName) throws IOException {	
		setImage(fileName);
		background.createPlainGrayFrame(source.getMat().rows(), source.getMat().cols(), 0);
		source.copyTo(dest);
		
		Id chainOfLayersId = new Id();
		chainOfLayersId.initNULL();
		
		chainOfLayers = new ChainOfLayers(new FiltersDataBase(), background, chainOfLayersId);
		chainOfLayers.setSource(source);
		chainOfLayers.setDest(dest);
		
		return chainOfLayers;	
	}
	
	private void setImage(String fileName) {
		source.readFromFile(fileName);
	}

	public ChainOfLayers getChainOfLayers() {
		return chainOfLayers;
	}


}
