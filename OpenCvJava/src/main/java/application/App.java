package application;

import renderingEngine.GroupsId;
import renderingEngine.ChainOfLayers;

import org.opencv.core.Core;

import baseClasses.Id;
import baseClasses.history.IdHistory;
import baseClasses.openCvFacade.Frame;
import filtersDataBase.FiltersDataBase;
import gui.UIImp;


public class App 
{
	protected IdHistory<Id> undoIdHistory;
	protected IdHistory<Id> renderAtIdHistory;
	protected Frame source;
	protected Frame background;
	protected Frame dest;
	
	protected ChainOfLayers chainOfLayers;
	protected UIImp mainWinow;
	
	public App() {	
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		undoIdHistory = new IdHistory<Id>();
		renderAtIdHistory = new IdHistory<Id>();
		background = new Frame();
		dest = new Frame();
		source = new Frame();	
	}
	
	public void init(String fileName) {
		
		setImage(fileName);
		background.createPlainGrayFrame(source.getMat().rows(), source.getMat().cols(), 0);
		source.copyTo(dest);
		
		Id rendererId = new Id();
		rendererId.initNULL();
		rendererId.setGroupId(GroupsId.RENDERER.ordinal());
	
		chainOfLayers = new ChainOfLayers(new FiltersDataBase(), background, rendererId, undoIdHistory, renderAtIdHistory);

		chainOfLayers.setSource(source);
		chainOfLayers.setDest(dest);
		chainOfLayers.play();
		mainWinow= new UIImp(chainOfLayers);	
	}
	
	private void setImage(String fileName) {
		source.readFromFile(fileName);
	}
	
	public UIImp getMainWin() {
		return mainWinow;
	}
}
