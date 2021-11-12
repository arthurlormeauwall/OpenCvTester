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
	protected GuiManager guiManager;
	
	public LayerController(Layer layer, GuiManager guiManager) {
		this.layer=layer;
		this.guiManager=guiManager;
		this.id.set(layer.getId());
		chainOfFilterControllers=new ChainOfCommands(layer.getId());
		layerWidget=new LayerWidget(this.layer, this.guiManager);
		layerWindow=new LayerWindow(this, this.guiManager);
	}
	
	public FilterController getFilterController(int index) {
		return  (FilterController)chainOfFilterControllers.getCommand(index);
	}
	public FilterController deleteFilterWidget(Id id) {
		return (FilterController)chainOfFilterControllers.delCommand(id);
	}

	public FilterController addFilterController(FilterController newFilterController) {
		chainOfFilterControllers.addCommand(newFilterController.getId(), newFilterController);
		return newFilterController;
	}
	public FilterController addFilterController(int filterIndex, String filterName, FiltersDataBase filterDataBase) {
		FilterController filterController = new FilterController(filterDataBase.getFilter(filterName));	
		chainOfFilterControllers.addCommand(filterController.getId(), filterController);
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
}
