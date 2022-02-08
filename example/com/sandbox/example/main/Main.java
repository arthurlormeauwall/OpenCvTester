package com.sandbox.example.main;

import java.io.IOException;

import com.opencvtester.app.App;
import com.sandbox.example.userFilters.BlueGreenRedMultiplierFilter;
import com.sandbox.example.userFilters.GrayScaleFilter;

public class Main 
{
    public static void main(String[] args) throws IOException{	
		App app= new App("assets/test2.jpg"); 
		
		app.addFilterInDataBase("BGR Multiplier", new BlueGreenRedMultiplierFilter());
		app.addFilterInDataBase("GrayScale", new GrayScaleFilter());	
		
		app.launch();
    }
}