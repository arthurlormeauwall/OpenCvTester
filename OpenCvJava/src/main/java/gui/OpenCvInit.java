package gui;

import renderingEngine.GroupsId;
import renderingEngine.ChainOfLayers;

import org.opencv.core.Core;

import baseClasses.Id;
import baseClasses.history.IdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;

public class OpenCvInit 
{
	protected IdHistory<Id> renderAtIdHistory;
	protected Frame source;
	protected Frame background;
	protected Frame dest;
	
	protected ChainOfLayers chainOfLayers;
	
	
	public OpenCvInit() {	
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		renderAtIdHistory = new IdHistory<Id>();
		background = new Frame();
		dest = new Frame();
		source = new Frame();	
		
	}
	
	public ChainOfLayers init(String fileName) {
		
		setImage(fileName);
		background.createPlainGrayFrame(source.getMat().rows(), source.getMat().cols(), 0);
		source.copyTo(dest);
		
		Id chainOfLayersId = new Id();
		chainOfLayersId.initNULL();
		chainOfLayersId.setGroupId(GroupsId.RENDERER.ordinal());
	
		chainOfLayers = new ChainOfLayers(new FiltersDataBase(), background, chainOfLayersId, renderAtIdHistory);

		chainOfLayers.setSource(source);
		chainOfLayers.setDest(dest);
		chainOfLayers.play();
		return chainOfLayers;
		
	}
	
	private void setImage(String fileName) {
		source.readFromFile(fileName);
	}

	public ChainOfLayers getChainOfLayers() {
		return chainOfLayers;
	}


}
