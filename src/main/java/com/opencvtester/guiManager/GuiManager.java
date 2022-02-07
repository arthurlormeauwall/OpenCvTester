package com.opencvtester.guiManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Stack;

import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.filtersDataBase.FiltersDataBase;
import com.opencvtester.gui.MainWindow;
import com.opencvtester.historyManager.HistoryManager;
import com.opencvtester.historyManager.action.AddOrDeleteFilter;
import com.opencvtester.historyManager.action.AddOrDeleteLayer;
import com.opencvtester.historyManager.action.Functionalities;
import com.opencvtester.historyManager.action.SetParameters;
import com.opencvtester.renderingEngine.ChainOfLayers;


public class GuiManager 
{
	private HistoryManager history;
	private ChainOfLayers chainOfLayers;
	private MainWindow mainWindow;
	private FrameWindowManager frameOutWindow;
	private LayerFactory layerFactory;
	private FilterFactory filterFactory;
	private FiltersDataBase filtersDataBase;

	/*
	 * CONSTRUCTOR & INITS
	 */
	public GuiManager(String fileName) throws IOException{
		
		filtersDataBase = new FiltersDataBase();
		chainOfLayers = new ChainOfLayers(fileName);
		mainWindow = new MainWindow(this);		
		history=new HistoryManager();
		frameOutWindow=new FrameWindowManager(chainOfLayers.getFrameOut());
		layerFactory=new LayerFactory(filtersDataBase, this);
		filterFactory=new FilterFactory(filtersDataBase, this);
	}

	
	/*
	 * GETTERS & SETTERS
	 */

	public Stack<String> getFiltersName() {
		return filtersDataBase.getFiltersName();	
	}
	
	/*
	 * FEATURES
	 */
	
	
	public void reload() {
		
	}

	public void save() {
		try(FileOutputStream out = new FileOutputStream("src/main/ressources/sessions/test"); ObjectOutputStream os = new ObjectOutputStream(out)) {
//			os.writeObject(new Session(mainWindow.getChainOfLayerManagers().getChainOfCommands())); 
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("yo mama");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("yo mama");
		}		
	}

	public void addFilterInDatabase(String name, FilterControlledByFloat filter) {
		filtersDataBase.addFilter(name, filter);
	}
	
	public void createAddLayerAndSetHistory(int layerIndex, Stack<String> filterNames) {
		LayerManager layerManager = layerFactory.createLayerManager(layerIndex, filterNames);
		addLayer(layerManager);
		setAddLayerHistory(layerManager);
	}
	
	public void createAddLayerAndSetHistory(int layerIndex) {
		LayerManager layerManager = layerFactory.createEmptyLayerManager(layerIndex);
		addLayer(layerManager);
		setAddLayerHistory(layerManager);
	}

	public boolean deleteLayerAndSetHistory(LayerManager layerManager) {		
		if (layerManager!=null) {
			deleteLayer(layerManager);			
			setDeleteLayerHistory(layerManager);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addLayer(LayerManager layerManager) {
		chainOfLayers.addLayer(layerManager.getLayer());			
		
		mainWindow.addLayerManager(layerManager);
		mainWindow.getGuiManager().refreshFrameOut();
	}	
	
	public void deleteLayer(LayerManager layerManager) {
		chainOfLayers.deleteLayer(layerManager.getLayer());
		mainWindow.deleteLayerManager(layerManager);	
		refreshFrameOut();
	}
	
	public void setAddLayerHistory(LayerManager layerManager) {
		AddOrDeleteLayer parameter= new AddOrDeleteLayer (this, layerManager);
		parameter.setAddOrDelete(Functionalities.ADD);
		history.setState(parameter);	
	}
	
	public void setDeleteLayerHistory(LayerManager layerManager) {
		AddOrDeleteLayer parameter= new AddOrDeleteLayer (this, layerManager);
		parameter.setAddOrDelete(Functionalities.DELETE);
		history.setState(parameter);	
	}
	
	
	///////////////////////////////////////////////////////////
	
	public void createAddFilterAndSetHistory(int layerIndex, int filterIndex, String filterName) {
		FilterManager filterManager = filterFactory.createFilterFilterManager(layerIndex, filterIndex, filterName);	
		addFilter( filterManager);
		setAddFilterHistory(filterManager);
	}	

	public boolean deleteFilterAndSetHistory(FilterManager filterManager) {		
		if (filterManager!=null) {
			deleteFilter(filterManager);			
			setDeleteFilterHistory(filterManager);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addFilter(FilterManager filterManager) {
		chainOfLayers.addFilter(filterManager.getFilter());	
		mainWindow.addFilterManager(filterManager);	
		mainWindow.getGuiManager().refreshFrameOut();
	}	
	
	public void deleteFilter(FilterManager filterManager) {
		chainOfLayers.deleteFilter(filterManager.getFilter());
		mainWindow.deleteFilterManager(filterManager);
		mainWindow.getGuiManager().refreshFrameOut();
	}
	
	public void setAddFilterHistory(FilterManager filterManager) {
		AddOrDeleteFilter parameter= new AddOrDeleteFilter (this, filterManager);
		parameter.setAddOrDelete(Functionalities.ADD);
		history.setState(parameter);
		
	}
	
	public void setDeleteFilterHistory(FilterManager filterManager) {
		AddOrDeleteFilter parameter= new AddOrDeleteFilter (this, filterManager);
		parameter.setAddOrDelete(Functionalities.DELETE);
		history.setState(parameter);
	}
	
	public void setOpacity(Filter opacityFilter, Float opacity) {	
		chainOfLayers.setOpacity(opacityFilter, opacity);
		refreshFrameOut();
	}	

	public void setParametersAndSetHistory(FilterControlledByFloat filterToSet, String name, Float value) throws IOException {
		chainOfLayers.setOneParameter (filterToSet, name, value);	
		refreshFrameOut();
		
		 setSetParameterHistory(filterToSet);
	}
	
	public void setSetParameterHistory(FilterControlledByFloat filter) {
		SetParameters parameter= new SetParameters(this, chainOfLayers, mainWindow.getChainOfLayerManagers(), filter);
		history.setState(parameter);	
	}
	
	public void setParameters(FilterControlledByFloat filterToSet, String name, Float value) throws IOException {
		chainOfLayers.setOneParameter (filterToSet, name, value);	
		refreshFrameOut();
	}
	
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass) {
		chainOfLayers.setBypass(layerIndex, filterIndex, bypass);
		refreshFrameOut();
	}
	
	public void refreshFrameOut(){
		frameOutWindow.refresh(chainOfLayers.getFrameOut());	
	}
	
	public void undo() {
		history.undo();	
	}
	
	public void redo() {
		history.redo();
	}
	
	public void store() {	
		history.storeCurrentStateInHistory();
	}
}
