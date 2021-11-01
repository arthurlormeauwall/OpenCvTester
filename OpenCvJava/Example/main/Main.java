package main;

import java.util.HashMap;
import java.util.Stack;
import fakeGui.FakeGui;
import userFilters.GrayScaleFilter;
import userFilters.BlueGreenRedMultiplierFilter;

public class Main 
{
    public static void main(String[] args){	
		FakeGui fakeGui= new FakeGui("assets/test.jpg"); 
		fakeGui.addFilterInDataBase("BgrMult", new BlueGreenRedMultiplierFilter());
		fakeGui.addFilterInDataBase("GrayScaleFilter", new GrayScaleFilter());
    
		Stack<String> controlIndexInDataBase= new Stack<String>();
		controlIndexInDataBase.push("BgrMult");
		
		fakeGui.addLayer(0, controlIndexInDataBase);
		fakeGui.setBypass(0, 0, false);

		
		HashMap<String, Float> floatParameters=new HashMap<String, Float>();
		floatParameters.put("BlueMult", 0.6f);
		floatParameters.put("GreenMult", 2f);
		floatParameters.put("RedMult", 0.9f);

		fakeGui.setParameters(0, 0, floatParameters);
		floatParameters.clear();
		
		fakeGui.undo();    
		fakeGui.undo();
		fakeGui.undo();
		fakeGui.addLayer(2, controlIndexInDataBase);
		fakeGui.undo();
		fakeGui.redo();
		fakeGui.redo();
		fakeGui.addLayer(1, controlIndexInDataBase);
		fakeGui.setBypass(1, 0, false);
		fakeGui.undo();
		fakeGui.redo();                                  
	
		fakeGui.undo();
		
		floatParameters.put("BlueMult", 0.6f);
		floatParameters.put("GreenMult", 2f);
		floatParameters.put("RedMult", 0.9f);
		fakeGui.setParameters(0, 0, floatParameters);	
		floatParameters.clear();
		
		controlIndexInDataBase.clear();
		controlIndexInDataBase.push("BgrMult");
		fakeGui.addLayer(1, controlIndexInDataBase);
		fakeGui.setBypass(1, 0, false);
		
		fakeGui.undo();                                 
		fakeGui.redo();
		
		floatParameters.put("BlueMult", 0.6f);
		floatParameters.put("GreenMult", 2f);
		floatParameters.put("RedMult", 0.9f);
		fakeGui.setParameters(1, 0, floatParameters);    
		floatParameters.clear();
		
		
		floatParameters.put("BlueMult", 0.6f);
		floatParameters.put("GreenMult", 2f);
		floatParameters.put("RedMult", 0.9f);
		fakeGui.setParameters(0, 0, floatParameters);  
		floatParameters.clear();
	
		fakeGui.undo();                                 
		fakeGui.undo();                                  
		fakeGui.undo();                                  
		fakeGui.redo();								
		fakeGui.redo();									 
    }
}