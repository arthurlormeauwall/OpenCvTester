package main;

import java.util.Stack;
import application.App;
import fakeGui.EventGenerator;


public class Main 
{
    public static void main(String[] args){

			EventGenerator fakeGui= new EventGenerator(new App(), "assets/20210717_203824.jpg"); 
    		
    		Stack<Integer> controlIndexInDataBase= new Stack<Integer>();
    		controlIndexInDataBase.push(1);
    		fakeGui.addLayer(0, controlIndexInDataBase);
    		fakeGui.store();
    		fakeGui.play();
    		fakeGui.setBypass(0, 0, false);
    		fakeGui.play();
    		fakeGui.undo();
    		fakeGui.play();
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