package com.opencvtester.gui.controller;

import java.util.ArrayList;
import java.util.List;

import com.opencvtester.controller.MainController;
import com.opencvtester.data.interfaces.LayerDataInterface;
import com.opencvtester.gui.LayerWidget;
import com.opencvtester.gui.LayerWindow;
import com.opencvtester.renderer.ControlledFilter;
import com.opencvtester.renderer.Layer;


public class LayerController
{
	protected List<FilterController> chainOfFilterController;
	protected Layer layer;
	protected LayerWidget layerWidget;
	protected LayerWindow layerWindow;
	protected MainController mainController;
	protected String indexType;
	private  LayerDataInterface layerData;
	ArrayList<ControlledFilter> filters;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayerController(ArrayList<ControlledFilter> filters, LayerDataInterface layerData, MainController mainController) {
		chainOfFilterController = new ArrayList<FilterController>();
		this.filters=filters;
		this.layerData=layerData;
		
		this.mainController=mainController;
		
		layer = mainController.layers().get(layerData.layerIndex());
		
		indexType="filter";

		layerWidget=new LayerWidget(this, this.mainController);
		layerWindow=new LayerWindow(this, this.mainController);		
	}

	public int layerIndex() {
		return layerData.layerIndex();
	}
	
	public FilterController getFilterManager(int index) {
		return  (FilterController)chainOfFilterController.get(index);
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

	public FilterController deleteFilterWidget(int filterIndex) {
		return (FilterController)chainOfFilterController.remove(filterIndex);
	}

	public FilterController addFilterManager(FilterController newFilterManager) {
		chainOfFilterController.add(newFilterManager);
		newFilterManager.getFilterWidget().setVisible(true);
		return newFilterManager;
	}
	
	public void deleteLayerWindow() {
		layerWindow.setVisible(false);
		layerWindow=null;
	}
	
	public void createLayerWindow() {
		layerWindow=new LayerWindow(this, this.mainController);
		layerWindow.setVisible(false);
	}

}
