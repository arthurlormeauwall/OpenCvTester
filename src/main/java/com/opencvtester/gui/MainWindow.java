package com.opencvtester.gui;



import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.guiManager.ChainOfLayerManagers;
import com.opencvtester.guiManager.FilterManager;
import com.opencvtester.guiManager.GuiManager;
import com.opencvtester.guiManager.LayerManager;


public class MainWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private ChainOfLayerManagers chainOfLayerManagers;
	private GuiManager guiManager;
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
	public MainWindow(GuiManager guiManager) {
		super("OpenCV tester");
		
		test= new Stack<LayerWindow>();
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.guiManager=guiManager;	
		chainOfLayerManagers= new ChainOfLayerManagers(this);
		
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
	    	  MainWindow.this.openImage();
	     });
		 
		 saveButton.addActionListener((ActionEvent event)->{
	    	  MainWindow.this.guiManager.save();
	     });
		 
		 saveAsButton.addActionListener((ActionEvent event)->{
	    	  MainWindow.this.saveSessionAs();
	     });
	 
		 reloadButton.addActionListener((ActionEvent event)->{
	    	  MainWindow.this.openSession();
	     });
		 undoButton.addActionListener((ActionEvent event)->{
		    	  MainWindow.this.guiManager.undo();
		     });
		 
		 redoButton.addActionListener((ActionEvent event)->{
		    	  MainWindow.this.guiManager.redo();
		     });
		 
		 addLayerButton.addActionListener((ActionEvent event)->{	
		    	MainWindow.this.guiManager.createAddLayerAndSetHistory(chainOfLayerManagers.getNumberOfLayer());
		    	MainWindow.this.guiManager.store();
		     });
		 
		 delLayerButton.addActionListener((ActionEvent event)->{
		    	  if (MainWindow.this.guiManager.deleteLayerAndSetHistory(chainOfLayerManagers.getLayerManager(chainOfLayerManagers.getNumberOfLayer()-1))) {
		    		  MainWindow.this.guiManager.store();
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
	public GuiManager getGuiManager() {
		return guiManager;
	}

	public ChainOfLayerManagers getChainOfLayerManagers() {
		return chainOfLayerManagers;
	}	
	
	/*
	 * FEATURES
	 */
	
	public void setOpacity(int layerIndex, Float opacity) {
		chainOfLayerManagers.getLayerManager(layerIndex).getLayerWidget().setOpacitySlider(opacity);
	}
	
	public void addFilterManager(FilterManager filterManager) {
		chainOfLayerManagers.addFilterManager(filterManager);
	}

	public void deleteFilterManager(FilterManager filterManager) {	
		chainOfLayerManagers.deleteFilterManager(filterManager);	
	}

	public void addLayerManager(LayerManager layerManager) {
		layerManager.createLayerWindow();
		chainOfLayerManagers.addLayerManager(layerManager);	
	}
	
	public void deleteLayerManager(LayerManager layerManager) {
		layerManager.deleteLayerWindow();
		chainOfLayerManagers.deleteLayerManager(layerManager);		
	}
	

	public void updateOpacityValue(int layerIndex, Float opacity) {
	}

	public void updateParametersValues(Filter filter, LinkedHashMap<String, Float> parameters) {	
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


