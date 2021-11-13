package guiManager;

import baseClasses.Id;
import baseClasses.chain.ChainOfCommands;
import baseClasses.filter.Filter;
import gui.MainWindow;

public class ChainOfLayersManager {
	
	private ChainOfCommands chainOfLayersControllers;
	private MainWindow gui;
	
	public ChainOfLayersManager (MainWindow gui){
		
		Id chainId = new Id();
		chainId.set(0,0);
		chainOfLayersControllers= new ChainOfCommands(chainId);
		this.gui=gui;
	}
	
	public void addFilterWigetInLayerWiget(FilterManager filterController) {
		((LayerManager)chainOfLayersControllers.getCommand(filterController.getId().get()[0])).addFilterController(filterController);
		gui.updateGui();
	}
	
	public void deFilterWidgetInLayerWidget(FilterManager filterWidget) {
		chainOfLayersControllers.delCommand(filterWidget.getId(),groupDeepnessIndes());
		gui.updateGui();
	}

	public void addLayerController(LayerManager layerController) {
		chainOfLayersControllers.addCommand(layerController.getId(), layerController, groupDeepnessIndes());	
		gui.updateGui();
	}

	public void deleteLayerController(LayerManager layerWidget) {
		chainOfLayersControllers.delCommand(layerWidget.getId(), groupDeepnessIndes());	
		gui.updateGui();
	}

	public void setOpacity(int layerIndex, Float opacity) {
		gui.updateOpacityValue(layerIndex,opacity);
	}


	public void setParameters(Filter filter) {
		gui.updateParametersValues(filter);
		
	}
	
	public int getNumberOfLayer(){
		return chainOfLayersControllers.getSize();
	}

	public void updateGui() {
		for (int i=0;i<getNumberOfLayer();i++) {
			((LayerManager)chainOfLayersControllers.getCommand(i)).updateGui();
		}
		
	}

	
	public LayerManager getLayerController (int i) {
		
		return (LayerManager)chainOfLayersControllers.getCommand(i);
	}
	
	public int groupDeepnessIndes() {
		return 0;
	}

}
