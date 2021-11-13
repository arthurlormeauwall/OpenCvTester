package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import guiManager.FilterManager;
import guiManager.ActionHistoryManager;
import guiManager.LayerManager;

public class LayerWindow  extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private LayerManager layerManager;
	private JPanel filterPanel;
	private ActionHistoryManager actionHistoryManager;
	private JList<String> filtersList;
	
	public LayerWindow (LayerManager layerController, ActionHistoryManager actionHistoryManager){
		super("Layer");
		this.actionHistoryManager=actionHistoryManager;
		this.layerManager=layerController;
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		filterPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		add(buttonPanel);
		add(filterPanel);
		
		JButton addButton = new JButton("AddFilter");
		JButton delButton = new JButton("delFilter");
		JButton undoButton = new JButton("Undo");
		JButton redoButton = new JButton("Redo");
		buttonPanel.add(addButton);
		buttonPanel.add(delButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(redoButton);
		
		filtersList=new JList<String>(actionHistoryManager.getFiltersName());
		filtersList.setVisible(false);
		filtersList.clearSelection();
		buttonPanel.add(filtersList);
		
		
		undoButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	
		      }
		    });
		 redoButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	
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
		    	  int indexOfFitlerToDel= LayerWindow.this.layerManager.getLayer().getNumberOfFilters()-2;
		    	  FilterManager filterToDel= LayerWindow.this.layerManager.getFilterController(indexOfFitlerToDel);
		    	  filterToDel.getFilterWidget().setVisible(false);
		    	  LayerWindow.this.pack();
		    	  LayerWindow.this.actionHistoryManager.delFilterInLayer(filterToDel);
		      }
		    });
		 
		 filtersList.addListSelectionListener (new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent listSelectionEvent) {

					 if ( !listSelectionEvent.getValueIsAdjusting()) {
						 	int thisLayerIndex = LayerWindow.this.layerManager.getLayer().getId().get()[0];
					    	int newFilterIndex=LayerWindow.this.layerManager.getLayer().getNumberOfFilters()-1;
					    	String nameOfNewFilter= LayerWindow.this.filtersList.getSelectedValue();
					    	if (nameOfNewFilter!=null) {
					    		LayerWindow.this.actionHistoryManager.createAndAddFilterInLayer(thisLayerIndex, newFilterIndex, nameOfNewFilter);
					    	}					    	
					    	LayerWindow.this.filtersList.setVisible(false);
					    	LayerWindow.this.filtersList.clearSelection();
					    	LayerWindow.this.pack();
					 }
			    		
		
				}
		    });
		
		
		 this.pack();
		updateGui();
		
		this.setVisible(true);
	}

	public void updateGui() {
		filterPanel.removeAll();
		
		int numberOfFiltersToAdd = layerManager.getLayer().getNumberOfFilters()-1;
		for (int i=0;i<numberOfFiltersToAdd;i++) {
			filterPanel.add(layerManager.getFilterController(i).getFilterWidget());
		}
		
		this.pack();
		
	}

}
