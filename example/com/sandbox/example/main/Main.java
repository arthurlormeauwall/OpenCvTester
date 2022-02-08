package com.sandbox.example.main;

import java.io.IOException;

import com.opencvtester.app.App;
import com.sandbox.example.userFilters.BlueGreenRedMultiplierFilter;
import com.sandbox.example.userFilters.GrayScaleFilter;

public class Main 
{
    public static void main(String[] args) throws IOException{	
		App app= new App("assets/test2.jpg"); 
		
		app.addFilterInDataBase(new BlueGreenRedMultiplierFilter("BGR Multiplier Filter"));
		app.addFilterInDataBase(new GrayScaleFilter("GrayScale Filter"));	
		
		app.launch();
    }
}