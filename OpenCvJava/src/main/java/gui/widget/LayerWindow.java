package gui.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import baseClasses.filter.FilterControlledByFloat;
import gui.GuiManager;
import gui.LayerController;
import renderingEngine.Layer;

public class LayerWindow  extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private LayerController layerController;
	private JPanel filterPanel;
	GuiManager guiManager;
	
	public LayerWindow (LayerController layerController, GuiManager guiManager){
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
		    	
		      }
		    });
		 delButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	
		      }
		    });
		
		
		
		updateGui();
		
		this.setVisible(true);
	}

	public void updateGui() {
		filterPanel.removeAll();
		
		int numberOfFiltersToAdd = layerController.getLayer().getNumberOfFilters()-1;
		for (int i=0;i<numberOfFiltersToAdd;i++) {
			filterPanel.add(layerController.getFilterController(i).getFilterWidget());
		}
		
	}

}
