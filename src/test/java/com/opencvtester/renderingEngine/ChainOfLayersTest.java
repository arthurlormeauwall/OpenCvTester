package com.opencvtester.renderingEngine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opencvtester.baseClasses.Command;
import com.opencvtester.baseClasses.Id;
import com.opencvtester.baseClasses.filter.DummyFilter;
import com.opencvtester.baseClasses.filter.Filter;
import com.opencvtester.baseClasses.frame.Frame;
import com.opencvtester.filtersDataBase.FiltersDataBase;

class ChainOfLayersTest {

	private ChainOfLayers chainOfLayers;
	private DummyFilter filter;
	private FiltersDataBase filterDb;
	private Stack<Layer> layers;
	private Stack<Filter> filters;
	
	public ChainOfLayersTest() {
		
		filter= new DummyFilter();
		filterDb =new FiltersDataBase();
		
		filterDb.addFilter("test", filter);
		
		layers= new Stack<Layer>();
		filters= new Stack<Filter>();
		
		filters.push(FilterFactory.createFilter(new Id(1,0), "test", filterDb));
		filters.push(FilterFactory.createFilter(new Id(1,1), "test",filterDb));
		filters.push(FilterFactory.createFilter(new Id(1,2), "test",filterDb));
		
		layers.push(LayerFactory.createEmptyLayer(0, filterDb));
		layers.push(LayerFactory.createEmptyLayer(1,filterDb));
		
		chainOfLayers = new ChainOfLayers(filterDb, new Frame(10,10,127), new Id(0,0));

	}
	
	@BeforeEach
	public void setup() {
		chainOfLayers.addLayer(layers.get(0));
		chainOfLayers.addLayer(layers.get(1));
		
		chainOfLayers.getLastLayer().addFilter(filters.get(0));
		chainOfLayers.getLastLayer().addFilter(filters.get(1));
		chainOfLayers.getLastLayer().addFilter(filters.get(2));
	}
	
	@Test
	void testAddLayer() {

		Stack<Command> result= chainOfLayers.getChain().getChain();
		Layer layer1= (Layer)result.get(0);
		Layer layer2= (Layer)result.get(1);
		
		assertEquals(layer1, layers.get(0));
		assertEquals(layer2, layers.get(1));
	}

	@Test
	void testDeleteLayer() {
		chainOfLayers.deleteLayer(layers.get(1));
		assertEquals (1, chainOfLayers.getNumberOfLayers());
		assertEquals(layers.get(0), chainOfLayers.getLastLayer());
		
		chainOfLayers.deleteLayer(layers.get(0));	
		assertEquals (0, chainOfLayers.getNumberOfLayers());
	}
	
	@Test
	void testGetLastLayer() {
		assertEquals(layers.get(1), chainOfLayers.getLastLayer());
	}
	
	@Test
	void testAddFilter() {
		Filter newFilter =FilterFactory.createFilter(new Id(1,3), "test",filterDb);
		
		chainOfLayers.addFilter(newFilter);
		
		assertEquals(newFilter, chainOfLayers.getLastLayer().getLastFilter());	
		
		Filter newFilter2 =FilterFactory.createFilter(new Id(0,2), "test",filterDb);
		
		chainOfLayers.addFilter(newFilter2);
		
		assertEquals(0, chainOfLayers.getLayer(0).getNumberOfFilters());	
		
		Filter newFilter3 =FilterFactory.createFilter(new Id(0,0), "test",filterDb);
		
		chainOfLayers.addFilter(newFilter3);
		
		assertEquals(1, chainOfLayers.getLayer(0).getNumberOfFilters());
		assertEquals(newFilter3, chainOfLayers.getLayer(0).getFilter(0));
		assertEquals(newFilter3, chainOfLayers.getLayer(0).getLastFilter());
	}
	
	@Test
	void testUpdateId() {	
		Filter newFilter =FilterFactory.createFilter(new Id(1,1), "test",filterDb);
		
		chainOfLayers.addFilter(newFilter);
		
		assertEquals(0, filters.get(0).filterIndex());
		
		assertEquals(newFilter, chainOfLayers.getLayer(1).getFilter(1));
		assertEquals(1, newFilter.filterIndex());
		assertEquals(2, filters.get(1).filterIndex());
		assertEquals(3, filters.get(2).filterIndex());
	}

	@Test
	void testDeleteFilter() {
		chainOfLayers.deleteFilter(filters.get(1));		
		assertEquals(2, chainOfLayers.getLastLayer().getNumberOfFilters());
		assertEquals(0, filters.get(0).filterIndex());
		assertEquals(1, filters.get(2).filterIndex());
	}

	@Test
	void testCheckAndActivateLayer() {
		chainOfLayers.getLayer(0).deleteAllFilters();
		filters.get(0).setId(new Id(0,0));
		chainOfLayers.getLayer(0).addFilter(filters.get(0));
		
		chainOfLayers.getLastLayer().deleteAllFilters();
		filters.get(1).setId(new Id(0,1));
		filters.get(2).setId(new Id(0,2));
		chainOfLayers.getLastLayer().addFilter(filters.get(1));
		chainOfLayers.getLastLayer().addFilter(filters.get(2));
		
		chainOfLayers.checkAndActivateLayer(chainOfLayers.getLayer(0));
		
		assertTrue(chainOfLayers.getLayer(0).isActivate());
		assertTrue(chainOfLayers.getLayer(0).getFilter(0).isActivate());
		assertTrue(chainOfLayers.getLayer(1).isActivate());
		assertTrue(chainOfLayers.getLayer(1).getFilter(0).isActivate());		
	}

	@Test
	void testCheckAndActivateFilter() {
		chainOfLayers.getLayer(0).deleteAllFilters();
		chainOfLayers.getLastLayer().deleteAllFilters();
		
		///////////////// 
		filters.get(0).setId(new Id(0,0));
		chainOfLayers.getLayer(0).addFilter(filters.get(0));
		
		filters.get(1).setId(new Id(1,1));
		filters.get(2).setId(new Id(1,2));
		chainOfLayers.getLastLayer().addFilter(filters.get(1));
		chainOfLayers.getLastLayer().addFilter(filters.get(2));
		
		chainOfLayers.checkAndActivateFilter(chainOfLayers.getLayer(0).getFirstFilter());
		
		assertTrue(chainOfLayers.getLayer(0).isActivate());
		assertTrue(chainOfLayers.getLayer(0).getFilter(0).isActivate());
		assertTrue(chainOfLayers.getLayer(1).isActivate());
		assertTrue(chainOfLayers.getLayer(1).getFilter(0).isActivate());
		
		///////////////// 
		chainOfLayers.getLayer(0).desactivate();
		chainOfLayers.getLastLayer().desactivate();
		filters.get(0).desactivate();
		filters.get(1).desactivate();
		filters.get(2).desactivate();
		chainOfLayers.getLayer(0).deleteAllFilters();
		chainOfLayers.getLastLayer().deleteAllFilters();
		///////////////// 

		filters.get(0).setId(new Id(0,0));
		chainOfLayers.getLayer(0).addFilter(filters.get(0));
		
		filters.get(1).setId(new Id(1,1));
		filters.get(2).setId(new Id(1,2));
		chainOfLayers.getLastLayer().addFilter(filters.get(1));
		chainOfLayers.getLastLayer().addFilter(filters.get(2));
		
		chainOfLayers.checkAndActivateFilter(chainOfLayers.getLayer(1).getFilter(1));
		
		assertFalse(chainOfLayers.getLayer(0).isActivate());
		assertFalse(chainOfLayers.getLayer(0).getFilter(0).isActivate());
		assertTrue(chainOfLayers.getLayer(1).isActivate());
		assertFalse(chainOfLayers.getLayer(1).getFilter(0).isActivate());
		assertTrue(chainOfLayers.getLayer(1).getFilter(1).isActivate());	
	}
}
