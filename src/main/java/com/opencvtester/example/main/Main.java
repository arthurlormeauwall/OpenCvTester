package com.opencvtester.example.main;

import java.io.IOException;

import com.opencvtester.example.userFilters.BlueGreenRedMultiplierFilter;
import com.opencvtester.example.userFilters.GrayScaleFilter;
import com.opencvtester.guiManager.App;
import com.opencvtester.guiManager.Library;

public class Main 
{
    public static void main(String[] args) throws IOException{	
		App app= new App(Library.OPENCV); 
		app.initialize("assets/test.jpg");
		
		app.addFilterInDataBase("BGR Multiplier", new BlueGreenRedMultiplierFilter());
		app.addFilterInDataBase("GrayScale", new GrayScaleFilter());	
    }
}