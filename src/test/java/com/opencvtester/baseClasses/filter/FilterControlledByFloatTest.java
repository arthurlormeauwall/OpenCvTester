package com.opencvtester.baseClasses.filter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opencvtester.baseClasses.frame.Frame;

class FilterControlledByFloatTest {
	FilterControlledByFloat filter;
	private Float a=1.2f, b=1.6f, c=2.6f;
	
	@BeforeEach
	public void setup() {
		filter= new DummyFilter("test");
	}
	
	@Test
	void testAddParameterFlag() {
		LinkedHashMap<String, Float> parameters= filter.getParameters();
		assertEquals(1f, parameters.get("BlueMult"));
		assertEquals(1f, parameters.get("GreenMult"));
		assertEquals(1f, parameters.get("RedMult"));
	}

	@Test
	void testReset() {
		testSetParameter();
		filter.reset();
		LinkedHashMap<String, Float> parameters= filter.getParameters();
		assertEquals(1f, parameters.get("BlueMult"));
		assertEquals(1f, parameters.get("GreenMult"));
		assertEquals(1f, parameters.get("RedMult"));
	}

	@Test
	void testSetParameter() {
		filter.setParameter("BlueMult", a);
		filter.setParameter("GreenMult", b);
		filter.setParameter("RedMult", c);
		LinkedHashMap<String, Float> parameters= filter.getParameters();
		assertEquals(a, parameters.get("BlueMult"));
		assertEquals(b, parameters.get("GreenMult"));
		assertEquals(c, parameters.get("RedMult"));	
	}

	@Test
	void testSetAllParameters() {
		LinkedHashMap<String, Float> parameters= new LinkedHashMap<String, Float>();
		parameters.put("BlueMult", a);
		parameters.put("GreenMult", b);
		parameters.put("RedMult", c);
		
		filter.setAllParameters(parameters);
		assertEquals(a, parameters.get("BlueMult"));
		assertEquals(b, parameters.get("GreenMult"));
		assertEquals(c, parameters.get("RedMult"));		
	}

	@Test
	void testSetFrameIn() {
		Frame frameIn= new Frame(10,10,127);
		
		filter.setFrameIn(frameIn);
		
		assertTrue(frameIn.compareTo(filter.getFrameIn())==0);
	}

	@Test
	void testSetFrameOut() {
		Frame frameOut= new Frame(10,10,127);
		
		filter.setFrameOut(frameOut);
		
		assertTrue(frameOut.compareTo(filter.getFrameOut())==0);
	}

}
