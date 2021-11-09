package gui.widget;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import baseClasses.filter.FilterControlledByFloat;
import gui.GuiManager;
import renderingEngine.Layer;

public class LayerWidget extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	public LayerWidget (Layer layer, GuiManager guiManager){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel titlePanel= new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
		
		
		JLabel filterName= new JLabel("Layer");
		
		titlePanel.add(filterName);
		add(titlePanel);
		
		JPanel insidePanel = new JPanel();
		insidePanel.setLayout(new BoxLayout(insidePanel, BoxLayout.LINE_AXIS));

        LabelledSlider opacitySlider= new LabelledSlider("Opacity", 1f, layer.getOpacityFilter(), guiManager);
        insidePanel.add(opacitySlider);      
	     
	
	    add(insidePanel);
	    
	    Border blackline = BorderFactory.createLineBorder(Color.black);
	    setBorder(blackline); 
	}

}
