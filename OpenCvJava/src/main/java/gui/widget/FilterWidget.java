package gui.widget;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import baseClasses.filter.FilterControlledByFloat;

public class FilterWidget extends JPanel
{
	HashMap<String, LabelledSlider> sliders;
	
	private static final long serialVersionUID = 1L;
	
	public FilterWidget (FilterControlledByFloat sourceFilter){
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		Iterator<Entry<String, Float>> parametersIterator= sourceFilter.getParameters().entrySet().iterator();
	    while (parametersIterator.hasNext()) {
	        HashMap.Entry<String, Float> parameterEntry= (HashMap.Entry<String, Float>) parametersIterator.next();
	        String parameterName= parameterEntry.getKey();
	        Float parameterValue= parameterEntry.getValue();
	        LabelledSlider sliderToAdd= new LabelledSlider(parameterName, parameterValue);
	        add(sliderToAdd);      
	    } 
	}
}
