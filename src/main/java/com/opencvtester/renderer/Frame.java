package com.opencvtester.renderer;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class Frame implements FrameInterface, Comparable<FrameInterface>  {
	
	final int NumberOfColorLayer = 3; 
	BufferedImage bufferedImage;
	FrameSpecs frameSpecs;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public Frame() {
		bufferedImage= new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		frameSpecs= new FrameSpecs();
		setSpecs();
	}
	
	public Frame(int row, int col, int data) {
		bufferedImage= new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		frameSpecs= new FrameSpecs();
		createPlainGrayFrame(row, col, data);
	}
	
	public Frame(BufferedImage bufferedImage) {
		this.bufferedImage= bufferedImage;
		frameSpecs= new FrameSpecs();
		setSpecs();
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
	
	public static BufferedImage deepCopy(BufferedImage bufferedImage) {
		 ColorModel cm = bufferedImage.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bufferedImage.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public void createPlainGrayFrame(int rows, int cols, int data) {
		data= Math.max(0, Math.min(255, data));
		int pixel = (data<<16) | (data<<8) | data;
		bufferedImage= new BufferedImage(cols, rows, BufferedImage.TYPE_INT_ARGB);
		for (int i=0 ; i < rows ;i++) {  	
			for (int j=0 ; j < cols ; j++){ 	
				bufferedImage.setRGB(j, i, pixel);
			}
		}
		
		setSpecs();
	}

	public int compareTo(FrameInterface frame) {
			int rowsP= frame.getSpecs().rows;
			int colsP=frame.getSpecs().cols;
			
			int rows =getSpecs().rows;
			int cols= getSpecs().cols;
			
			if (rowsP == rows && colsP == cols){
				
				for (int i=0; i<rows; i++) {
					for (int j=0; j<cols;j ++) {
						int[] framePixel=frame.getPixelAt(i, j);
						int[] thisPixel=getPixelAt(i,j);
						
						if (!Arrays.equals(framePixel, thisPixel)) {
							return -1;
						}
					}
				}
				return 0 ; 
			}
			return -1;
		}

	public int[] getPixelAt(int row, int col) {
		int [] pixelValue= new int[4];	
		int p = bufferedImage.getRGB(col, row);

	    //get alpha
	    pixelValue[3] =((p>>24) & 0xff);
	    //get red
	    pixelValue[2] = ((p>>16) & 0xff);
	    //get green
	    pixelValue[1] = ((p>>8) & 0xff);
	    //get blue
	    pixelValue[0] = ((p) & 0xff);
	
		return pixelValue;		   
	}

	public void setPixelAt(int row, int col, int[] data) {
		int pixel = (data[2]<<16) | (data[1]<<8) | data[0];
		bufferedImage.setRGB(col, row, pixel);
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
