package com.opencvtester.data.interfacesImp;

import java.util.ArrayList;
import java.util.List;

import com.opencvtester.controller.interfaces.DataController;
import com.opencvtester.data.interfaces.FilterDataInterface;
import com.opencvtester.data.interfaces.LayerDataInterface;
import com.opencvtester.renderer.ControlledFilter;
import com.opencvtester.renderer.Layer;

public class DataCtrlImp implements DataController
{
	
	protected List<Layer> layers;
	protected List<ArrayList<ControlledFilter>> filters;
	
	public DataCtrlImp() {
		layers=new ArrayList<Layer>();
		filters=new ArrayList<ArrayList<ControlledFilter>>();
	}
	
	public void udpateIndex() {
		int numberOfLayers = layers.size();
		
		for (int i=0;i<numberOfLayers;i++) {
			layers.get(i).getData().setLayerIndex(i);
			int numberOfFilters = filters.get(i).size();
			for (int j=0;j<numberOfFilters;j++) {
				filters.get(i).get(j).getData().setFilterIndex(j);
				filters.get(i).get(j).getData().setLayerIndex(i);			
			}
		}
	}

	@Override
	public void addLayer(int layerIndex) {
		Layer layer = createLayerData(layerIndex);
		addLayer(layer);		
	}
	
	private Layer createLayerData(int layerIndex) {
		return null;
	}
	
	public void addLayer(Layer layer) {
		filters.add(new ArrayList<ControlledFilter>());
		layers.add(layer);
		
		udpateIndex();
		checkAndActivateLayer(layer.getData().layerIndex());
	}

	@Override
	public void deleteLayer(int layerIndex) {
		layers.remove(layerIndex);
		filters.remove(layerIndex);
		
		udpateIndex();
		if (layerIndex>0) {
			checkAndActivateLayer(getLayer(layerIndex-1).getData().layerIndex());
		}
		
	}
	
	public Layer getLayer(int layerIndex) {
	return layers.get(layerIndex);
}
	
	@Override
	public void addFilter(ControlledFilter filterManager) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ControlledFilter addFilter(int layerIndex, int filterIndex, String name) {
		ControlledFilter filter = createFilter(layerIndex, filterIndex, name);
		addFilter(filter);	
		return filter;
	}
	
	private ControlledFilter createFilter(int layerIndex, int filterIndex, String name) {
		return null;
	}

	@Override
	public void deleteFilter(int layerIndex, int filterInex) {
		// TODO Auto-generated method stub
		
	}
	
	public void deleteFilter(ControlledFilter filter) {
		int layerIndex=filter.layerIndex();
		int filterIndex=filter.filterIndex();
		
		filters.get(layerIndex).remove(filterIndex);		
		
		udpateIndex();
		
		if (this.getLayer(layerIndex).getNumberOfFilters()==0 || filterIndex==0) {
			checkAndActivateLayer(getLayer(layerIndex).getData().layerIndex());
		}
		else {
			checkAndActivateFilter(layerIndex, filterIndex-1);
		}
	}
	
	public void checkAndActivateFilter (int layerIndex, int filterIndex) {	
		layers.get(layerIndex).getData().activate();
		getFilter(layerIndex, filterIndex).getData().activate();
		for (int i=layerIndex+1; i<getNumberOfLayers() ; i++) {
			checkAndActivateLayer(getLayer(i).getData().layerIndex());
		}
	}
	
	public ControlledFilter getFilter(int layerIndex, int filterIndex) {
		return filters.get(layerIndex).get(filterIndex);
	}
	
	public int getNumberOfLayers() {
		return layers.size();
	}

	@Override
	public void setParameters(ControlledFilter filterToSet, String name, Float value) {
		filterToSet.setParameter(name, value);
		checkAndActivateFilter(filterToSet.layerIndex(), filterToSet.filterIndex());	
		
	}

	@Override
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass) {
		((FilterDataInterface)getFilter(layerIndex,filterIndex).getData()).setBypass(bypass);
		checkAndActivateFilter(layerIndex, filterIndex);		
	}

	@Override
	public void setOpacity(int layerIndex, Float opacity) {
		layers.get(layerIndex).getOpacityFilter().setOpacity(opacity);	
		checkAndActivateLayer(layerIndex);
		
	}

	@Override
	public void clearAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Layer> getLayers() {
		return layers;
	}

	@Override
	public ArrayList<ControlledFilter> getFilters(int layerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkAndActivateLayer(int layerIndex) {
		LayerDataInterface layer= (LayerDataInterface) layers.get(layerIndex);
		layer.activate();
		
		if (layer.getNumberOfFilters()>0) {
			getFilter(layerIndex,0).getData().activate();
		}
		for (int i= layerIndex; i< layers.size() ; i++) {
			LayerDataInterface currentLayer= (LayerDataInterface) layers.get(i);
			currentLayer.activate();
			if (currentLayer.getNumberOfFilters()>0) {
				getFilter(i,0).getData().activate();
			}
		}
		
	}

//	public void deleteLayer(Layer layer) {
//		deleteLayer(layer.getData().layerIndex());
//	}

//	public void addFilter(ControlledFilter filter) {
//		filters.get(filter.layerIndex()).add(filter);		
//		udpateIndex();
//		checkAndActivateFilter(filter.layerIndex(),filter.filterIndex());
//	}


//	public Layer getLastLayer(){
//		return layers.get(getNumberOfLayers() - 1);
//	}   

//	public void setAllFilterParameters(ControlledFilter adjustControlToSet, LinkedHashMap<String,Float> parameters){
//			adjustControlToSet.setAllParameters(parameters);
//			checkAndActivateFilter(adjustControlToSet.layerIndex(),adjustControlToSet.filterIndex());
//	} 
}