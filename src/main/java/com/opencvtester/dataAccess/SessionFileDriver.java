package com.opencvtester.dataAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class FileNotCreatedException extends RuntimeException{
	private static final long serialVersionUID = 9222458802487115627L;	
}
public class SessionFileDriver implements SessionPersistenceDriver {
	public void saveSessionAs(Session session, String fileName) {
		session.setTitle(fileName);
		saveSession(session);
	}
	
	@Override
	public void saveSession(Session session) {

		String fileName= session.getTitle();
		if (fileName==null) {
			FileNotCreatedException e = new FileNotCreatedException();
			throw e;
		}
		else {
			File file= new File(fileName);
			if (file.exists()) {
				file.delete();
			}
			try(FileOutputStream out = new FileOutputStream(fileName);ObjectOutputStream os = new ObjectOutputStream(out)) {
				os.writeObject(session); 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

	@Override
	public Session reloadSession(Session session, String fileName) {
		Session sessionTemp = null;
		try(FileInputStream in = new FileInputStream(fileName);ObjectInputStream ins = new ObjectInputStream(in)) {
			sessionTemp = (Session)ins.readObject(); 	
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return sessionTemp;	
	}


}
