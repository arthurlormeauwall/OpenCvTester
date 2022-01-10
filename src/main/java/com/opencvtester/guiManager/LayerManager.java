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
	protected String indexType;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayerManager(Layer layer, GuiManager guiManager) {
		this.layer=layer;
		this.guiManager=guiManager;
		this.id.set(layer);
		
		indexType="filter";
		chainOfFilterManager=new ChainOfCommands(indexType);
		layerWidget=new LayerWidget(this, this.guiManager);
		layerWindow=new LayerWindow(this, this.guiManager);		
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public FilterManager getFilterManager(int index) {
		return  (FilterManager)chainOfFilterManager.getCommand(index);
	}
	
	public void updateGui() {
		layerWindow.updateGui();	
	}
	public Layer getLayer() {
		return layer;
	}

	public LayerWidget getLayerWidget() {
		return layerWidget;
	}
	
	public LayerWindow getLayerWindow() {
		return layerWindow;
	}
		
	/*
	 * FEATURES
	 */	
	public FilterManager deleteFilterWidget(Command command) {
		return (FilterManager)chainOfFilterManager.delCommand(command.getIndex(indexType));
	}

	public FilterManager addFilterManager(FilterManager newFilterManager) {
		chainOfFilterManager.addCommand(newFilterManager);
		return newFilterManager;
	}
	public FilterManager addFilterManager(int filterIndex, String filterName, FiltersDataBase filterDataBase) {
		FilterManager filterManager = new FilterManager(filterDataBase.getFilter(filterName), guiManager);	
		chainOfFilterManager.addCommand(filterManager);
		return filterManager;
	}
	
	public void deleteLayerWindow() {
		layerWindow.setVisible(false);
		layerWindow=null;
	}
	
	public void createLayerWindow() {
		layerWindow=new LayerWindow(this, this.guiManager);
		layerWindow.setVisible(false);
	}
}
