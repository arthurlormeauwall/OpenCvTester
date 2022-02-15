package com.opencvtester.data.interfacesImp;

import java.util.LinkedHashMap;

import com.opencvtester.controller.interfaces.DataController;
import com.opencvtester.controller.layer.LayerController;
import com.opencvtester.data.ChainOfCommands;
import com.opencvtester.data.FilterData;
import com.opencvtester.data.LayerData;
import com.opencvtester.data.LayerData;
import com.opencvtester.filterController.FilterController;
import com.opencvtester.renderer.entity.ControlledFilter;
import com.opencvtester.renderer.entity.LayerFactory;
import com.opencvtester.renderer.interfaces.IOFrame;

public class DataCtrlImp extends ChainOfCommands implements DataController
{
	protected LayerFactory layersFactory;
	
	public DataCtrlImp () {
		super("layer", -2, -2);
	}

	public void  addLayer(LayerData newLayer) {
		add(newLayer);
		
		checkAndActivateLayer(newLayer);
	}

	@Override
	public void addLayer(LayerController layerManager) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public LayerData createLayerData(int layerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteLayer(LayerData layer){
		delete(layer.layerIndex());
		
		if (layer.layerIndex()>0) {
			checkAndActivateLayer(this.getLayer(layer.layerIndex()-1));
		}	
	}  


	@Override
	public void deleteLayer(LayerController layerManager) {
		// TODO Auto-generated method stub
		
	}
	
	public void addFilter(ControlledFilter filter) {
		if (areIndexLegal(filter.layerIndex(), filter.filterIndex())) {
			((LayerData)getCommand(filter.layerIndex())).add(filter);
			
			checkAndActivateFilter(filter);
		}
	}
	
	public void deleteFilter(ControlledFilter filter){
		((LayerData)getCommand(filter.layerIndex())).delete(filter.filterIndex());
		
		if (this.getLayer(filter.layerIndex()).getNumberOfFilters()==0 || filter.filterIndex()==0) {
			checkAndActivateLayer(getLayer(filter.layerIndex()));
		}
		else {
			checkAndActivateFilter(getLayer(filter.layerIndex()).getFilter(filter.filterIndex()-1));
		}
	}  
	
	public void setOpacity(ControlledFilter opacityFilter, Float opacity){
		if (getNumberOfLayers() >opacityFilter.layerIndex()) {
			((LayerData)getCommand(opacityFilter.layerIndex())).setOpacity(opacity);
			
			checkAndActivateLayer(getLayer(opacityFilter.layerIndex()));
		}
	} 
	
	public void setOneParameter(ControlledFilter filterToSet, String name, Float value) {
		filterToSet.setParameter(name, value);
		checkAndActivateFilter(filterToSet);
	}
	
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass){
		
		if (areIndexLegal(layerIndex, filterIndex)) {
	
			ControlledFilter filterToBypass = ((ControlledFilter)((LayerData)getCommand(layerIndex)).getFilter(filterIndex));
			filterToBypass.bypass(bypass);
			checkAndActivateFilter(filterToBypass);			
		}
	}
	
	public LayerData getLayer(int layerIndex) {
		return (LayerData)getCommand(layerIndex);
	}

	public LayerData getLastLayer(){
		return (LayerData)getCommand(getSize() - 1);
	}   
	
	public int getNumberOfLayers() {
		return getSize();
	}	

	
	public void setAllFilterParameters(ControlledFilter adjustControlToSet, LinkedHashMap<String,Float> parameters){
			adjustControlToSet.setAllParameters(parameters);
			checkAndActivateFilter(adjustControlToSet);
	} 
		
	public Boolean areIndexLegal(int layerIndex, int filterIndex) {
		return getNumberOfLayers() > layerIndex && ((LayerData)getCommand(layerIndex)).getNumberOfFilters()  >= filterIndex;
	}

	public void checkAndActivateLayer (LayerData layer) {		
		layer.activate();
		if (layer.getNumberOfFilters()>0) {
			layer.getFirstFilter().activate();
		}
		for (int i= layer.layerIndex(); i<getNumberOfLayers() ; i++) {
			getLayer(i).activate();
			if (getLayer(i).getNumberOfFilters()>0) {
				getLayer(i).getFirstFilter().activate();
			}
		}
	}
	
	public void checkAndActivateFilter (ControlledFilter filter) {	
		getLayer(filter.layerIndex()).activate();
		filter.activate();
		for (int i=filter.layerIndex()+1; i<getNumberOfLayers() ; i++) {
			checkAndActivateLayer(getLayer(i));
		}
	}


	@Override
	public FilterData createFilter(int layerIndex, int filterIndex, String filterName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFilter(FilterController filterManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFilter(FilterController filterManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParameters(ControlledFilter filterToSet, String name, Float value) {
		// TODO Auto-generated method stub
	}

	@Override
	public void checkAndActivateFilter(IOFrame newFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearAll() {
		// TODO Auto-generated method stub
		
	}

	
}