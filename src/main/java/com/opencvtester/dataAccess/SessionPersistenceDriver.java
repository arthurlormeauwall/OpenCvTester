package com.opencvtester.dataAccess;

public interface SessionPersistenceDriver {	
	public void saveSessionAs(Session session, String fileName);
	public Session reloadSession(Session session, String fileName);
	public void saveSession(Session session);
}
