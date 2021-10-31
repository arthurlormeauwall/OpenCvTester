package main;

import java.util.Stack;
import fakeGui.FakeGui;
import userFilters.GrayScaleFilter;
import userFilters.BlueGreenRedMultiplierFilter;

public class Main 
{
    public static void main(String[] args){	
		FakeGui fakeGui= new FakeGui("assets/test.jpg"); 
		fakeGui.addFilterInDataBase(new BlueGreenRedMultiplierFilter());
		fakeGui.addFilterInDataBase(new GrayScaleFilter());
    
		Stack<Integer> controlIndexInDataBase= new Stack<Integer>();
		controlIndexInDataBase.push(0);
		
		fakeGui.addLayer(0, controlIndexInDataBase);
		fakeGui.setBypass(0, 0, false);

		
		Stack<Float> floatParameters=new Stack<Float>();
		floatParameters.push(0.6f);
		floatParameters.push(2f);
		floatParameters.push(0.9f);

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
		
		floatParameters.push(1f);
		floatParameters.push(0.3f);
		floatParameters.push(1.6f);
		fakeGui.setParameters(0, 0, floatParameters);	
		floatParameters.clear();
		
		controlIndexInDataBase.clear();
		controlIndexInDataBase.push(0);
		fakeGui.addLayer(1, controlIndexInDataBase);
		fakeGui.setBypass(1, 0, false);
		
		fakeGui.undo();                                 
		fakeGui.redo();
		
		floatParameters.push(2f);
		floatParameters.push(0.3f);
		floatParameters.push(0f);
		fakeGui.setParameters(1, 0, floatParameters);    
		floatParameters.clear();
		
		
		floatParameters.push(1.2f);
		floatParameters.push(1.6f);
		floatParameters.push(0.9f);
		fakeGui.setParameters(0, 0, floatParameters);  
		floatParameters.clear();
	
		fakeGui.undo();                                 
		fakeGui.undo();                                  
		fakeGui.undo();                                  
		fakeGui.redo();								
		fakeGui.redo();									 
    }
}