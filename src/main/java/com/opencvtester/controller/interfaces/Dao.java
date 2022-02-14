package com.opencvtester.controller.interfaces;

import com.opencvtester.entity.Session;
import com.opencvtester.entity.interfaces.DataRecord;

public interface Dao<T> {	
	public abstract void init(Session session);
	public abstract void update (DataRecord data);
	public abstract void add(T object);
	public abstract void delete(T object);
}
