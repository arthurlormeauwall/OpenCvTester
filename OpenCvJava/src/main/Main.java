package main;

import org.opencv.core.Core;

import application.App;
import test.Test;


public class Main {
    public static void main(String[] args){
    
    		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
    		App myApp = new App();
    		Test myTest= new Test(myApp);      
    }
}