package com.opencvtester.controller.layer;

import java.util.Stack;

import com.opencvtester.controller.MainController;
import com.opencvtester.controller.filter.FilterManager;
import com.opencvtester.entity.Command;
import com.opencvtester.entity.LayerData;
import com.opencvtester.gui.LayerWidget;
import com.opencvtester.gui.LayerWindow;
import com.opencvtester.renderer.Layer;


public class LayerController extends Command
{
	protected Stack<FilterManager> chainOfFilterManager;
	protected Layer layer;
	protected LayerWidget layerWidget;
	protected LayerWindow layerWindow;
	protected MainController guiManager;
	protected String indexType;
	private LayerData layerData;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayerController(Layer layer, MainController guiManager) {
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
