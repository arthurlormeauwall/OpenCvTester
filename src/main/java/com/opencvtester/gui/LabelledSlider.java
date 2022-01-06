package com.opencvtester.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.guiManager.GuiManager;

public class LabelledSlider extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	protected JSlider slider;
	protected int currentValue;
	protected JLabel  nameWidget;
	protected FilterControlledByFloat widgetToUpdate;
	protected JLabel value;
	protected Boolean emitSignal;

	protected GuiManager guiManager;
	
	public LabelledSlider (String name, Float defaultValue, FilterControlledByFloat widgetToUpdate, GuiManager actionHistoryManager){
		emitSignal=true;
		this.widgetToUpdate=widgetToUpdate;
		this.guiManager=actionHistoryManager;
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
		
		addListeners();
		
		currentValue=slider.getValue();
	}
	
	protected void addListeners() {
		if (emitSignal) {
			 slider.addChangeListener(new ChangeListener() {
			      public void stateChanged(ChangeEvent event)   {
			    	  LabelledSlider.this.value.setText(String.valueOf(slider.getValue()*0.01f)); // TODO : change this
			    	  try {
			    		 currentValue=slider.getValue();
						 LabelledSlider.this.guiManager.setParameters(LabelledSlider.this.widgetToUpdate, nameWidget.getText(), slider.getValue()*0.01f);	
					} catch (IOException e) {
						e.printStackTrace();
					}
			      }
			    });
			 slider.addMouseListener(new MouseListener() {

				public void mouseClicked(MouseEvent event) {
					
					
				}

				public void mousePressed(MouseEvent event) {
					 try {
						 LabelledSlider.this.guiManager.setParameters(LabelledSlider.this.widgetToUpdate, nameWidget.getText(), currentValue*0.01f);
						 LabelledSlider.this.guiManager.store();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}

				public void mouseReleased(MouseEvent e) {
				
					
				}

				public void mouseEntered(MouseEvent event) {
					
					
				}

				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			    });
		}		
		
	}

	public void setEmitSignal(Boolean emitSignal) {
		this.emitSignal=emitSignal;
	}

	public JSlider getSlider() {
		
		return slider;
	}
}
