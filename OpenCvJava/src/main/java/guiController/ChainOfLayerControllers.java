package guiController;

import baseClasses.Id;
import baseClasses.chain.ChainOfCommands;
import baseClasses.filter.Filter;
import gui.MainWindow;
import gui.LayerWidget;

public class ChainOfLayerControllers {
	
	private ChainOfCommands chainOfLayersControllers;
	private MainWindow gui;
	
	public ChainOfLayerControllers (MainWindow gui){
		
		Id chainId = new Id();
		chainId.set(0,0,1);
		chainOfLayersControllers= new ChainOfCommands(chainId);
		this.gui=gui;
	}
	
	public void addFilterWigetInLayerWiget(FilterController filterController) {
		((LayerController)chainOfLayersControllers.getCommand(filterController.getId().get()[0])).addFilterController(filterController);
		gui.updateGui();
	}
	
	public void deFilterWidgetInLayerWidget(FilterController filterWidget) {
		chainOfLayersControllers.delCommand(filterWidget.getId());
		gui.updateGui();
	}

	public void addLayerController(LayerController layerController) {
		chainOfLayersControllers.addCommand(layerController.getId(), layerController);	
		gui.updateGui();
	}

	public void deleteLayerController(LayerController layerWidget) {
		chainOfLayersControllers.delCommand(layerWidget.getId());	
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
			((LayerController)chainOfLayersControllers.getCommand(i)).updateGui();
		}
		
	}

	
	public LayerController getLayerController (int i) {
		
		return (LayerController)chainOfLayersControllers.getCommand(i);
	}

}
