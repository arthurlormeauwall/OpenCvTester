package com.opencvtester.gui;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.opencvtester.baseClasses.Id;
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
	private JButton addButton; 
	private JButton delButton; 
	private JButton undoButton;
	private JButton redoButton;  
	
	public MainWindow(GuiManager guiManager) {
		super("OpenCV tester");
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
		
		addButton = new JButton("AddLayer");
		delButton = new JButton("dellayer");
		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");
		buttonPanel.add(addButton);
		buttonPanel.add(delButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(redoButton);
		
		addListeners();
		 
		this.pack();
		this.setVisible(true);
	}
	
	public void addListeners() {
		 undoButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	  
		    	  MainWindow.this.guiManager.undo();
		    	
		      }
		    });
		 
		 redoButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	  MainWindow.this.guiManager.redo();
		      }
		    });
		 
		 addButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	MainWindow.this.guiManager.createAndAddLayer(chainOfLayerManagers.getNumberOfLayer(), null);
		      }
		    });
		 
		 delButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	  MainWindow.this.guiManager.deleteLayerManager(chainOfLayerManagers.getLayerManager(chainOfLayerManagers.getNumberOfLayer()-1));	    	
		      }
		    });
	}

	public void addFilterWidgetInLayerWidget(FilterManager filterController) {
		chainOfLayerManagers.addFilterWigetInLayerWiget(filterController);
		updateGui();
	}

	public void delFilterWidgetInLayerWidget(FilterManager filterController) {
		chainOfLayerManagers.deFilterWidgetInLayerWidget(filterController);	
	}

	public void addLayerManager(LayerManager layerManager) {
		chainOfLayerManagers.addLayerManager(layerManager);	
	}
	
	public void deleteLayerManager(LayerManager layerManager) {
		layerManager.getLayerWindow().setVisible(false);
		chainOfLayerManagers.deleteLayerManager(layerManager);		
	}


	public void updateOpacityValue(int layerIndex, Float opacity) {
	}

	public void updateParametersValues(Filter filter, LinkedHashMap<String, Float> parameters) {	
		chainOfLayerManagers.getLayerManager(filter.getLayerIndex()).getFilterManager(filter.getFilterIndex()).updateParameterValues(parameters);
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

	public GuiManager getGuiManager() {
		return guiManager;
	}

	public ChainOfLayerManagers getChainOfLayerManagers() {
		return chainOfLayerManagers;
	}	
}


