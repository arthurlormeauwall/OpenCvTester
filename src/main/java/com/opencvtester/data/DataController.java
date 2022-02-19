package com.opencvtester.data;

import java.util.LinkedHashMap;
import java.util.Stack;

import com.opencvtester.appControllers.DataInterface;
import com.opencvtester.renderer.ControlledFilter;
import com.opencvtester.renderer.FiltersDataBase;
import com.opencvtester.renderer.Layer;

public class DataController implements DataInterface
{
	
	protected Stack<Layer> layers;

	private FiltersDataBase filtersDataBase;
	
	public DataController(FiltersDataBase filtersDataBase) {
		this.filtersDataBase = filtersDataBase;
		layers=new Stack<Layer>();
	}
	
	public void udpateIndex() {
		int numberOfLayers = layers.size();
		
		for (int i=0;i<numberOfLayers;i++) {
			layers.get(i).
			getFilterData().
			getIndexData().		    
			setLayerIndex(i);
			
			int numberOfFilters = layers.get(i).getNumberOfFilters();
			for (int j=0;j<numberOfFilters;j++) {
				layers.get(i).getFilter(j).getFilterData().getIndexData().setFilterIndex(j);
				layers.get(i).getFilter(j).getFilterData().getIndexData().setLayerIndex(i);			
			}
		}
	}

	@Override
	public void addLayer(int layerIndex) {
		Layer layer = createLayer(layerIndex);
		addLayer(layer);		
	}
	
	private Layer createLayer(int layerIndex) {
		return new Layer(layerIndex);
	}
	
	public void addLayer(Layer layer) {
		layers.push(layer);
		
		udpateIndex();
		if (getNumberOfLayers() <= 1) {
			checkAndActivateLayer(getLayer(layer.getData().getIndexData().layerIndex()).getFilterData().getIndexData().layerIndex());
		}
		else  {
			checkAndActivateLayer(getLayer(layer.getData().getIndexData().layerIndex()-1).getFilterData().getIndexData().layerIndex());
		}
	}

	@Override
	public void deleteLayer(int layerIndex) {
		layers.remove(layerIndex);
	
		udpateIndex();

		 if (getNumberOfLayers() >= 1) {
			checkAndActivateLayer(getLayer(layerIndex-1).getFilterData().getIndexData().layerIndex());
		}
		
	}
	
	public Layer getLayer(int layerIndex) {
	return layers.get(layerIndex);
}
	
	@Override
	public void addFilter(ControlledFilter filter) {
		layers.get(filter.layerIndex()).addFilter(filter);	
		
		
		LayerDataInterface data = layers.get(filter.layerIndex()).getFilterData();
		data.setNumberOfFilters(data.getNumberOfFilters()+1);
		udpateIndex();
		
		checkAndActivateFilter(filter.layerIndex(),filter.filterIndex());
	}
	
	@Override
	public void addFilter(int layerIndex, int filterIndex, String name) {
		ControlledFilter filter = createFilter(layerIndex, filterIndex, name);
		addFilter(filter);	
		filter.bypass(false);
	}
	
	private ControlledFilter createFilter(int layerIndex, int filterIndex, String name) {
		ControlledFilter filter = filtersDataBase.getFilter(name);
		filter.getFilterData().getIndexData().setFilterIndex(filterIndex);
		filter.getFilterData().getIndexData().setLayerIndex(layerIndex);	
		return filter;
	}

	@Override
	public void deleteFilter(int layerIndex, int filterIndex) {

		layers.get(layerIndex).removeFilter(filterIndex);		
		
		udpateIndex();
		
		if (this.getLayer(layerIndex).getNumberOfFilters()==0 || filterIndex==0) {
			checkAndActivateLayer(getLayer(layerIndex).getFilterData().getIndexData().layerIndex());
		}
		else {
			checkAndActivateFilter(layerIndex, filterIndex-1);
		}
		
	}
	
	public void deleteFilter(ControlledFilter filter) {
		int layerIndex=filter.layerIndex();
		int filterIndex=filter.filterIndex();
		
		layers.get(layerIndex).removeFilter(filterIndex);		
		
		udpateIndex();
		
		if (this.getLayer(layerIndex).getNumberOfFilters()==0 || filterIndex==0) {
			checkAndActivateLayer(getLayer(layerIndex).getFilterData().getIndexData().layerIndex());
		}
		else {
			checkAndActivateFilter(layerIndex, filterIndex-1);
		}
	}
	
	public void checkAndActivateFilter (int layerIndex, int filterIndex) {	
		layers.get(layerIndex).getFilterData().getIndexData().activate();
		getFilter(layerIndex, filterIndex).getFilterData().getIndexData().activate();
		for (int i=layerIndex+1; i<getNumberOfLayers() ; i++) {
			checkAndActivateLayer(getLayer(i).getFilterData().getIndexData().layerIndex());
		}
	}
	
	public ControlledFilter getFilter(int layerIndex, int filterIndex) {
		return layers.get(layerIndex).getFilter(filterIndex);
	}
	
	public int getNumberOfLayers() {
		return layers.size();
	}

	@Override
	public void setParameters(ControlledFilter filterToSet, String name, Float value) {
		filterToSet.setParameter(name, value);
		checkAndActivateFilter(filterToSet.layerIndex(), filterToSet.filterIndex());	
		
	}
	
	public void setParameters(ControlledFilter filterToSet, LinkedHashMap<String, Float> parameters) {
		filterToSet.setAllParameters(parameters);
		checkAndActivateFilter(filterToSet.layerIndex(), filterToSet.filterIndex());	
	}
	

	@Override
	public void setParameters(int layerIndex, int filterIndex, LinkedHashMap<String, Float> parameters) {
		ControlledFilter filterToSet = getFilter(layerIndex, filterIndex);
		filterToSet.setAllParameters(parameters);
		checkAndActivateFilter(layerIndex, filterIndex);	
	}

	@Override
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass) {
		getFilter(layerIndex,filterIndex).bypass(bypass);
		checkAndActivateFilter(layerIndex, filterIndex);		
	}

	@Override
	public void setOpacity(int layerIndex, Float opacity) {
		layers.get(layerIndex).getOpacityFilter().setOpacity(opacity);	
		((LayerDataInterface)layers.get(layerIndex).getFilterData()).setOpacity(opacity);
		checkAndActivateLayer(layerIndex);	
	}

	@Override
	public void clearAll() {
		layers.clear();		
	}

	@Override
	public Stack<Layer> getLayers() {
		return layers;
	}
	
	@Override
	public void checkAndActivateLayer(int layerIndex) {
		Layer layer= layers.get(layerIndex);
		
		layer.getFilterData().getIndexData().activate();
		
		if (layer.getNumberOfFilters()>0) {
			getFilter(layerIndex,0).getFilterData().getIndexData().activate();
		}
		for (int i= layerIndex; i< layers.size() ; i++) {
			Layer currentLayer= layers.get(i);
			currentLayer.getFilterData().getIndexData().activate();
			if (currentLayer.getNumberOfFilters()>0) {
				getFilter(i,0).getFilterData().getIndexData().activate();
			}
		}
		
	}

}