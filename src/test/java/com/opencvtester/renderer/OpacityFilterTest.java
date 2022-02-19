package com.opencvtester.renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opencvtester.data.interfaces.FilterDataInterface;
import com.opencvtester.renderer.Frame;
import com.opencvtester.renderer.OpacityFilter;


class OpacityFilterTest {
	private Frame background;
	private OpacityFilter filter;
	
	@BeforeEach
	public void setup() {
		background = new Frame();
		background.createPlainGrayFrame(100, 100, 0);
		filter = new OpacityFilter ("Opacity");
		filter.init(background);
	}
	
	@Test
	public void testSetParameter() {
		LinkedHashMap<String,Float> p1 = new LinkedHashMap<String, Float>();
		p1.put("Opacity", 0.5f);
		
		filter.setAllParameters(p1);
		
		assertEquals(0.5f, filter.getParameter("Opacity"));
		
		filter.setOpacity(0.6f);
		
		assertEquals(0.6f, filter.getParameter("Opacity"));
		
		filter.setOpacity(1f);
		
		assertTrue(((FilterDataInterface)filter.getFilterData()).isBypass());	
	}
	
	@Test
	public void executeTest() {
		Frame frameIn = new Frame();
		Frame frameOut = new Frame();
		frameIn.createPlainGrayFrame(100, 100, 255);
		frameOut.createPlainGrayFrame(100, 100, 255);
		
		filter.setFrameIn(frameIn);
		filter.setFrameOut(frameOut);
		
		for (Float opacity=0f;opacity<1; opacity+=0.05f) {
			filter.setOpacity(opacity);
			filter.getFilterData().setBypass(false);
			filter.render();
			
			Frame result=new Frame();
			result.createPlainGrayFrame(100, 100, Math.round(255*opacity));
			assertTrue(result.compareTo(filter.getFrameOut())==0);
		}
	}
}
