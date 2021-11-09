package gui;

import baseClasses.chain.ChainOfCommands;
import baseClasses.filter.Filter;

public class ChainOfLayerControllers {
	
	private ChainOfCommands chainOfLayersControllers;
	private Gui gui;
	
	public ChainOfLayerControllers (Gui gui){
		this.gui=gui;
	}
	
	public void addFilterWigetInLayerWiget(FilterController filterWidget) {
		chainOfLayersControllers.addCommand(filterWidget.getId(), filterWidget);
		gui.addFilterWidgetInLayerWidget(filterWidget);
	}
	public void deFilterWidgetInLayerWidget(FilterController filterWidget) {
		chainOfLayersControllers.delCommand(filterWidget.getId());
		gui.delFilterWidgetInLayerWidget(filterWidget);
	}

	public void addLayerWidget(LayerController layerWidget) {
		chainOfLayersControllers.addCommand(layerWidget.getId(), layerWidget);	
		gui.addLayerWidget(layerWidget);
	}

	public void delLayerWidget(LayerController layerWidget) {
		chainOfLayersControllers.delCommand(layerWidget.getId());	
		gui.delLayerWidget(layerWidget);
	}

	public void setOpacity(int layerIndex, int opacity) {
		gui.updateOpacityValue(layerIndex,opacity);
	}


	public void setParameters(Filter filter) {
		gui.updateParametersValues(filter);
		
	}

}
