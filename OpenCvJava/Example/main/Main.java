package main;

import java.io.IOException;

import baseClasses.frame.LibraryOption;
import guiManager.App;
import userFilters.BlueGreenRedMultiplierFilter;
import userFilters.GrayScaleFilter;

public class Main 
{
    public static void main(String[] args) throws IOException{	
		App app= new App("assets/test.jpg", LibraryOption.OPENCV); 
		app.addFilterInDataBase("BGR Multiplier", new BlueGreenRedMultiplierFilter());
		app.addFilterInDataBase("GrayScale", new GrayScaleFilter());
    }
}