package com.opencvtester.guiManager;

import com.opencvtester.baseClasses.ChainOfCommands;
import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.Id;
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
	protected GroupsId groupID;
	
	public LayerManager(Layer layer, GuiManager guiManager) {
		groupID=GroupsId.FILTER;
		this.layer=layer;
		this.guiManager=guiManager;
		this.id.set(layer.getId());
		chainOfFilterManager=new ChainOfCommands(layer.getId());
		layerWidget=new LayerWidget(this, this.guiManager);
		layerWindow=new LayerWindow(this, this.guiManager);		
	}
	
	public FilterManager getFilterManager(int index) {
		return  (FilterManager)chainOfFilterManager.getCommand(index);
	}
	public FilterManager deleteFilterWidget(Id id) {
		return (FilterManager)chainOfFilterManager.delCommand(id,indexType());
	}

	public FilterManager addFilterManager(FilterManager newFilterManager) {
		chainOfFilterManager.addCommand(newFilterManager.getId(), newFilterManager,indexType());
		return newFilterManager;
	}
	public FilterManager addFilterManager(int filterIndex, String filterName, FiltersDataBase filterDataBase) {
		FilterManager filterManager = new FilterManager(filterDataBase.getFilter(filterName), guiManager);	
		chainOfFilterManager.addCommand(filterManager.getId(), filterManager,indexType());
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
	
	public int indexType() {
		return groupID.ordinal();
	}	
}