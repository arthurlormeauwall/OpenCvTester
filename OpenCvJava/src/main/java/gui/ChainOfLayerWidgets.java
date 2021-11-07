package gui;

import baseClasses.chain.ChainOfCommands;
import baseClasses.filter.Filter;

public class ChainOfLayerWidgets {
	
	private ChainOfCommands chainOfLayersWidget;
	private Gui gui;
	
	public ChainOfLayerWidgets (Gui gui){
		this.gui=gui;
	}
	
	public void addFilterWigetInLayerWiget(FilterController filterWidget) {
		chainOfLayersWidget.addCommand(filterWidget.getId(), filterWidget);
		gui.addFilterWidgetInLayerWidget(filterWidget);
	}
	public void deFilterWidgetInLayerWidget(FilterController filterWidget) {
		chainOfLayersWidget.delCommand(filterWidget.getId());
		gui.delFilterWidgetInLayerWidget(filterWidget);
	}

	public void addLayerWidget(LayerController layerWidget) {
		chainOfLayersWidget.addCommand(layerWidget.getId(), layerWidget);	
		gui.addLayerWidget(layerWidget);
	}

	public void delLayerWidget(LayerController layerWidget) {
		chainOfLayersWidget.delCommand(layerWidget.getId());	
		gui.delLayerWidget(layerWidget);
	}

	public void setOpacity(int layerIndex, int opacity) {
		gui.updateOpacityValue(layerIndex,opacity);
	}


	public void setParameters(Filter filter) {
		gui.updateParametersValues(filter);
		
	}

}
