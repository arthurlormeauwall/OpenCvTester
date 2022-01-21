package com.opencvtester.gui;


import java.text.DecimalFormat;

import javax.swing.event.ChangeEvent;

import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.guiManager.GuiManager;

public class OpacitySlider extends LabelledSlider {

	private static final long serialVersionUID = 1L;
	private static final DecimalFormat df = new DecimalFormat("0.00");

	/*
	 * CONSTRUCTOR & INITS
	 */
	public OpacitySlider(String name, Float defaultValue, FilterControlledByFloat widgetToUpdate, GuiManager actionHistoryManager) {
		super(name, defaultValue, widgetToUpdate, actionHistoryManager);
	}
	
	@Override
	protected void addListeners() {
		if (emitSignal) {
			 slider.addChangeListener((ChangeEvent event)->{
			    	  OpacitySlider.this.value.setText(String.valueOf(df.format(slider.getValue()*0.01f))); // TODO : change this
			    	  
			    		  OpacitySlider.this.guiManager.setOpacity(OpacitySlider.this.filterToUpdate, slider.getValue()*0.01f);
			     });
		}		
		
	}
}
