package com.opencvtester.baseClasses;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandTest {

	private int layerIndex;
	private int filterIndex;
	private static int layerIndexMaxValue= 100;
	private static int filterIndexMaxValue=100;
	
	@BeforeEach
	public void generateRandomIndex() {
		Random random= new Random();
		this.layerIndex= random.nextInt(layerIndexMaxValue + 1);
		this.filterIndex= random.nextInt(filterIndexMaxValue + 1);
	}
	
//	@Test
//	void testCommand() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testCommandId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetLayerIndex() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetFilterIndex() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetLayerIndex() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetFilterIndex() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testIsbypass() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetBypass() {
//		fail("Not yet implemented");
//	}

}
