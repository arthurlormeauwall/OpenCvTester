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

import com.opencvtester.controller.MainController;
import com.opencvtester.renderer.entity.ControlledFilter;

public class LabelledSlider extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	protected JSlider slider;
	protected int currentValue;
	protected JLabel  nameWidget;
	protected ControlledFilter filterToUpdate;
	protected JLabel value;
	protected Boolean emitSignal;

	protected MainController guiManager;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public LabelledSlider (String name, Float defaultValue, ControlledFilter filterToUpdate, MainController actionHistoryManager){
		emitSignal=true;
		this.filterToUpdate=filterToUpdate;
		this.guiManager=actionHistoryManager;
		slider = new JSlider ();

		slider.setMaximum(filterToUpdate.getFlags().sliderScale.get(name));
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
		    			 LabelledSlider.this.guiManager.setParametersAndSetHistory(LabelledSlider.this.filterToUpdate, nameWidget.getText(), slider.getValue()*0.01f);
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
						LabelledSlider.this.guiManager.setParametersAndSetHistory(LabelledSlider.this.filterToUpdate, nameWidget.getText(), slider.getValue()*0.01f);
					} catch (IOException e) {
						e.printStackTrace();
					}
					 LabelledSlider.this.guiManager.store();
				}
			}

			public void mouseReleased(MouseEvent e) {	
				if (emitSignal) { 
					LabelledSlider.this.guiManager.store();
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
