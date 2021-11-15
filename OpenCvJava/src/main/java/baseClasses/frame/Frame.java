package baseClasses.frame;

import java.awt.image.BufferedImage;

public interface Frame {
	
	public void readFromFile(final String fileName) ;

	public void copyTo(Frame newFrame) ;

	public void createPlainGrayFrame(int rows, int cols, int data);
	
	public Boolean compareTo(Frame p) ;

	public FrameSpecs getSpecs();
	
	public void setSpecs();
	
	public double[] getPixelAt(int row, int col);
	
	public void setPixelAt(int row, int col, double[] data);
	
	public BufferedImage toBufferedImage();
	
	public Frame create();
}
