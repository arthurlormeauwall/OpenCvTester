package actions;


import gui.ChainOfLayerControllers;
import gui.FilterController;
import renderingEngine.ChainOfLayers;

public class AddOrDeleteFilter implements Action {

	public Functionalities addOrDelete;
	private FilterController filterWidget;
	private ChainOfLayers chainOfLayers;
	private ChainOfLayerControllers chainOfLayerWidgets;
	
	public AddOrDeleteFilter(ChainOfLayers chainOfLayers, ChainOfLayerControllers chainOfLayerWidgets, FilterController filterWidget){
		this.filterWidget=filterWidget;
		this.chainOfLayers=chainOfLayers;
		this.chainOfLayerWidgets=chainOfLayerWidgets;
	}

	public void invert() {
		if (addOrDelete== Functionalities.ADD_FILTER) {
			addOrDelete=Functionalities.DELETE_FILTER;
		}
		else if (addOrDelete== Functionalities.DELETE_FILTER) {
			addOrDelete=Functionalities.ADD_FILTER;
		}
	}

	public void execute() {
		if (addOrDelete== Functionalities.ADD_FILTER) {
			chainOfLayers.addFilterInLayer(filterWidget.getFilter());
			chainOfLayerWidgets.addFilterWigetInLayerWiget(filterWidget);
		}
		else if (addOrDelete== Functionalities.DELETE_FILTER) {
			chainOfLayers.delFilterInLayer(filterWidget.getFilter());
			chainOfLayerWidgets.deFilterWidgetInLayerWidget(filterWidget);
		}	
	}

}
