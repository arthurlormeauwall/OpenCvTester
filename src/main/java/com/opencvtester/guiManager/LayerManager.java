package com.opencvtester.guiManager;

import java.util.Stack;

import com.opencvtester.baseClasses.Command;
import com.opencvtester.dataAccess.LayerData;
import com.opencvtester.gui.LayerWidget;
import com.opencvtester.gui.LayerWindow;
import com.opencvtester.renderingEngine.Layer;


public class LayerManager extends Command
{
	protected Stack<FilterManager> chainOfFilterManager;
	protected Layer layer;
	protected LayerWidget layerWidget;
	protected LayerWindow layerWindow;
	protected GuiManager guiManager;
	protected String indexType;
	private LayerData layerData;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayerManager(Layer layer, GuiManager guiManager) {
		this.layer=layer;
		this.guiManager=guiManager;
		setLayerIndex(layer.layerIndex());
		this.layerData=layer.getData();
		
		indexType="filter";
		chainOfFilterManager=new Stack<FilterManager>();
		layerWidget=new LayerWidget(this, this.guiManager);
		layerWindow=new LayerWindow(this, this.guiManager);		
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public FilterManager getFilterManager(int index) {
		return  (FilterManager)chainOfFilterManager.get(index);
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
		return (FilterManager)chainOfFilterManager.remove(command.getIndex(indexType));
	}

	public FilterManager addFilterManager(FilterManager newFilterManager) {
		chainOfFilterManager.add(newFilterManager);
		newFilterManager.getFilterWidget().setVisible(true);
		return newFilterManager;
	}
	
	public void deleteLayerWindow() {
		layerWindow.setVisible(false);
		layerWindow=null;
	}
	
	public void createLayerWindow() {
		layerWindow=new LayerWindow(this, this.guiManager);
		layerWindow.setVisible(false);
	}

	public LayerData getData() {
		return layerData;
	}
}
