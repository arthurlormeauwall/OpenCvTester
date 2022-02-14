package com.opencvtester.controller.layer;

import com.opencvtester.controller.interfaces.Dao;
import com.opencvtester.entity.LayerData;
import com.opencvtester.entity.Session;
import com.opencvtester.entity.interfaces.DataRecord;

public class LayerDao implements Dao<LayerController> {

	private Session session;

	@Override
	public void init(Session session) {
		this.session=session;
	}

	@Override
	public void update(DataRecord layerData) {	
		session.getLayers().get(((LayerData)layerData).getLayerIndex()).setOpacityValue(((LayerData)layerData).getOpacityValue());
	}
	
	@Override
	public void add(LayerController layerManager) {
		session.getLayers().add(layerManager.getData());
	}

	@Override
	public void delete(LayerController layerManager) {
		session.getLayers().remove(layerManager.getData());
		for (int i=0;i<session.getFilters().size();i++) {
			if(session.getFilters().get(i).getLayerIndex()==layerManager.layerIndex()) {
				session.getFilters().remove(i);
			}
		}
	}

}
