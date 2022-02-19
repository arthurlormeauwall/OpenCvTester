package com.opencvtester.renderer;

import java.awt.image.BufferedImage;

public interface FrameInterface {

	public void readFromFile(final String fileName) ;

	public void copyTo(FrameInterface newFrame) ;

	public void createPlainGrayFrame(int rows, int cols, int data);
	
	public int compareTo(FrameInterface p) ;

	public FrameSpecs getSpecs();
	
	public void setSpecs();
	
	public int[] getPixelAt(int row, int col);
	
	public void setPixelAt(int row, int col, int[] data);
	
	public BufferedImage getBufferedImage();
	
	public FrameInterface create();

	public void setBufferedImage(BufferedImage bufferedImage);
}
