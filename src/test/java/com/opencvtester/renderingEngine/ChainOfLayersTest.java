package com.opencvtester.renderingEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opencvtester.baseClasses.filter.DummyFilter;
import com.opencvtester.data.Command;
import com.opencvtester.data.FilterData;
import com.opencvtester.data.Layer;
import com.opencvtester.data.LayerData;
import com.opencvtester.data.interfacesImp.DataCtrlImp;
import com.opencvtester.renderer.IOFrame;
import com.opencvtester.renderer.FilterFactory;
import com.opencvtester.renderer.FiltersDataBase;
import com.opencvtester.renderer.Frame;
import com.opencvtester.renderer.LayerFactory;

class ChainOfLayersTest {

	private DataCtrlImp chainOfLayers;
	private DummyFilter filter;
	private FiltersDataBase filterDb;
	private Stack<Layer> layers;
	private Stack<IOFrame> filters;
	
	public ChainOfLayersTest() {
		
		filter= new DummyFilter("test");
		filterDb =new FiltersDataBase();
		
		
		filterDb.addFilter("test", filter);
		
		layers= new Stack<Layer>();
		filters= new Stack<IOFrame>();
		
		filters.push(FilterFactory.createFilter(new FilterData(1,0,"test",null), filterDb));
		filters.push(FilterFactory.createFilter(new FilterData(1,1,"test",null), filterDb));
		filters.push(FilterFactory.createFilter(new FilterData(1,2,"test",null), filterDb));
		
		layers.push(LayerFactory.createEmptyLayer(new LayerData(0, 1f), filterDb));
		layers.push(LayerFactory.createEmptyLayer(new LayerData(1, 1f), filterDb));
		
		chainOfLayers = new DataCtrlImp(new Frame(10,10,127));

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
		IOFrame newFilter =FilterFactory.createFilter(new FilterData(1,3,"test",null),filterDb);
		
		chainOfLayers.addFilter(newFilter);
		
		assertEquals(newFilter, chainOfLayers.getLastLayer().getLastFilter());	
		
		IOFrame newFilter2 =FilterFactory.createFilter(new FilterData(0,2,"test",null),filterDb);
		
		chainOfLayers.addFilter(newFilter2);
		
		assertEquals(0, chainOfLayers.getLayer(0).getNumberOfFilters());	
		
		IOFrame newFilter3 =FilterFactory.createFilter(new FilterData(0,0,"test",null),filterDb);
		
		chainOfLayers.addFilter(newFilter3);
		
		assertEquals(1, chainOfLayers.getLayer(0).getNumberOfFilters());
		assertEquals(newFilter3, chainOfLayers.getLayer(0).getFilter(0));
		assertEquals(newFilter3, chainOfLayers.getLayer(0).getLastFilter());
	}
	
	@Test
	void testUpdateId() {	
		IOFrame newFilter =FilterFactory.createFilter(new FilterData(1,1,"test",null),filterDb);
		
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
		filters.get(0).setId(0,0);
		chainOfLayers.getLayer(0).addFilter(filters.get(0));
		
		chainOfLayers.getLastLayer().deleteAllFilters();
		filters.get(1).setId(0,1);
		filters.get(2).setId(0,2);
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
		filters.get(0).setId(0,0);
		chainOfLayers.getLayer(0).addFilter(filters.get(0));
		
		filters.get(1).setId(1,1);
		filters.get(2).setId(1,2);
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

		filters.get(0).setId(0,0);
		chainOfLayers.getLayer(0).addFilter(filters.get(0));
		
		filters.get(1).setId(1,1);
		filters.get(2).setId(1,2);
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
