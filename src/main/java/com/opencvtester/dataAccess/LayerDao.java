package com.opencvtester.dataAccess;

import com.opencvtester.guiManager.LayerManager;

public class LayerDao implements Dao<LayerManager> {

	private Session session;
	private LayerFactory layerFactory;
	
	public LayerDao(LayerFactory layerFactory) {
		this.layerFactory=layerFactory;
	}
	@Override
	public void init(Session session) {
		this.session=session;
	}

	@Override
	public void update(DataRecord layerData) {
		
	}

	@Override
	public LayerManager create(DataRecord layerData) {
		return layerFactory.createLayerManager((LayerData)layerData);
	}

	@Override
	public void add(LayerManager layerManager) {
		session.getLayers().add(layerManager.getData());
	}

	@Override
	public void delete(LayerManager layerManager) {
		session.getLayers().remove(layerManager.getData());
		for (int i=0;i<session.getFilters().size();i++) {
			if(session.getFilters().get(i).getLayerIndex()==layerManager.layerIndex()) {
				session.getFilters().remove(i);
			}
		}
	}

}
