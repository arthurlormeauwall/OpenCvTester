package gui;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import baseClasses.filter.FilterControlledByFloat;
import guiManager.ActionHistoryManager;

public class OpacitySlider extends LabelledSlider {

	private static final long serialVersionUID = 1L;

	public OpacitySlider(String name, Float defaultValue, FilterControlledByFloat widgetToUpdate, ActionHistoryManager actionHistoryManager) {
		super(name, defaultValue, widgetToUpdate, actionHistoryManager);
		slider.setMaximum(100);
	}
	
	@Override
	protected void addListeners() {
		if (emitSignal) {
			 slider.addChangeListener(new ChangeListener() {
			      public void stateChanged(ChangeEvent event)   {
			    	  OpacitySlider.this.value.setText(String.valueOf(slider.getValue()*0.01f)); // TODO : change this
			    	  
			    		  OpacitySlider.this.actionHistoryManager.setOpacity(OpacitySlider.this.widgetToUpdate.getId().get()[0], slider.getValue()*0.01f);
			      }
			    });
		}		
		
	}
}
