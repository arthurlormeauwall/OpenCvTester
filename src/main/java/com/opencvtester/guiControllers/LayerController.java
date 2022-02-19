package com.opencvtester.guiControllers;

import java.util.Stack;

import com.opencvtester.app.MainController;
import com.opencvtester.data.LayerDataInterface;
import com.opencvtester.gui.LayerWidget;
import com.opencvtester.gui.LayerWindow;
import com.opencvtester.renderer.ControlledFilter;
import com.opencvtester.renderer.Layer;


public class LayerController
{
	protected Stack<FilterController> chainOfFilterController;
	protected Layer layer;
	protected LayerWidget layerWidget;
	protected LayerWindow layerWindow;
	protected MainController mainController;
	protected String indexType;
	private  LayerDataInterface layerData;
	protected Stack<ControlledFilter> filters;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayerController(Stack<ControlledFilter> filters, LayerDataInterface layerData, MainController mainController) {
		chainOfFilterController = new Stack<FilterController>();
		for (int i=0;i<filters.size();i++) {
			addFilterController(new FilterController(filters.get(i), mainController));
		}
		
		this.filters=filters;
		this.layerData=layerData;
		
		this.mainController=mainController;
		
		layer = mainController.layers().get(layerData.getIndexData().layerIndex());
		
		indexType="filter";

		layerWidget=new LayerWidget(this, this.mainController);
		layerWindow=new LayerWindow(this, this.mainController);		
	}

	public int layerIndex() {
		return layerData.getIndexData().layerIndex();
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

	public FilterController addFilterController(FilterController newFilterManager) {
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
