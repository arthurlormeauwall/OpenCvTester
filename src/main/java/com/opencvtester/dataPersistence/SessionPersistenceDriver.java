package com.opencvtester.dataPersistence;

public interface SessionPersistenceDriver {	
	public void saveSessionAs(SessionController session, String fileName);
	public SessionController reloadSession(SessionController session, String fileName);
	public void saveSession(SessionController session);
}
