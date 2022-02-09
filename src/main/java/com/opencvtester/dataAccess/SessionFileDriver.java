package com.opencvtester.dataAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SessionFileDriver implements SessionPersistenceDriver {
	public void saveSession(Session session, String fileName) {
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
