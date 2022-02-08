package com.opencvtester.dataAccess;

public interface Dao<T> {	
	public abstract void init(Session session);
	public abstract void update (T object);
	public abstract T create(DataRecord data);
	public abstract void add(T object);
	public abstract void delete(T object);
}
