package main;

import org.opencv.core.Core;


public class Main {
    public static void main(String[] args){
    
    		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    		
    		/*
            Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
            System.out.println("mat = " + mat.dump());    
           
            Frame frame = new Frame();
            frame.readFromFile("assets/20210717_203824.jpg");
            
            MainWin win = new MainWin();
            win.setVisible(true);
            
            frame.play(); */
    		
    		App myApp = new App();
    		myApp.init();
           
    }
}