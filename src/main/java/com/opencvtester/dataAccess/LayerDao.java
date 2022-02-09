package com.opencvtester.dataAccess;

import com.opencvtester.guiManager.LayerManager;

public class LayerDao implements Dao<LayerManager> {

	private Session session;

	@Override
	public void init(Session session) {
		this.session=session;
	}

	@Override
	public void update(DataRecord layerData) {		
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
