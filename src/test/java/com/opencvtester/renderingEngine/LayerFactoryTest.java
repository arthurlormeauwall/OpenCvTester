package com.opencvtester.renderingEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;

import org.junit.jupiter.api.Test;

import com.opencvtester.baseClasses.filter.DummyFilter;
import com.opencvtester.dataAccess.LayerFactory;
import com.opencvtester.filtersDataBase.FiltersDataBase;

class LayerFactoryTest {

	@Test
	void testCreateLayer() {
		DummyFilter filter= new DummyFilter();
		FiltersDataBase filterDb =new FiltersDataBase();
		filterDb.addFilter("test", filter);
		
		Stack<String> tempNames = new Stack<String>();
		tempNames.push("test");
		tempNames.push("test");
		
		Layer layer = LayerFactory.createLayer(1, tempNames, filterDb);
		
		assertEquals(1, layer.layerIndex());
		assertEquals("test", layer.getFilter(0).getFilterName());
		assertEquals(0, layer.getFilter(0).filterIndex());
		assertEquals(1, layer.getFilter(0).layerIndex());
		
		assertEquals("test", layer.getFilter(1).getFilterName());
		assertEquals(1, layer.getFilter(1).filterIndex());
		assertEquals(1, layer.getFilter(1).layerIndex());
	}

}
