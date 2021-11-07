package main;

import java.util.HashMap;
import java.util.Stack;

import gui.GuiManager;
import userFilters.GrayScaleFilter;
import userFilters.BlueGreenRedMultiplierFilter;

public class Main 
{
    public static void main(String[] args){	
		GuiManager fakeGui= new GuiManager("assets/test.jpg"); 
		fakeGui.addFilterInDataBase("BgrMult", new BlueGreenRedMultiplierFilter());
		fakeGui.addFilterInDataBase("GrayScaleFilter", new GrayScaleFilter());
    
		Stack<String> filtersName= new Stack<String>();
		filtersName.push("BgrMult");
		
		fakeGui.addLayer(0, filtersName);
		
		fakeGui.setBypass(0, 0, false);

		
		HashMap<String, Float> floatParameters=new HashMap<String, Float>();
		floatParameters.put("BlueMult", 0.6f);
		floatParameters.put("GreenMult", 2f);
		floatParameters.put("RedMult", 0.9f);

		fakeGui.setParameters(0, 0, floatParameters);
		floatParameters.clear();
		
		fakeGui.undo();   
		fakeGui.redo();
		
		floatParameters.put("BlueMult", 1.2f);
		floatParameters.put("GreenMult", 0.5f);
		floatParameters.put("RedMult", 2f);
		fakeGui.setParameters(0, 0, floatParameters);	
		floatParameters.clear();		
		
		filtersName.clear();
		filtersName.push("GrayScaleFilter");
		fakeGui.addLayer(1, filtersName);
		fakeGui.setBypass(1, 0, false);		
    }
}