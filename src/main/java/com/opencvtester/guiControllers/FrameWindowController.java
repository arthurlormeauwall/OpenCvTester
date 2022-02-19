package com.opencvtester.guiControllers;

import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.opencvtester.renderer.FrameInterface;

public class FrameWindowController {

	protected JFrame frameWindow;
	protected BufferedImage bufImage;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public FrameWindowController(){
		frameWindow= new JFrame("Image");
		frameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	   
	}
	
	public FrameWindowController(FrameInterface frameOut){
		frameWindow= new JFrame("Image");
		frameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		refresh(frameOut);
	}
	
	public void setInMiddleOfScreen() {
		    GraphicsConfiguration config = frameWindow.getGraphicsConfiguration();
		    Rectangle bounds = config.getBounds();
		    
		    int x = bounds.width/2-frameWindow.getWidth()/2;
		    int y = bounds.height/2 -frameWindow.getHeight()/2;
		    frameWindow.setLocation(x, y);

	}
	/*
	 * FEATURES
	 */
	public void refresh(FrameInterface frameOut) {
		frameWindow.getContentPane().removeAll();
		frameWindow.getContentPane().add(new JLabel(new ImageIcon(frameOut.getBufferedImage())));
		frameWindow.pack();
		
		frameWindow.setVisible(true);
	}
}
