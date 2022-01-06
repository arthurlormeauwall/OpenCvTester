package com.opencvtester.baseClasses.frame;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class Frame implements FrameInterface {
	
	final int NumberOfColorLayer = 3; 
	BufferedImage bufferedImage;
	FrameSpecs frameSpecs;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public Frame() {
		bufferedImage= new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		frameSpecs= new FrameSpecs();
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public FrameSpecs getSpecs() {
		return frameSpecs;
	}

	public void setSpecs() {
		frameSpecs.bitMax=255; //TODO change and read actual maxbitValue of image	
		frameSpecs.cols=bufferedImage.getWidth();
		frameSpecs.rows=bufferedImage.getHeight();
	}
	
	/*
	 * FEATURES
	 */
	public void readFromFile(String fileName) {
		try
        {
          bufferedImage = ImageIO.read(new File(fileName));
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
          }
		setSpecs();   
	}

	public void copyTo(FrameInterface newFrame) {
		((Frame)newFrame).setBufferedImage(deepCopy(bufferedImage));
		newFrame.setSpecs();
	}

	public void setBufferedImage (BufferedImage bufferedImage){
		this.bufferedImage = bufferedImage; 
		setSpecs();
	}
	
	public BufferedImage deepCopy(BufferedImage bufferedImage) {
		 ColorModel cm = bufferedImage.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bufferedImage.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public void createPlainGrayFrame(int rows, int cols, int data) {
		bufferedImage= new BufferedImage(rows, cols, BufferedImage.TYPE_INT_RGB);
		for (int i=0 ; i < rows ;i++) {  	
			for (int j=0 ; j < cols ; j++){ 	
				bufferedImage.setRGB(i, j, data);
			}
		}
		setSpecs();
	}

	public Boolean compareTo(FrameInterface frame) {
			int rowsP= frame.getSpecs().rows;
			int colsP=frame.getSpecs().cols;
			
			int rows =getSpecs().rows;
			int cols= getSpecs().cols;
			
			if (rowsP == rows && colsP == cols){
				
				for (int i=0; i<rows; i++) {
					for (int j=0; j<cols;j ++) {
						if (frame.getPixelAt(i,j) != getPixelAt(i,j)) {
							return false;
						}
					}
				}
				return true ; 
			}
			return false;
		}

	public double[] getPixelAt(int row, int col) {
		double [] pixelValue= new double[3];
		
        Color color = new Color(bufferedImage.getRGB(row, col), true);
        pixelValue[0]=(double)color.getBlue();
        pixelValue[1]=(double)color.getGreen();
        pixelValue[2]=(double)color.getRed();
		
		return pixelValue;		   
	}

	public void setPixelAt(int row, int col, double[] data) {
		Color color = new Color ((int)data[2],(int)data[1],(int)data[0]);	
		bufferedImage.setRGB(row, col, color.getRGB());
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	
	public Mat getMat(){ 
		  Mat  mat = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
		    byte[] data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
		    mat.put(0, 0, data);
		    return  mat; 
	}

	public void set(Mat frame){
		 //Encoding the image
		  MatOfByte matOfByte = new MatOfByte();
		  Imgcodecs.imencode(".jpg", frame, matOfByte);
		  //Storing the encoded Mat in a byte array
		  byte[] byteArray = matOfByte.toArray();
		  InputStream in = new ByteArrayInputStream(byteArray);
		  try {
			  bufferedImage = ImageIO.read(in);
		  } catch (IOException e) {
			  e.printStackTrace();
		  } 	  
	}

	public FrameInterface create() {
		return new Frame();
	}
}
