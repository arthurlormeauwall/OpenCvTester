package com.opencvtester.baseClasses;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdTest {

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
	
	@Test
	public void constructorTest() {
		
		// default constructor 
		Id idA=new Id();
		
		assertEquals(-1, idA.layerIndex());
		assertEquals(-1, idA.filterIndex());
		
		// construct with hashmap
		HashMap<String, Integer> hashmap= new HashMap<String, Integer>();
		hashmap.put("layer", layerIndex);
		hashmap.put("filter", filterIndex);	
		
		Id idB= new Id(hashmap);
		
		assertEquals(layerIndex, idB.layerIndex());
		assertEquals(filterIndex, idB.filterIndex());	
		
		// construct with layer and filter values
		Id idC= new Id(layerIndex, filterIndex);
		assertEquals(layerIndex, idC.layerIndex());
		assertEquals(filterIndex, idC.filterIndex());	
	}
	
	@Test
	public void setLayerOrIndexTest() {
		// test setFilterOrLayer method
		Id id = new Id();
		id.setFilterOrLayer("layer", layerIndex);
		id.setFilterOrLayer("filter", filterIndex);
		
		assertEquals(layerIndex, id.layerIndex());
		assertEquals(filterIndex, id.filterIndex());
		
		// test setters
		id.setLayerIndex(-1);
		id.setFilterIndex(-1);
		
		assertEquals(-1, id.layerIndex());
		assertEquals(-1, id.filterIndex());
	}
}
