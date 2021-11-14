package guiManager;

import baseClasses.Id;
import baseClasses.chain.ChainOfCommands;
import baseClasses.filter.Filter;
import gui.MainWindow;

public class ChainOfLayersManager {
	
	private ChainOfCommands chainOfLayersManager;
	private MainWindow gui;
	protected GroupsId groupID;
	
	public ChainOfLayersManager (MainWindow gui){
		
		Id chainId = new Id();
		chainId.set(0,0);
		chainOfLayersManager= new ChainOfCommands(chainId);
		this.gui=gui;
		groupID=GroupsId.LAYER;
	}
	
	public void addFilterWigetInLayerWiget(FilterManager filterController) {
		((LayerManager)chainOfLayersManager.getCommand(filterController.getId().get()[0])).addFilterController(filterController);
		gui.updateGui();
	}
	
	public void deFilterWidgetInLayerWidget(FilterManager filterManager) {
		((LayerManager)chainOfLayersManager.getCommand(filterManager.getId().get()[0])).deleteFilterWidget(filterManager.getId());
		gui.updateGui();
	}

	public void addLayerManager(LayerManager layerController) {
		chainOfLayersManager.addCommand(layerController.getId(), layerController, indexType());	
		gui.updateGui();
	}

	public void deleteLayerManager(LayerManager layerWidget) {
		chainOfLayersManager.delCommand(layerWidget.getId(), indexType());	
		gui.updateGui();
	}

	public void setOpacity(int layerIndex, Float opacity) {
		gui.updateOpacityValue(layerIndex,opacity);
	}


	public void setParameters(Filter filter) {
		gui.updateParametersValues(filter);
		
	}
	
	public int getNumberOfLayer(){
		return chainOfLayersManager.getSize();
	}

	public void updateGui() {
		for (int i=0;i<getNumberOfLayer();i++) {
			((LayerManager)chainOfLayersManager.getCommand(i)).updateGui();
		}
		
	}

	
	public LayerManager getLayerController (int i) {
		
		return (LayerManager)chainOfLayersManager.getCommand(i);
	}
	
	public int indexType() {
		return groupID.ordinal();
	}

}
