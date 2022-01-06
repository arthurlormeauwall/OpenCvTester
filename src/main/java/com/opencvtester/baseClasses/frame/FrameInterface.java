package com.opencvtester.baseClasses.frame;

import java.awt.image.BufferedImage;

public interface FrameInterface {
	
	public void readFromFile(final String fileName) ;

	public void copyTo(FrameInterface newFrame) ;

	public void createPlainGrayFrame(int rows, int cols, int data);
	
	public Boolean compareTo(FrameInterface p) ;

	public FrameSpecs getSpecs();
	
	public void setSpecs();
	
	public double[] getPixelAt(int row, int col);
	
	public void setPixelAt(int row, int col, double[] data);
	
	public BufferedImage getBufferedImage();
	
	public FrameInterface create();
}