package com.opencvtester.renderingEngine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.DummyFilter;
import com.opencvtester.baseClasses.frame.Frame;
import com.opencvtester.filtersDataBase.FiltersDataBase;

class ChainOfLayersTest {

	private ChainOfLayers chainOfLayers;
	private DummyFilter filter;
	private FiltersDataBase filterDb;
	private Stack<Layer> layers;
	
	@BeforeAll
	public void generalSetup() {
		filter= new DummyFilter();
		filterDb =new FiltersDataBase();
		
		filterDb.addFilter("test", filter);
		
		LayersFactory layersfactory=new LayersFactory(filterDb);
		
		

		layers.push(layersfactory.createEmptyLayer(new Id(0,0)));
		
		Stack<Id> tempIdStack= new Stack<Id>();
		tempIdStack.push(new Id(1,0));
		tempIdStack.push(new Id(1,0));
		tempIdStack.push(new Id(1,1));
		Stack<String> tempNames = new Stack<String>();
		tempNames.push("test");
		tempNames.push("test");
		layers.push(layersfactory.createLayer(tempIdStack, tempNames));

		chainOfLayers = new ChainOfLayers(filterDb, new Frame(10,10,127), new Id(0,0));
	}
	
	@Test
	void testGetLastLayer() {
		
	}

	@Test
	void testGetLayer() {
		
	}

	@Test
	void testGetNumberOfLayers() {
		
	}

	@Test
	void testAddLayer() {
		
	}

	@Test
	void testDelLayer() {
		
	}

	@Test
	void testAddFilter() {
		
	}

	@Test
	void testDelFilter() {
		
	}

	@Test
	void testCheckAndActivateLayer() {
	
	}

	@Test
	void testCheckAndActivateFilter() {
		
	}

	@Test
	void testIsIndexOutOfRange() {
	
	}

}
