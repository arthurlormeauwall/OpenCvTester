package com.opencvtester.guiManager;

import com.opencvtester.baseClasses.ChainOfCommands;
import com.opencvtester.baseClasses.Command;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.gui.LayerWidget;
import com.opencvtester.gui.LayerWindow;
import com.opencvtester.renderingEngine.Layer;


public class LayerManager extends Command
{
	protected ChainOfCommands chainOfFilterManager;
	protected Layer layer;
	protected LayerWidget layerWidget;
	protected LayerWindow layerWindow;
	protected GuiManager guiManager;
	protected String groupID;
	
	public LayerManager(Layer layer, GuiManager guiManager) {
		groupID="filter";
		this.layer=layer;
		this.guiManager=guiManager;
		this.id.set(layer);
		chainOfFilterManager=new ChainOfCommands();
		layerWidget=new LayerWidget(this, this.guiManager);
		layerWindow=new LayerWindow(this, this.guiManager);		
	}
	
	public FilterManager getFilterManager(int index) {
		return  (FilterManager)chainOfFilterManager.getCommand(index);
	}
	
	public FilterManager deleteFilterWidget(Command command) {
		return (FilterManager)chainOfFilterManager.delCommand(command,indexType());
	}

	public FilterManager addFilterManager(FilterManager newFilterManager) {
		chainOfFilterManager.addCommand(newFilterManager,indexType());
		return newFilterManager;
	}
	public FilterManager addFilterManager(int filterIndex, String filterName, FiltersDataBase filterDataBase) {
		FilterManager filterManager = new FilterManager(filterDataBase.getFilter(filterName), guiManager);	
		chainOfFilterManager.addCommand(filterManager,indexType());
		return filterManager;
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
	
	public String indexType() {
		return groupID;
	}	
}
