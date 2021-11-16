package gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.awt.Color;

import baseClasses.filter.FilterControlledByFloat;
import guiManager.ActionHistoryManager;

public class FilterWidget extends JPanel
{
	private HashMap<String, LabelledSlider> sliders;
	private FilterControlledByFloat sourceFilter;
	private JCheckBox bypass;
	
	private static final long serialVersionUID = 1L;
	
	public FilterWidget (FilterControlledByFloat sourceFilter, ActionHistoryManager actionHistoryManager){
		this.sourceFilter= sourceFilter;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel titlePanel= new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
		JLabel filterName= new JLabel(sourceFilter.getFilterName());
		titlePanel.add(filterName);
		bypass= new JCheckBox("Bypass", false);
		titlePanel.add(bypass);
		add(titlePanel);
		
		JPanel insidePanel = new JPanel();
		insidePanel.setLayout(new BoxLayout(insidePanel, BoxLayout.LINE_AXIS));
		Iterator<Entry<String, Float>> parametersIterator= sourceFilter.getParameters().entrySet().iterator();
	    while (parametersIterator.hasNext()) {
	        HashMap.Entry<String, Float> parameterEntry= (HashMap.Entry<String, Float>) parametersIterator.next();
	        String parameterName= parameterEntry.getKey();
	        Float parameterValue= parameterEntry.getValue();
	        LabelledSlider sliderToAdd= new LabelledSlider(parameterName, parameterValue, this.getFilter(), actionHistoryManager); 
	        insidePanel.add(sliderToAdd);      
	    } 
	  
	    add(insidePanel);
	    
	    Border blackline = BorderFactory.createLineBorder(Color.black);
	    setBorder(blackline); 
	    
	    addListeneres();
	}
	
	public void addListeneres() {
		
	}

	public void setParameters(String name, Integer value) {
		sourceFilter.setParameter(name, value.floatValue());	
	}
	
	public FilterControlledByFloat getFilter() {
		return sourceFilter;
	}
}
