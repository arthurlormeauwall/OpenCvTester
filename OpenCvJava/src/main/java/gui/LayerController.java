package gui;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.chain.ChainOfCommands;
import filtersDataBase.FiltersDataBase;
import gui.widget.LayerWidget;
import gui.widget.LayerWindow;
import renderingEngine.Layer;


public class LayerController extends Command
{
	protected ChainOfCommands chainOfFilterControllers;
	protected Layer layer;
	protected LayerWidget layerWidget;
	protected LayerWindow layerWindow;
	
	public FilterController deleteFilterWidget(Id id) {
		return (FilterController)chainOfFilterControllers.delCommand(id);
	}

	public FilterController addFilterController(int filterIndex, String filterName, FiltersDataBase filterDataBase) {
		FilterController filterController = new FilterController(filterDataBase.getFilter(filterName));	
		chainOfFilterControllers.addCommand(filterController.getId(), filterController);
		return filterController;
	}


	public Layer getLayer() {
		return layer;
	}
}
