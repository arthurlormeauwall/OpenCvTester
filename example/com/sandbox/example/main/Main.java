package com.sandbox.example.main;

import java.io.IOException;

import com.opencvtester.app.App;
import com.sandbox.example.userFilters.RGBMultiplierFilter;
import com.sandbox.example.userFilters.BlackAndWhiteFilter;

public class Main 
{
    public static void main(String[] args) throws IOException{	
		App app= new App("assets/test2.jpg"); 
		
		app.addFilterInDataBase(new RGBMultiplierFilter("RGB Multiplier Filter"));
		app.addFilterInDataBase(new BlackAndWhiteFilter("B/W Filter"));	
		
		app.launch();
    }
}