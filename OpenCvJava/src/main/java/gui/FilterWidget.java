package gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import baseClasses.filter.FilterControlledByFloat;
import guiManager.GuiManager;

public class FilterWidget extends JPanel
{
	private FilterControlledByFloat sourceFilter;
	private JCheckBox bypassBox;
	private GuiManager actionHistoryManager;
	private Boolean bypass;
	private LinkedHashMap<String, LabelledSlider> sliders;
	
	private static final long serialVersionUID = 1L;
	
	public FilterWidget (FilterControlledByFloat sourceFilter, GuiManager actionHistoryManager){
		sliders= new LinkedHashMap<String, LabelledSlider>();
		bypass=sourceFilter.isbypass();
		this.sourceFilter= sourceFilter;
		this.actionHistoryManager=actionHistoryManager;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel titlePanel= new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
		JLabel filterName= new JLabel(sourceFilter.getFilterName());
		titlePanel.add(filterName);
		bypassBox= new JCheckBox("Bypass", false);
		titlePanel.add(bypassBox);
		add(titlePanel);
		
		JPanel insidePanel = new JPanel();
		insidePanel.setLayout(new BoxLayout(insidePanel, BoxLayout.LINE_AXIS));
		Iterator<Entry<String, Float>> parametersIterator= sourceFilter.getParameters().entrySet().iterator();
	    while (parametersIterator.hasNext()) {
	        HashMap.Entry<String, Float> parameterEntry= (HashMap.Entry<String, Float>) parametersIterator.next();
	        String parameterName= parameterEntry.getKey();
	        Float parameterValue= parameterEntry.getValue();
	        LabelledSlider sliderToAdd= new LabelledSlider(parameterName, parameterValue, this.getFilter(), actionHistoryManager); 
	        sliders.put(parameterName, sliderToAdd);
	        insidePanel.add(sliderToAdd);      
	    } 
	  
	    add(insidePanel);
	    
	    Border blackline = BorderFactory.createLineBorder(Color.black);
	    setBorder(blackline); 
	    
	    addListeneres();
	}
	
	public void addListeneres() {
		bypassBox.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	  if (bypass) {
		    		  FilterWidget.this.actionHistoryManager.setBypass(sourceFilter.getId().layerIndex(), sourceFilter.getId().filterIndex(), false);
		    		  bypass=false;
		    	  }
		    	  else  {
		    		  FilterWidget.this.actionHistoryManager.setBypass(sourceFilter.getId().layerIndex(), sourceFilter.getId().filterIndex(), true);
		    		  bypass=true;
		    	  }
		      }
		    });		
	}

	public void setParameters(String name, Integer value) {
		sourceFilter.setParameters(name, value.floatValue());	
	}
	
	public FilterControlledByFloat getFilter() {
		return sourceFilter;
	}
	
	public void setEmitSignal(Boolean emitSignal) {
		Iterator<Entry<String, LabelledSlider>> parametersIterator= sliders.entrySet().iterator();
	    while (parametersIterator.hasNext()) {
	        HashMap.Entry<String, LabelledSlider> parameterEntry= (HashMap.Entry<String, LabelledSlider>) parametersIterator.next();
	        String parameterName= parameterEntry.getKey();
	        sliders.get(parameterName).setEmitSignal(emitSignal);
	    } 
	}

	public void updateParameterValues(LinkedHashMap<String, Float> parameters) {	
		Stack<String> names=new Stack<String>();
		Stack<Float> values = new Stack<Float>();
		
		for (Map.Entry<String, Float> entry : parameters.entrySet()) {
		  names.push(entry.getKey());
		  values.push(entry.getValue());
		}
		
		for (int i=0;i<names.size();i++) {
			sliders.get(names.get(i)).getSlider().setValue(((Float)(values.get(i)*100)).intValue()); 
		}
	}
}
