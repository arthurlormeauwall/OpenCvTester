package com.opencvtester.dataAccess;

public interface SessionPersistenceDriver {	
	public void saveSession(Session session, String fileName);
	public Session reloadSession(Session session, String fileName);
}
