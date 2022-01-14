package com.opencvtester.baseClasses;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.Stack;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChainOfCommandsTest {

	private int NUMBER_OF_TEST;
	
	private int layerIndexMaxValue;
	private int filterIndexMaxValue;
	private int layerIndexMinValue;
	private int filterIndexMinValue;
	
	private Stack<Command> layers, filters;
	private ChainOfCommands chainOfLayer ;
	private ChainOfCommands chainOfFilter;
	
	@BeforeEach // generate stack of command object with random Id 
	public void setup() {
		NUMBER_OF_TEST=100;
		
		layerIndexMaxValue= NUMBER_OF_TEST*10;
		filterIndexMaxValue= NUMBER_OF_TEST*10;
		layerIndexMinValue= -NUMBER_OF_TEST*10;
		filterIndexMinValue= -NUMBER_OF_TEST*10;
		
		layers=new Stack<Command>();
		filters=new Stack<Command>();

		chainOfLayer = new ChainOfCommands("layer");
		chainOfFilter = new ChainOfCommands("filter");
		
		for(int i=0;i< NUMBER_OF_TEST;i++){
			Random random = new Random();
			
			//generate random layer and filter value between max and min value 
			int randomLayerIndex= random.nextInt(layerIndexMaxValue-layerIndexMinValue + 1)+layerIndexMinValue;
			int randomFilterIndex= random.nextInt(filterIndexMaxValue -layerIndexMinValue + 1)+filterIndexMinValue;
		
			Id id = new Id(randomLayerIndex,randomFilterIndex);
	
			layers.push(new Command(id));
			filters.push(new Command(id));
		}
	}
	
	@Test
	void testAddCommand() {
		
		for(int i=0;i< NUMBER_OF_TEST;i++){	        
			chainOfLayer.addCommand(layers.get(i));
			chainOfFilter.addCommand(filters.get(i));
		}
		
		Stack<Command> resultChainOfLayer= chainOfLayer.getChain();
		Stack<Command> resultChainOfFilter= chainOfFilter.getChain();
		
		for(int i=0;i< NUMBER_OF_TEST;i++){
			assertEquals(i, resultChainOfLayer.get(i).layerIndex());
			assertEquals(i, resultChainOfFilter.get(i).filterIndex());
			
			assertTrue(resultChainOfLayer.contains(layers.get(i)));
			assertTrue(resultChainOfFilter.contains(filters.get(i)));
		}
	}

	@Test
	void testDelCommand() {
		for(int i=0;i< NUMBER_OF_TEST;i++){	        
			chainOfLayer.addCommand(layers.get(i));
			chainOfFilter.addCommand(filters.get(i));
		}
		
		Stack<Command> stackOfLayer = new Stack<Command>();
		Stack<Command> stackOfFilter = new Stack<Command>();
		
		for(int i=0; i<NUMBER_OF_TEST; i++) {
			stackOfLayer.push(chainOfLayer.delCommand(i));
			stackOfFilter.push(chainOfFilter.delCommand(i));
		}
		
		for(int i=0;i< NUMBER_OF_TEST;i++){
			assertTrue(stackOfLayer.contains(layers.get(i)));
			assertTrue(stackOfFilter.contains(filters.get(i)));
		}
		assertTrue(chainOfLayer.getSize()==0);
		assertTrue(chainOfFilter.getSize()==0);		
	}
}
