package com.opencvtester.dataPersistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.opencvtester.dataPersistence.interfaces.SessionPersistenceDriver;
import com.opencvtester.dataPersistence.interfacesImp.FileNotCreatedException;


public class SessionFileDriver implements SessionPersistenceDriver {
	public void saveSessionAs(SessionController session, String fileName) {
		session.setTitle(fileName);
		saveSession(session);
	}
	
	@Override
	public void saveSession(SessionController session) {

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
	public SessionController reloadSession(SessionController session, String fileName) {
		SessionController sessionTemp = null;
		try(FileInputStream in = new FileInputStream(fileName);ObjectInputStream ins = new ObjectInputStream(in)) {
			sessionTemp = (SessionController)ins.readObject(); 	
			
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
