package gui.widget;

import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import baseClasses.filter.FilterControlledByFloat;
import gui.GuiManager;

public class LabelledSlider extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JSlider slider;
	private JLabel  nameWidget;
	private String name;
	private FilterControlledByFloat widgetToUpdate;
	private JLabel value;

	private GuiManager guiManager;
	
	public LabelledSlider (final String name, Float defaultValue, FilterControlledByFloat widgetToUpdate, GuiManager guiManager){
		this.widgetToUpdate=widgetToUpdate;
		this.name=name;
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
