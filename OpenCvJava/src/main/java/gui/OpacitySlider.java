package gui;

import java.io.IOException;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import baseClasses.filter.FilterControlledByFloat;
import guiController.GuiManager;
import renderingEngine.GroupsId;

public class OpacitySlider extends LabelledSlider {

	private static final long serialVersionUID = 1L;

	public OpacitySlider(String name, Float defaultValue, FilterControlledByFloat widgetToUpdate, GuiManager guiManager) {
		super(name, defaultValue, widgetToUpdate, guiManager);
		slider.setMaximum(100);
	}
	
	@Override
	protected void addListener() {
		if (emitSignal) {
			 slider.addChangeListener(new ChangeListener() {
			      public void stateChanged(ChangeEvent event)   {
			    	  OpacitySlider.this.value.setText(String.valueOf(slider.getValue()*0.01f)); // TODO : change this
			    	  try {
			    		  OpacitySlider.this.guiManager.setParameters(OpacitySlider.this.widgetToUpdate.getId(), nameWidget.getText(), slider.getValue()*0.01f, GroupsId.LAYER);
					} catch (IOException e) {
						e.printStackTrace();
					}
			      }
			    });
		}		
		
	}

}
