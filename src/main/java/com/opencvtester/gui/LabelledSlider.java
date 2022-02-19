package com.opencvtester.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import com.opencvtester.app.MainController;
import com.opencvtester.data.interfaces.FilterDataInterface;
import com.opencvtester.renderer.ControlledFilter;

public class LabelledSlider extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	protected JSlider slider;
	protected int currentValue;
	protected JLabel  nameWidget;
	protected ControlledFilter filter;
	protected JLabel value;
	protected Boolean emitSignal;

	protected MainController mainController;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public LabelledSlider (String name, Float defaultValue, ControlledFilter filterToUpdate, MainController actionHistoryManager){
		emitSignal=true;
		this.filter=filterToUpdate;
		this.mainController=actionHistoryManager;
		slider = new JSlider ();

		slider.setMaximum(filterToUpdate.getFilterData().getSliderScale().get(name));   
		slider.setOrientation(JSlider.VERTICAL);
		slider.setValue(Math.round(filterToUpdate.getParameter(name)*100));
		this.nameWidget = new JLabel(name);
		this.value = new JLabel( String.valueOf(defaultValue));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS ));
		add(this.nameWidget);
		add(slider);
		add(value);
		
		addListeners();
		
		currentValue=slider.getValue();
	}
	
	protected void addListeners() {
		 slider.addChangeListener((ChangeEvent event)->{
		    	  LabelledSlider.this.value.setText(String.valueOf(df.format(slider.getValue()*0.01f))); 
		    	  try {
		    		 currentValue=slider.getValue();
		    		 if (emitSignal) {
		    			 LabelledSlider.this.mainController.setParametersAndSetHistory(LabelledSlider.this.filter, nameWidget.getText(), slider.getValue()*0.01f);
		    		 }
					 
				} catch (IOException e) {
					e.printStackTrace();
				}
		     });
		 
		 slider.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent event) {
			}

			public void mousePressed(MouseEvent event) {
				if (emitSignal) { 
					try {
						LabelledSlider.this.mainController.setParametersAndSetHistory(LabelledSlider.this.filter, nameWidget.getText(), slider.getValue()*0.01f);
					} catch (IOException e) {
						e.printStackTrace();
					}
					 LabelledSlider.this.mainController.store();
				}
			}

			public void mouseReleased(MouseEvent e) {	
				if (emitSignal) { 
					LabelledSlider.this.mainController.store();
				}
			}

			public void mouseEntered(MouseEvent event) {
				
				
			}

			public void mouseExited(MouseEvent e) {
				
			}
		    });	
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public void setEmitSignal(Boolean emitSignal) {
		this.emitSignal=emitSignal;
	}

	public JSlider getSlider() {
		return slider;
	}
}
