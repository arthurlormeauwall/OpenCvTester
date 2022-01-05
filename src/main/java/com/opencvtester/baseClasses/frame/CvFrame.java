package com.opencvtester.baseClasses.frame;

import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class CvFrame extends DefaultFrame
{
	static Boolean isInitialized=false;
	final int NumberOfColorLayer = 3; 
	
	public CvFrame() { 
		if (!isInitialized) {
			nu.pattern.OpenCV.loadLocally();
			isInitialized=true;
		}	
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
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  } 	  
	}

	public Frame create() {
		return new CvFrame();
	}
}
