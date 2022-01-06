package com.opencvtester.guiManager;

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

	/*
	 * FEATURES
	 */
	public void refresh(BufferedImage bufImage) {
		frameWindow.getContentPane().removeAll();
		frameWindow.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
		frameWindow.pack();
		frameWindow.setVisible(true);
	}
}
