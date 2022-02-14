package com.opencvtester.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.opencvtester.controller.MainController;
import com.opencvtester.controller.layer.LayerController;

public class LayerWidget extends JPanel 
{
	private LayerController layerController;
	private JButton layerButton;
	private OpacitySlider  opacitySlider;
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public LayerWidget (LayerController layerController, MainController actionHistoryManager){
		this.layerController=layerController;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel titlePanel= new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
		layerButton= new JButton("Open");
		titlePanel.add(layerButton);
		add(titlePanel);
		
		JPanel insidePanel = new JPanel();
		insidePanel.setLayout(new BoxLayout(insidePanel, BoxLayout.LINE_AXIS));
        opacitySlider= new OpacitySlider("Opacity", 1f, layerController.getLayer().getOpacityFilter(), actionHistoryManager);
        insidePanel.add(opacitySlider);      

	    add(insidePanel);
	    
	    Border blackline = BorderFactory.createLineBorder(Color.black);
	    setBorder(blackline); 
	    
	    addListeners();
	   
	}
	
	public void addListeners() {
		 layerButton.addActionListener((ActionEvent event)->{
		    	  LayerWidget.this.layerController.getLayerWindow().setVisible(true);    	
		     });	
	}
	
	public void setOpacitySlider(Float opacity) {
		opacitySlider.getSlider().setValue((int)(opacity*100));
	}
}
