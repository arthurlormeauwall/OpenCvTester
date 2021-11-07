package gui;

import baseClasses.Command;
import baseClasses.Id;
import baseClasses.chain.ChainOfCommands;
import filtersDataBase.FiltersDataBase;
import renderingEngine.Layer;


public class LayerWidget extends Command
{
	protected ChainOfCommands chainOfFiltersWidget;
	protected Layer layer;
	
	public FilterWidget deleteFilterWidget(Id id) {
		return (FilterWidget)chainOfFiltersWidget.delCommand(id);
	}

	public FilterWidget addFilterWidget(int filterIndex, String filterName, FiltersDataBase filterDataBase) {
		FilterWidget filterWidget = new FilterWidget(filterDataBase.getFilter(filterName));	
		chainOfFiltersWidget.addCommand(filterWidget.getId(), filterWidget);
		return filterWidget;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	public Layer getLayer() {
		return layer;
	}
	
	

}
