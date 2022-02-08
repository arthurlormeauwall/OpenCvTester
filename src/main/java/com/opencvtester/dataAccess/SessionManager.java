package com.opencvtester.dataAccess;

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
	
	public SessionManager(FiltersDataBase filtersDataBase, GuiManager guiManager) {
		session=new Session("temp", new ArrayList<LayerData>(), new ArrayList<FilterData>());
		
		layerDao=new LayerDao(new LayerFactory(filtersDataBase, guiManager));
		layerDao.init(session);
		filterDao=new FilterDao(new FilterFactory(filtersDataBase, guiManager));
		filterDao.init(session);
	}
	
	public void saveSession(String fileName) {	
		try(FileOutputStream out = new FileOutputStream(fileName);ObjectOutputStream os = new ObjectOutputStream(out)) {
			os.writeObject(session); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void reloadSession(String fileName) {
		try(FileInputStream in = new FileInputStream(fileName);ObjectInputStream ins = new ObjectInputStream(in)) {
			session = (Session)ins.readObject(); 	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {
	
			e.printStackTrace();
		} 
	}
	public void restoreSession(String fileName, GuiManager guiManager) {	
		reloadSession(fileName);
		guiManager.clearAll();
		buildFromSession(guiManager);
	}

	private void buildFromSession(GuiManager guiManager) {
		for (int i=0;i<session.layers().size();i++) {
			guiManager.addLayer(layerDao.create(session.layers().get(i)));	
		}
		
		for (int i=0;i<session.filters().size();i++) {
			guiManager.addFilter(filterDao.create(session.filters().get(i)));
		}
	}

	public LayerManager createLayer(int layerIndex, Stack<String> filterNames) {	
		return layerDao.create(new LayerData(layerIndex, 100f, filterNames));
	}

	public LayerManager createLayer(int layerIndex) {	
		return layerDao.create(new LayerData(layerIndex, 100f, null));
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

	public FilterManager createFilter(int layerIndex, int filterIndex, String filterName) {
		return filterDao.create(new FilterData(layerIndex,filterIndex, filterName, null));
	}
	
	public void addFilter(FilterManager filterManager) {	
		filterDao.add(filterManager);
	}
	
	public void deleteFilter(FilterManager filterManager) {
		filterDao.delete(filterManager);
	}


	public void updateParameters(FilterControlledByFloat filterToSet, String name, Float value) {
		LinkedHashMap<String, Float> parameters= new LinkedHashMap<String, Float>();
		parameters.put(name, value);
		filterDao.update(new FilterData(filterToSet.layerIndex(), filterToSet.filterIndex(), filterToSet.getFilterName(), parameters));
	}

}
