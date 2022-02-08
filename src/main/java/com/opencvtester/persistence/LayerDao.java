package com.opencvtester.persistence;

import com.opencvtester.guiManager.LayerFactory;
import com.opencvtester.guiManager.LayerManager;

public class LayerDao implements Dao<LayerManager> {

	private Session session;
	private LayerFactory layerFactory;
	
	public LayerDao(LayerFactory layerFactory) {
		this.layerFactory=layerFactory;
	}
	@Override
	public void init(Session session ) {
		this.session=session;
	}

	@Override
	public void update(LayerManager layerManager) {
		
	}

	@Override
	public LayerManager create(DataRecord layerData) {
		return layerFactory.createLayerManager((LayerData)layerData);
	}

	@Override
	public void add(LayerManager layerManager) {
		
		
	}

	@Override
	public void delete(LayerManager layerManager) {
		
		
	}

}
