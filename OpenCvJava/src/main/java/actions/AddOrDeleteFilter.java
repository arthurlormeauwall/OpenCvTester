package actions;


import guiManager.ChainOfLayerManagers;
import guiManager.FilterManager;
import renderingEngine.ChainOfLayers;

public class AddOrDeleteFilter implements Action {

	public Functionalities addOrDelete;
	private FilterManager filterWidget;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerManagers chainOfLayerWidgets;
	
	public AddOrDeleteFilter(ChainOfLayers chainOfLayers, ChainOfLayerManagers chainOfLayerWidgets, FilterManager filterWidget){
		this.filterWidget=filterWidget;
		this.chainOfLayers=chainOfLayers;
		this.chainOfLayerWidgets=chainOfLayerWidgets;
	}

	public void invert() {
		if (addOrDelete== Functionalities.ADD) {
			addOrDelete=Functionalities.DELETE;
		}
		else if (addOrDelete== Functionalities.DELETE) {
			addOrDelete=Functionalities.ADD;
		}
	}

	public void execute() {
		if (addOrDelete== Functionalities.ADD) {
			chainOfLayers.addFilterInLayer(filterWidget.getFilter());
			chainOfLayerWidgets.addFilterWigetInLayerWiget(filterWidget);
		}
		else if (addOrDelete== Functionalities.DELETE) {
			chainOfLayers.delFilterInLayer(filterWidget.getFilter());
			chainOfLayerWidgets.deFilterWidgetInLayerWidget(filterWidget);
		}	
	}

}
