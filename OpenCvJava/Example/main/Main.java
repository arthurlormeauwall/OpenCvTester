package main;

import java.io.IOException;

import guiManager.App;
import openCvAdapter.CvFrame;
import userFilters.BlueGreenRedMultiplierFilter;
import userFilters.GrayScaleFilter;

public class Main 
{
    public static void main(String[] args) throws IOException{	
		App app= new App(); 
		app.addFrameType("OpenCv", new CvFrame());
		app.setFrameType("OpenCv");
		app.initialize("assets/test.jpg");
		
		app.addFilterInDataBase("BGR Multiplier", new BlueGreenRedMultiplierFilter());
		app.addFilterInDataBase("GrayScale", new GrayScaleFilter());	
    }
}