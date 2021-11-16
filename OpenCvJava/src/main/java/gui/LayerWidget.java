package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import guiManager.ActionHistoryManager;
import guiManager.LayerManager;

public class LayerWidget extends JPanel 
{
	LayerManager layerController;
	JButton layerButton;
	
	private static final long serialVersionUID = 1L;
	
	public LayerWidget (LayerManager layerController, ActionHistoryManager actionHistoryManager){
		this.layerController=layerController;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel titlePanel= new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
		layerButton= new JButton("Layer");
		titlePanel.add(layerButton);
		add(titlePanel);
		
		JPanel insidePanel = new JPanel();
		insidePanel.setLayout(new BoxLayout(insidePanel, BoxLayout.LINE_AXIS));
        OpacitySlider  opacitySlider= new OpacitySlider("Opacity", 1f, layerController.getLayer().getOpacityFilter(), actionHistoryManager);
        insidePanel.add(opacitySlider);      

	    add(insidePanel);
	    
	    Border blackline = BorderFactory.createLineBorder(Color.black);
	    setBorder(blackline); 
	    
	    addListeners();
	   
	}
	
	public void addListeners() {
		 layerButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	  LayerWidget.this.layerController.getLayerWindow().setVisible(true);    	
		      }
		    });	
	}
}
