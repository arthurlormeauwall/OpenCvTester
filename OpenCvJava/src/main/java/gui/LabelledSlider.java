package gui;

import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import baseClasses.filter.FilterControlledByFloat;
import guiController.GuiManager;

public class LabelledSlider extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	protected JSlider slider;
	private JLabel  nameWidget;
	private FilterControlledByFloat widgetToUpdate;
	private JLabel value;
	protected Boolean emitSignal;

	private GuiManager guiManager;
	
	public LabelledSlider (final String name, Float defaultValue, FilterControlledByFloat widgetToUpdate, GuiManager guiManager){
		emitSignal=true;
		this.widgetToUpdate=widgetToUpdate;
		this.guiManager=guiManager;
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
		
		if (emitSignal) {
			 slider.addChangeListener(new ChangeListener() {
			      public void stateChanged(ChangeEvent event)   {
			    	  LabelledSlider.this.value.setText(String.valueOf(slider.getValue()*0.01f)); // TODO : change this
			    	  try {
						LabelledSlider.this.guiManager.setParameters(LabelledSlider.this.widgetToUpdate.getId(), name, slider.getValue()*0.01f);
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
