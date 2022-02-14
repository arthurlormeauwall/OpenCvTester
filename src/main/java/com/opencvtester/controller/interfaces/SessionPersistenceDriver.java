package com.opencvtester.controller.interfaces;

import com.opencvtester.entity.Session;

public interface SessionPersistenceDriver {	
	public void saveSessionAs(Session session, String fileName);
	public Session reloadSession(Session session, String fileName);
	public void saveSession(Session session);
}
