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
		fakeGui.store();
		fakeGui.play();
		
		controlIndexInDataBase.clear();
		controlIndexInDataBase.push(1);
		
		fakeGui.addLayer(1, controlIndexInDataBase);
		fakeGui.setBypass(1, 0, false);
		fakeGui.store();
		fakeGui.play();
		
		
		fakeGui.setBypass(0, 0, false);
		fakeGui.play();
		
		fakeGui.undo();
		fakeGui.play();
		
		fakeGui.redo();
		fakeGui.play();
		
		fakeGui.addControlInLayer(0, 1, 1);
		fakeGui.store();
		fakeGui.setBypass(0, 1, false);
		fakeGui.play();
		
		fakeGui.undo();
		fakeGui.play();
		
		fakeGui.undo();
		fakeGui.play();
		
		fakeGui.undo();
		fakeGui.play();
		
		fakeGui.redo();
		fakeGui.play();
		
		fakeGui.redo();
		fakeGui.play();
		
		fakeGui.setBypass(0, 0, true);
		fakeGui.play();
		Stack<Float> floatParameters=new Stack<Float>();
		floatParameters.push(0.6f);
		floatParameters.push(2f);
		floatParameters.push(0.9f);
    		
		fakeGui.setParameters(0, 0, floatParameters);	
		fakeGui.setBypass(0, 0, false);
		fakeGui.store();	
		fakeGui.play();
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