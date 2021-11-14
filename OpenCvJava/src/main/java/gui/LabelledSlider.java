package gui;

import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import baseClasses.filter.FilterControlledByFloat;
import guiManager.ActionHistoryManager;

public class LabelledSlider extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	protected JSlider slider;
	protected JLabel  nameWidget;
	protected FilterControlledByFloat widgetToUpdate;
	protected JLabel value;
	protected Boolean emitSignal;

	protected ActionHistoryManager actionHistoryManager;
	
	public LabelledSlider (String name, Float defaultValue, FilterControlledByFloat widgetToUpdate, ActionHistoryManager actionHistoryManager){
		emitSignal=true;
		this.widgetToUpdate=widgetToUpdate;
		this.actionHistoryManager=actionHistoryManager;
		slider = new JSlider ();
		slider.setValue(Math.round(defaultValue*100));
		slider.setMaximum(200);
		slider.setOrientation(JSlider.VERTICAL);
		this.nameWidget = new JLabel(name);
		this.value = new JLabel( String.valueOf(defaultValue));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS ));
		add(this.nameWidget);
		add(slider);
		add(value);
		
		addListener();

	}
	
	protected void addListener() {
		if (emitSignal) {
			 slider.addChangeListener(new ChangeListener() {
			      public void stateChanged(ChangeEvent event)   {
			    	  LabelledSlider.this.value.setText(String.valueOf(slider.getValue()*0.01f)); // TODO : change this
			    	  try {
						LabelledSlider.this.actionHistoryManager.setParameters(LabelledSlider.this.widgetToUpdate.getId(), nameWidget.getText(), slider.getValue()*0.01f);
					} catch (IOException e) {
						e.printStackTrace();
					}
			      }
			    });
		}		
		
	}

	public void setEmitSignal(Boolean emitSignal) {
		this.emitSignal=emitSignal;
	}
	
	
	

}
