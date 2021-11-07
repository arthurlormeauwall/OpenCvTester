package gui.widget;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LabelledSlider extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JSlider slider;
	private JLabel  name;
	private JLabel value;
	
	public LabelledSlider (String name, Float defaultValue){
		slider = new JSlider ();
		slider.setValue(Math.round(defaultValue*100));
		slider.setMaximum(200);
		slider.setOrientation(JSlider.VERTICAL);
		this.name = new JLabel(name);
		this.value = new JLabel( String.valueOf(defaultValue));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS ));
		add(this.name);
		add(slider);
		add(value);
		
		 slider.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent event) {
		    	  LabelledSlider.this.value.setText(String.valueOf(slider.getValue()));        
		      }
		    });
	}
	

}
