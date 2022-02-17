package com.opencvtester.renderingEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;

import org.junit.jupiter.api.Test;

import com.opencvtester.baseClasses.filter.DummyFilter;
import com.opencvtester.data.LayerData;
import com.opencvtester.data.LayerFactory;
import com.opencvtester.renderer.FiltersDataBase;

class LayerFactoryTest {

	@Test
	void testCreateLayer() {
		DummyFilter filter= new DummyFilter("test");
		FiltersDataBase filterDb =new FiltersDataBase();
		filterDb.addFilter("test", filter);
		
		Stack<String> tempNames = new Stack<String>();
		tempNames.push("test");
		tempNames.push("test");
		
		LayerData layer = LayerFactory.createLayer(new LayerData(1,1f, tempNames), filterDb);
		
		assertEquals(1, layer.layerIndex());
		assertEquals("test", layer.getFilter(0).getFilterName());
		assertEquals(0, layer.getFilter(0).filterIndex());
		assertEquals(1, layer.getFilter(0).layerIndex());
		
		assertEquals("test", layer.getFilter(1).getFilterName());
		assertEquals(1, layer.getFilter(1).filterIndex());
		assertEquals(1, layer.getFilter(1).layerIndex());
	}

}
