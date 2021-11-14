package guiManager;

import baseClasses.ChainOfCommands;
import baseClasses.Id;
import baseClasses.filter.Filter;
import gui.MainWindow;

public class ChainOfLayerManagers {
	
	private ChainOfCommands layerManagers;
	private MainWindow gui;
	protected GroupsId groupID;
	
	public ChainOfLayerManagers (MainWindow gui){	
		Id chainId = new Id();
		chainId.set(0,0);
		layerManagers= new ChainOfCommands(chainId);
		this.gui=gui;
		groupID=GroupsId.LAYER;
	}
	
	public void addFilterWigetInLayerWiget(FilterManager filterController) {
		((LayerManager)layerManagers.getCommand(filterController.getId().get()[0])).addFilterController(filterController);
		gui.updateGui();
	}
	
	public void deFilterWidgetInLayerWidget(FilterManager filterManager) {
		((LayerManager)layerManagers.getCommand(filterManager.getId().get()[0])).deleteFilterWidget(filterManager.getId());
		gui.updateGui();
	}

	public void addLayerManager(LayerManager layerController) {
		layerManagers.addCommand(layerController.getId(), layerController, indexType());	
		gui.updateGui();
	}

	public void deleteLayerManager(LayerManager layerWidget) {
		layerManagers.delCommand(layerWidget.getId(), indexType());	
		gui.updateGui();
	}

	public void setOpacity(int layerIndex, Float opacity) {
		gui.updateOpacityValue(layerIndex,opacity);
	}

	public void setParameters(Filter filter) {
		gui.updateParametersValues(filter);
	}
	
	public int getNumberOfLayer(){
		return layerManagers.getSize();
	}

	public void updateGui() {
		for (int i=0;i<getNumberOfLayer();i++) {
			((LayerManager)layerManagers.getCommand(i)).updateGui();
		}	
	}

	
	public LayerManager getLayerController (int i) {
		
		return (LayerManager)layerManagers.getCommand(i);
	}
	
	public int indexType() {
		return groupID.ordinal();
	}

}
