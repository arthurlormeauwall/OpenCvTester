package com.opencvtester.gui;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import com.opencvtester.app.MainController;
import com.opencvtester.data.interfaces.FilterDataInterface;
import com.opencvtester.gui.controller.FilterController;
import com.opencvtester.gui.controller.LayerController;

public class LayerWindow  extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	private LayerController layerController;
	private JPanel filterPanel;
	private MainController mainController;
	private JList<String> filtersList;
    
	private JButton addFilterButton;
	private JButton delFilterButton;
	private JButton undoButton;
	private JButton redoButton;
 	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayerWindow (LayerController layerManager, MainController actionHistoryManager){
		super("Layer");
		
		
		this.mainController=actionHistoryManager;
		this.layerController=layerManager;
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		filterPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		add(buttonPanel);
		add(filterPanel);
		
		addFilterButton = new JButton("Add");
		delFilterButton = new JButton("Delete");
		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");
		buttonPanel.add(addFilterButton);
		buttonPanel.add(delFilterButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(redoButton);
		
		filtersList=new JList<String>(actionHistoryManager.getFiltersName());
		filtersList.setVisible(false);
		filtersList.clearSelection();
		buttonPanel.add(filtersList);
		
		addListeners();
		
		 this.pack();
		updateGui();
		
		this.setVisible(false);
	}
	
	public void addListeners() {
		 undoButton.addActionListener((ActionEvent event)->{
			 	LayerWindow.this.mainController.undo();
			});

		 redoButton.addActionListener((ActionEvent event)-> {
	    	  	LayerWindow.this.mainController.redo();
	  		});
		 
		 addFilterButton.addActionListener((ActionEvent event)->{	    	
		    	LayerWindow.this.filtersList.setVisible(true);		    	
		    	LayerWindow.this.pack();
	    	});
		 
		 delFilterButton.addActionListener((ActionEvent event) -> {
		    	int indexOfFitlerToDel= LayerWindow.this.layerController.getLayer().getNumberOfFilters()-1;
				if(indexOfFitlerToDel>=0) {
					FilterController filterToDel= LayerWindow.this.layerController.getFilterManager(indexOfFitlerToDel);
					filterToDel.getFilterWidget().setVisible(false);
					 LayerWindow.this.pack();
					 LayerWindow.this.mainController.deleteFilterAndSetHistory(filterToDel.getFilter().layerIndex(), 
							 												filterToDel.getFilter().filterIndex(),
							 											    filterToDel.getFilter().getFilterData().getName());
				     LayerWindow.this.mainController.store();
				}	 
		    });

		 filtersList.addListSelectionListener ((listSelectionEvent)-> {
					 if ( !listSelectionEvent.getValueIsAdjusting()) {
						 	int thisLayerIndex = LayerWindow.this.layerController.getLayer().getFilterData().getIndexData().layerIndex();
					    	int newFilterIndex=LayerWindow.this.layerController.getLayer().getNumberOfFilters();
					    	String nameOfNewFilter= LayerWindow.this.filtersList.getSelectedValue();
					    	if (nameOfNewFilter!=null) {
					    		LayerWindow.this.mainController.createAddFilterAndSetHistory(thisLayerIndex, newFilterIndex, nameOfNewFilter);
					        	LayerWindow.this.mainController.store();
					    	}					    	
					    	LayerWindow.this.filtersList.setVisible(false);
					    	LayerWindow.this.filtersList.clearSelection();
					    	LayerWindow.this.pack();
					 }
			 });
	}


	/*
	 * FEATURES
	 */
	public void updateGui() {
		filterPanel.removeAll();
		
		int numberOfFiltersToAdd = layerController.getLayer().getNumberOfFilters();
		for (int i=0;i<numberOfFiltersToAdd;i++) {
			filterPanel.add(layerController.getFilterManager(i).getFilterWidget());
		}
		
		this.pack();		
	}
}
