package com.opencvtester.dataAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Stack;

import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.guiManager.FilterManager;
import com.opencvtester.guiManager.GuiManager;
import com.opencvtester.guiManager.LayerManager;

public class SessionManager {
	private LayerDao layerDao;
	private FilterDao filterDao;
	private Session session;
	private FiltersDataBase filtersDataBase;
	private GuiManager guiManager;
	
	public SessionManager(FiltersDataBase filtersDataBase, GuiManager guiManager) {
		this.filtersDataBase=filtersDataBase;
		this.guiManager= guiManager;
		session=new Session("temp", new ArrayList<LayerData>(), new ArrayList<FilterData>());
		init();
	}
	
	public void init() {
		session=new Session("temp", new ArrayList<LayerData>(), new ArrayList<FilterData>());
		layerDao=new LayerDao(new LayerFactory(filtersDataBase, guiManager));
		layerDao.init(session);
		filterDao=new FilterDao(new FilterFactory(filtersDataBase, guiManager));
		filterDao.init(session);
	}
	
	public void saveSession(String fileName) {
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
	
	public void restoreSession(String fileName, GuiManager guiManager) {	
		Session sessionTemp = reloadSession(fileName);
		guiManager.clearAll();
		buildFromSession(guiManager, sessionTemp);
	}

	public Session reloadSession(String fileName) {
		init();
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


	private void buildFromSession(GuiManager guiManager, Session sessionTemp) {
		int numberOfLayer= sessionTemp.getLayers().size();
		for (int i=0;i<numberOfLayer;i++) {
			guiManager.addLayer(layerDao.create(sessionTemp.getLayers().get(i)));	
		}
		int numberOfFilter= sessionTemp.getFilters().size();
		for (int i=0;i<numberOfFilter;i++) {
			guiManager.addFilter(filterDao.create(sessionTemp.getFilters().get(i)));
		}
	}

	public LayerData createLayerData(int layerIndex, Stack<String> filterNames) {	
		return new LayerData(layerIndex, 100f, filterNames);
	}

	public LayerData createLayerData(int layerIndex) {	
		return new LayerData(layerIndex, 100f, null);
	}	
	
	public void addLayer(LayerManager layerManager) {
		layerDao.add(layerManager);
	}	
	
	public void deleteLayer(LayerManager layerManager) {
		layerDao.delete(layerManager);	
	}

	public void updateOpacity(FilterControlledByFloat opacityFilter, Float opacity) {
		
		LinkedHashMap<String, Float> parameters= new LinkedHashMap<String, Float>();
		parameters.put("Opacity", opacity);
		filterDao.update(new FilterData(opacityFilter.layerIndex(), opacityFilter.filterIndex(), opacityFilter.getFilterName(), parameters));
	}

	public FilterData createFilter(int layerIndex, int filterIndex, String filterName) {
		return new FilterData(layerIndex,filterIndex, filterName, null);
	}
	
	public void addFilter(FilterManager filterManager) {	
		filterDao.add(filterManager);
	}
	
	public void deleteFilter(FilterManager filterManager) {
		filterDao.delete(filterManager);
	}


	public void updateParameters(FilterControlledByFloat filterToSet, String name, Float value) {
		LinkedHashMap<String, Float> parameters= filterToSet.getParameters();
		parameters.put(name, value);
		filterDao.update(new FilterData(filterToSet.layerIndex(), filterToSet.filterIndex(), filterToSet.getFilterName(), parameters));
	}
	


}
