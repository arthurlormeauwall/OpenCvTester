package main;

import java.io.IOException;

import guiController.App;
import userFilters.BlueGreenRedMultiplierFilter;
import userFilters.GrayScaleFilter;

public class Main 
{
    public static void main(String[] args) throws IOException{	
		App app= new App("assets/test.jpg"); 
		app.addFilterInDataBase("BGR Multiplier", new BlueGreenRedMultiplierFilter());
		app.addFilterInDataBase("GrayScale", new GrayScaleFilter());
    }
}