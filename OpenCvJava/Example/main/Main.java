package main;

import java.util.Stack;
import fakeGui.FakeGui;
import userAlgorithms.GrayScaleControl;
import userAlgorithms.MultBgrControl;


public class Main 
{
    public static void main(String[] args){
    		
		FakeGui fakeGui= new FakeGui("assets/20210717_203824.jpg"); 
		fakeGui.addControlInDataBase(new MultBgrControl());
		fakeGui.addControlInDataBase(new GrayScaleControl());
    		
		Stack<Integer> controlIndexInDataBase= new Stack<Integer>();
		controlIndexInDataBase.push(0);
		fakeGui.addLayer(0, controlIndexInDataBase);
		fakeGui.setBypass(0, 0, false);
		
		
		controlIndexInDataBase.clear();
		controlIndexInDataBase.push(1);
		
		fakeGui.addLayer(1, controlIndexInDataBase);
		fakeGui.setBypass(1, 0, false);
		
		fakeGui.undo();
		
		fakeGui.redo();
		
		fakeGui.addControlInLayer(0, 1, 0);
		fakeGui.setBypass(0, 1, false);
		
		fakeGui.undo();
		
		fakeGui.undo();
		
		fakeGui.undo();
		
		fakeGui.redo();
		
		fakeGui.redo();
		
		fakeGui.setBypass(0, 0, true);
		Stack<Float> floatParameters=new Stack<Float>();
		floatParameters.push(0.6f);
		floatParameters.push(2f);
		floatParameters.push(0.9f);
    		
		fakeGui.setParameters(0, 0, floatParameters);	
		fakeGui.setBypass(0, 0, false);
    }
}
/*
source.play();
MultBgrControl test= new MultBgrControl();
test.setSource(dest);
test.setDest(dest);
test.setBypass(false);
test.compute();
source.play();
dest.play();*/