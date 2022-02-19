package com.opencvtester.renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;

import org.junit.jupiter.api.Test;

class FiltersDataBaseTest {

	@Test
	void testGetFiltersName() {
		FiltersDataBase fdb= new FiltersDataBase();
		fdb.addFilter("name1", null);
		fdb.addFilter("name2", null);
		fdb.addFilter("name3", null);
		
		Stack<String> result= new Stack<String>();
		result.push("name1");
		result.push("name2");
		result.push("name3");
		
		assertEquals(result, fdb.getFiltersName());
	}

}
