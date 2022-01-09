package com.opencvtester.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.opencvtester.guiManager.FilterManager;
import com.opencvtester.guiManager.GuiManager;
import com.opencvtester.guiManager.LayerManager;

public class LayerWindow  extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private LayerManager layerManager;
	private JPanel filterPanel;
	private GuiManager guiManager;
	private JList<String> filtersList;
    
	private JButton addButton;
	private JButton delButton;
	private JButton undoButton;
	private JButton redoButton;
 	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayerWindow (LayerManager layerController, GuiManager actionHistoryManager){
		super("Layer");
		this.guiManager=actionHistoryManager;
		this.layerManager=layerController;
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		filterPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		add(buttonPanel);
		add(filterPanel);
		
		addButton = new JButton("AddFilter");
		delButton = new JButton("delFilter");
		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");
		buttonPanel.add(addButton);
		buttonPanel.add(delButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(redoButton);
		
		filtersList=new JList<String>(actionHistoryManager.getFiltersName());
		filtersList.setVisible(false);
		filtersList.clearSelection();
		buttonPanel.add(filtersList);
		
		addListeners();
		
		 this.pack();
		updateGui();
		
		this.setVisible(true);
	}
	
	public void addListeners() {
		 undoButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	  
		    	  LayerWindow.this.guiManager.undo();
		      }
		    });
		 
		 redoButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	  LayerWindow.this.guiManager.redo();
		      }
		    });
		 addButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	
		    	LayerWindow.this.filtersList.setVisible(true);		    	
		    	LayerWindow.this.pack();
		
		      }
		    });
		 delButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
			    	int indexOfFitlerToDel= LayerWindow.this.layerManager.getLayer().getNumberOfFilters()-1;
					if(indexOfFitlerToDel>=0) {
						FilterManager filterToDel= LayerWindow.this.layerManager.getFilterManager(indexOfFitlerToDel);
						filterToDel.getFilterWidget().setVisible(false);
						 LayerWindow.this.pack();
						 LayerWindow.this.guiManager.deleteFilterInLayer(filterToDel);
					     LayerWindow.this.guiManager.store();
					}	 
		      }
		    });
		 
		 filtersList.addListSelectionListener (new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent listSelectionEvent) {

					 if ( !listSelectionEvent.getValueIsAdjusting()) {
						 	int thisLayerIndex = LayerWindow.this.layerManager.getLayer().getLayerIndex();
					    	int newFilterIndex=LayerWindow.this.layerManager.getLayer().getNumberOfFilters();
					    	String nameOfNewFilter= LayerWindow.this.filtersList.getSelectedValue();
					    	if (nameOfNewFilter!=null) {
					    		LayerWindow.this.guiManager.createAndAddFilterInLayer(thisLayerIndex, newFilterIndex, nameOfNewFilter);
					        	LayerWindow.this.guiManager.store();
					    	}					    	
					    	LayerWindow.this.filtersList.setVisible(false);
					    	LayerWindow.this.filtersList.clearSelection();
					    	LayerWindow.this.pack();
					 }
			    		
		
				}
		    });
	}


	/*
	 * FEATURES
	 */
	public void updateGui() {
		filterPanel.removeAll();
		
		int numberOfFiltersToAdd = layerManager.getLayer().getNumberOfFilters();
		for (int i=0;i<numberOfFiltersToAdd;i++) {
			filterPanel.add(layerManager.getFilterManager(i).getFilterWidget());
		}
		
		this.pack();		
	}
}
