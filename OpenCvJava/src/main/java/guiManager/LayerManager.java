package guiManager;

import baseClasses.ChainOfCommands;
import baseClasses.Command;
import baseClasses.Id;
import filtersDataBase.FiltersDataBase;
import gui.LayerWidget;
import gui.LayerWindow;
import renderingEngine.Layer;


public class LayerManager extends Command
{
	protected ChainOfCommands chainOfFilterControllers;
	protected Layer layer;
	protected LayerWidget layerWidget;
	protected LayerWindow layerWindow;
	protected ActionHistoryManager guiManager;
	protected GroupsId groupID;
	
	public LayerManager(Layer layer, ActionHistoryManager guiManager) {
		groupID=GroupsId.FILTER;
		this.layer=layer;
		this.guiManager=guiManager;
		this.id.set(layer.getId());
		chainOfFilterControllers=new ChainOfCommands(layer.getId());
		layerWidget=new LayerWidget(this, this.guiManager);
		layerWindow=new LayerWindow(this, this.guiManager);		
	}
	
	public FilterManager getFilterController(int index) {
		return  (FilterManager)chainOfFilterControllers.getCommand(index);
	}
	public FilterManager deleteFilterWidget(Id id) {
		return (FilterManager)chainOfFilterControllers.delCommand(id,indexType());
	}

	public FilterManager addFilterController(FilterManager newFilterController) {
		chainOfFilterControllers.addCommand(newFilterController.getId(), newFilterController,indexType());
		return newFilterController;
	}
	public FilterManager addFilterController(int filterIndex, String filterName, FiltersDataBase filterDataBase) {
		FilterManager filterController = new FilterManager(filterDataBase.getFilter(filterName), guiManager);	
		chainOfFilterControllers.addCommand(filterController.getId(), filterController,indexType());
		return filterController;
	}

	public Layer getLayer() {
		return layer;
	}

	public void updateGui() {
		layerWindow.updateGui();	
	}

	public LayerWidget getLayerWidget() {
		return layerWidget;
	}
	
	public LayerWindow getLayerWindow() {
		return layerWindow;
	}
	
	public int indexType() {
		return groupID.ordinal();
	}	
}
