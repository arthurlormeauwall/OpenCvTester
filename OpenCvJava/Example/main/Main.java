package main;

import java.util.Stack;
import fakeGui.FakeGui;
import userAlgorithms.MultBgrControl;


public class Main 
{
    public static void main(String[] args){
    		
		FakeGui fakeGui= new FakeGui("assets/20210717_203824.jpg"); 
		fakeGui.addControlInDataBase(new MultBgrControl());
    		
		Stack<Integer> controlIndexInDataBase= new Stack<Integer>();
		controlIndexInDataBase.push(1);
		fakeGui.addLayer(0, controlIndexInDataBase);
		fakeGui.store();
		fakeGui.play();
    		
		fakeGui.setBypass(0, 0, false);
		fakeGui.play();
		fakeGui.undo();
		fakeGui.undo();
		fakeGui.undo();
		fakeGui.undo();
  
		fakeGui.play();
		fakeGui.redo();
		fakeGui.redo();
		fakeGui.redo();
		fakeGui.redo();
		fakeGui.redo();
		fakeGui.redo();
		fakeGui.redo();
		fakeGui.redo();
		fakeGui.redo();
		fakeGui.play();
    		
		Stack<Float> floatParameters=new Stack<Float>();
		floatParameters.push(0.6f);
		floatParameters.push(2f);
		floatParameters.push(0.9f);
    		
		fakeGui.setParameters(0, 0, floatParameters);	
		fakeGui.store();	
		fakeGui.play();
    }
}