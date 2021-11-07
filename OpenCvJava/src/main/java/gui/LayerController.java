package gui;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.chain.ChainOfCommands;
import filtersDataBase.FiltersDataBase;
import gui.widget.LayerWidget;
import renderingEngine.Layer;


public class LayerController extends Command
{
	protected ChainOfCommands chainOfFiltersWidget;
	protected Layer layer;
	protected LayerWidget layerWidget;
	
	public FilterController deleteFilterWidget(Id id) {
		return (FilterController)chainOfFiltersWidget.delCommand(id);
	}

	public FilterController addFilterWidget(int filterIndex, String filterName, FiltersDataBase filterDataBase) {
		FilterController filterWidget = new FilterController(filterDataBase.getFilter(filterName));	
		chainOfFiltersWidget.addCommand(filterWidget.getId(), filterWidget);
		return filterWidget;
	}


	public Layer getLayer() {
		return layer;
	}
	
	

}
