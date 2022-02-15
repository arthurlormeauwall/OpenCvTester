package com.opencvtester.gui.interfacesImp;



import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.opencvtester.controller.MainController;
import com.opencvtester.controller.interfaces.MainWindow;
import com.opencvtester.controller.layer.LayerController;
import com.opencvtester.controller.layer.LayersController;
import com.opencvtester.filterController.FilterController;
import com.opencvtester.gui.LayerWindow;
import com.opencvtester.renderer.entity.ControlledFilter;


public class MainWindowSwing extends JFrame implements MainWindow
{
	private static final long serialVersionUID = 1L;
	
	private LayersController chainOfLayerManagers;
	private MainController guiManager;
	private JPanel layerPanel;
	private JButton addLayerButton; 
	private JButton delLayerButton; 
	private JButton undoButton;
	private JButton redoButton;  
	private JButton saveButton;
	private JButton reloadButton;
	private JFileChooser fileChooser;
	
	public Stack<LayerWindow> test;

	private JButton saveAsButton;

	private JButton openImageButton;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public MainWindowSwing(MainController guiManager) {
		super("OpenCV tester");
		
		test= new Stack<LayerWindow>();
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.guiManager=guiManager;	
		chainOfLayerManagers= new LayersController(this);
		
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
	    	  MainWindowSwing.this.guiManager.save();
	     });
		 
		 saveAsButton.addActionListener((ActionEvent event)->{
	    	  MainWindowSwing.this.saveSessionAs();
	     });
	 
		 reloadButton.addActionListener((ActionEvent event)->{
	    	  MainWindowSwing.this.openSession();
	     });
		 undoButton.addActionListener((ActionEvent event)->{
		    	  MainWindowSwing.this.guiManager.undo();
		     });
		 
		 redoButton.addActionListener((ActionEvent event)->{
		    	  MainWindowSwing.this.guiManager.redo();
		     });
		 
		 addLayerButton.addActionListener((ActionEvent event)->{	
		    	MainWindowSwing.this.guiManager.createAddLayerAndSetHistory(chainOfLayerManagers.getNumberOfLayer());
		    	MainWindowSwing.this.guiManager.store();
		     });
		 
		 delLayerButton.addActionListener((ActionEvent event)->{
		    	  if (MainWindowSwing.this.guiManager.deleteLayerAndSetHistory(chainOfLayerManagers.getLayerManager(chainOfLayerManagers.getNumberOfLayer()-1))) {
		    		  MainWindowSwing.this.guiManager.store();
		    	  }		    	  
		     });
	}


	private void openImage() {
		int response = fileChooser.showOpenDialog(this);
		if (response==0) {
			guiManager.openImage(fileChooser.getSelectedFile().getPath());
		}
	}

	/*
	 * GETTERS & SETTERS
	 */
	public MainController getGuiManager() {
		return guiManager;
	}

	public LayersController getChainOfLayerManagers() {
		return chainOfLayerManagers;
	}	
	
	/*
	 * FEATURES
	 */
	
	public void setOpacity(int layerIndex, Float opacity) {
		chainOfLayerManagers.getLayerManager(layerIndex).getLayerWidget().setOpacitySlider(opacity);
	}
	
	public void addFilterManager(FilterController filterManager) {
		chainOfLayerManagers.addFilterManager(filterManager);
	}

	public void deleteFilterManager(FilterController filterManager) {	
		chainOfLayerManagers.deleteFilterManager(filterManager);	
	}

	public void addLayerManager(LayerController layerManager) {
		layerManager.createLayerWindow();
		chainOfLayerManagers.addLayerManager(layerManager);	
	}
	
	public void deleteLayerManager(LayerController layerManager) {
		layerManager.deleteLayerWindow();
		chainOfLayerManagers.deleteLayerManager(layerManager);		
	}
	

	public void updateOpacityValue(int layerIndex, Float opacity) {
	}

	public void updateParametersValues(ControlledFilter filter, LinkedHashMap<String, Float> parameters) {	
		chainOfLayerManagers.getLayerManager(filter.layerIndex()).getFilterManager(filter.filterIndex()).updateParameterValues(parameters);
	}
	
	public void updateGui() {
		chainOfLayerManagers.updateGui();		
		
		layerPanel.removeAll();
		
		int numberOfFiltersToAdd = chainOfLayerManagers.getNumberOfLayer();
		for (int i=0;i<numberOfFiltersToAdd;i++) {
			
			layerPanel.add(chainOfLayerManagers.getLayerManager(i).getLayerWidget());
		}
		this.pack();
	}

	public void clearAll() {
		for (int i=chainOfLayerManagers.getNumberOfLayer()-1;i>=0;i--) {
			chainOfLayerManagers.getLayerManager(i).deleteLayerWindow();
			chainOfLayerManagers.deleteLayerManager(chainOfLayerManagers.getLayerManager(i));	
		}
	}

	public void saveSessionAs() {
	
		int response = fileChooser.showSaveDialog(this);
		if (response==0) {
			guiManager.saveSessionAs(fileChooser.getSelectedFile().getPath());
		}
	}
	
	private void openSession() {
		int response = fileChooser.showOpenDialog(this);
		if (response==0) {
			guiManager.openSession(fileChooser.getSelectedFile().getPath());
		}
	}

}


