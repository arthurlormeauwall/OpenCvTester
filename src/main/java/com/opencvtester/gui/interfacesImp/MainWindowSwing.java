package com.opencvtester.gui.interfacesImp;



import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.opencvtester.controller.MainController;
import com.opencvtester.controller.interfaces.MainWindowController;
import com.opencvtester.data.interfaces.FilterDataInterface;
import com.opencvtester.gui.LayerWindow;
import com.opencvtester.gui.controller.FilterController;
import com.opencvtester.gui.controller.LayerController;
import com.opencvtester.gui.controller.LayersController;
import com.opencvtester.renderer.ControlledFilter;
import com.opencvtester.renderer.Layer;


public class MainWindowSwing extends JFrame implements MainWindowController
{
	private static final long serialVersionUID = 1L;
	
	private LayersController chainOfLayerController;
	private MainController mainController;
	
	private List<Layer> layers;
	private List<ArrayList<ControlledFilter>> filters;
	
	private JPanel layerPanel;
	private JButton addLayerButton; 
	private JButton delLayerButton; 
	private JButton undoButton;
	private JButton redoButton;  
	private JButton saveButton;
	private JButton reloadButton;
	private JFileChooser fileChooser;

	private JButton saveAsButton;

	private JButton openImageButton;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public MainWindowSwing(MainController guiManager, List<ArrayList<ControlledFilter>> filters, List<Layer> layers) {
		super("OpenCV tester");
		this.layers=layers;
		this.filters= filters;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainController=guiManager;	
		chainOfLayerController= new LayersController(this);
		
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		layerPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		layerPanel.setLayout(new BoxLayout(layerPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		add(buttonPanel);
		add(layerPanel);
		
		addLayerButton = new JButton("Add");
		delLayerButton = new JButton("Delete");
		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");
		saveButton = new JButton("save");
		saveAsButton = new JButton("save as");
		reloadButton = new JButton ("open project");
		openImageButton = new JButton ("open image");
		
		buttonPanel.add(addLayerButton);
		buttonPanel.add(delLayerButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(redoButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(saveAsButton);
		buttonPanel.add(reloadButton);
		buttonPanel.add(openImageButton);
		
		addListeners();
		
		fileChooser = new JFileChooser(System.getProperty("project.dir"));
		
		 
		this.pack();
		this.setVisible(true);
	}
	
	public void addListeners() {
		 openImageButton.addActionListener((ActionEvent event)->{
	    	  MainWindowSwing.this.openImage();
	     });
		 
		 saveButton.addActionListener((ActionEvent event)->{
	    	  MainWindowSwing.this.mainController.save();
	     });
		 
		 saveAsButton.addActionListener((ActionEvent event)->{
	    	  MainWindowSwing.this.launchSaveSessionAs();
	     });
	 
		 reloadButton.addActionListener((ActionEvent event)->{
	    	  MainWindowSwing.this.openSession();
	     });
		 undoButton.addActionListener((ActionEvent event)->{
		    	  MainWindowSwing.this.mainController.undo();
		     });
		 
		 redoButton.addActionListener((ActionEvent event)->{
		    	  MainWindowSwing.this.mainController.redo();
		     });
		 
		 addLayerButton.addActionListener((ActionEvent event)->{	
		    	MainWindowSwing.this.mainController.createAddLayerAndSetHistory(chainOfLayerController.getNumberOfLayer());
		    	MainWindowSwing.this.mainController.store();
		     });
		 
		 delLayerButton.addActionListener((ActionEvent event)->{
			 if(chainOfLayerController.getNumberOfLayer()>0) {
				 MainWindowSwing.this.mainController.deleteLayerAndSetHistory(chainOfLayerController.getNumberOfLayer()-1);
				 MainWindowSwing.this.mainController.store();	  
			 }	  
		 });
	}


	private void openImage() {
		int response = fileChooser.showOpenDialog(this);
		if (response==0) {
			mainController.openImage(fileChooser.getSelectedFile().getPath());
		}
	}

	public MainController getGuiManager() {
		return mainController;
	}

	public LayersController getChainOfLayerManagers() {
		return chainOfLayerController;
	}	

	
	public void setOpacity(int layerIndex, Float opacity) {
		chainOfLayerController.getLayerManager(layerIndex).getLayerWidget().setOpacitySlider(opacity);
	}
	
	public void addFilter(int layerIndex, int filterIndex) {
		FilterController filterManager= createFilterManager(filters.get(layerIndex).get(filterIndex));
		chainOfLayerController.addFilterManager(filterManager);
	}



	public void deleteFilter(int layerIndex, int filterIndex) {	
		chainOfLayerController.deleteFilterManager(layerIndex,  filterIndex);	
	}

	public void addLayer(int layerIndex) {
		LayerController layerManager= createLayerController(layerIndex);
		layerManager.createLayerWindow();
		chainOfLayerController.addLayerManager(layerManager);	
	}
	
	private LayerController createLayerController(int layerIndex) {
		
		return new LayerController(filters.get(layerIndex), layers.get(layerIndex).getData(), mainController);
	}
	
	private FilterController createFilterManager(ControlledFilter controlledFilter) {

		return new FilterController(controlledFilter, mainController);
	}

	public void deleteLayer(int layerIndex) {
		LayerController layerManager= chainOfLayerController.getLayerManager(layerIndex);
		layerManager.deleteLayerWindow();
		chainOfLayerController.deleteLayerManager(layerManager);		
	}
	

	public void updateOpacityValue(int layerIndex, Float opacity) {
	}

	public void updateFilter(int layerIndex, int filterIndex) {	
		LinkedHashMap<String, Float> parameters= ((FilterDataInterface)filters.get(layerIndex).get(filterIndex).getData()).getParameterValues();
		chainOfLayerController.getLayerManager(layerIndex).getFilterManager(filterIndex).updateParameterValues(parameters);
	}
	
	public void updateGui() {
		chainOfLayerController.updateGui();		
		
		layerPanel.removeAll();
		
		int numberOfFiltersToAdd = chainOfLayerController.getNumberOfLayer();
		for (int i=0;i<numberOfFiltersToAdd;i++) {
			
			layerPanel.add(chainOfLayerController.getLayerManager(i).getLayerWidget());
		}
		this.pack();
	}

	public void clearAll() {
		for (int i=chainOfLayerController.getNumberOfLayer()-1;i>=0;i--) {
			chainOfLayerController.getLayerManager(i).deleteLayerWindow();
			chainOfLayerController.deleteLayerManager(chainOfLayerController.getLayerManager(i));	
		}
	}

	public void launchSaveSessionAs() {
	
		int response = fileChooser.showSaveDialog(this);
		if (response==0) {
			mainController.saveSessionAs(fileChooser.getSelectedFile().getPath());
		}
	}
	
	private void openSession() {
		int response = fileChooser.showOpenDialog(this);
		if (response==0) {
			mainController.openSession(fileChooser.getSelectedFile().getPath());
		}
	}

}


