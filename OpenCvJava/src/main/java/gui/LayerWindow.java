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
	private LayerManager layerController;
	private JPanel filterPanel;
	private ActionHistoryManager guiManager;
	private JList<String> filtersList;
	
	public LayerWindow (LayerManager layerController, ActionHistoryManager guiManager){
		super("Layer");
		this.guiManager=guiManager;
		this.layerController=layerController;
		
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
		
		filtersList=new JList<String>(guiManager.getFiltersName());
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
		    	  int indexOfFitlerToDel= LayerWindow.this.layerController.getLayer().getNumberOfFilters()-2;
		    	  FilterManager filterToDel= LayerWindow.this.layerController.getFilterController(indexOfFitlerToDel);
		    	  filterToDel.getFilterWidget().setVisible(false);
		    	  LayerWindow.this.pack();
		    	  LayerWindow.this.guiManager.delFilterInLayer(filterToDel);
		      }
		    });
		 
		 filtersList.addListSelectionListener (new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent listSelectionEvent) {

					 if ( !listSelectionEvent.getValueIsAdjusting()) {
						 int thisLayerIndex = LayerWindow.this.layerController.getLayer().getId().get()[0];
					    	int newFilterIndex=LayerWindow.this.layerController.getLayer().getNumberOfFilters()-1;
					    	String nameOfNewFilter= LayerWindow.this.filtersList.getSelectedValue();
					    	LayerWindow.this.guiManager.createAndAddFilterInLayer(thisLayerIndex, newFilterIndex, nameOfNewFilter);
					    	LayerWindow.this.filtersList.setVisible(false);
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
		
		int numberOfFiltersToAdd = layerController.getLayer().getNumberOfFilters()-1;
		for (int i=0;i<numberOfFiltersToAdd;i++) {
			filterPanel.add(layerController.getFilterController(i).getFilterWidget());
		}
		
		this.pack();
		
	}

}
