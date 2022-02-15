package com.opencvtester.controller.layer;

import java.util.Stack;

import com.opencvtester.controller.MainController;
import com.opencvtester.data.Command;
import com.opencvtester.data.LayerData;
import com.opencvtester.data.LayerData;
import com.opencvtester.filterController.FilterController;
import com.opencvtester.gui.LayerWidget;
import com.opencvtester.gui.LayerWindow;


public class LayerController extends Command
{
	protected Stack<FilterController> chainOfFilterManager;
	protected LayerData layer;
	protected LayerWidget layerWidget;
	protected LayerWindow layerWindow;
	protected MainController guiManager;
	protected String indexType;
	private LayerData layerData;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayerController(LayerData layer, MainController guiManager) {
		this.layer=layer;
		this.guiManager=guiManager;
		setLayerIndex(layer.layerIndex());
		this.layerData=layer.getData();
		
		indexType="filter";
		chainOfFilterManager=new Stack<FilterController>();
		layerWidget=new LayerWidget(this, this.guiManager);
		layerWindow=new LayerWindow(this, this.guiManager);		
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public FilterController getFilterManager(int index) {
		return  (FilterController)chainOfFilterManager.get(index);
	}
	
	public void updateGui() {
		layerWindow.updateGui();	
	}
	
	public LayerData getLayer() {
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
	public FilterController deleteFilterWidget(Command command) {
		return (FilterController)chainOfFilterManager.remove(command.getIndex(indexType));
	}

	public FilterController addFilterManager(FilterController newFilterManager) {
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
