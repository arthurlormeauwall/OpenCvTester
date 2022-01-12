package com.opencvtester.guiManager;

import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FrameWindowManager {

	protected JFrame frameWindow;
	protected BufferedImage bufImage;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	FrameWindowManager(){
		frameWindow= new JFrame("Image");
		frameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
	   
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
	public void refresh(BufferedImage bufImage) {
		frameWindow.getContentPane().removeAll();
		frameWindow.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
		frameWindow.pack();
		setInMiddleOfScreen();
		frameWindow.setVisible(true);
	}
}
