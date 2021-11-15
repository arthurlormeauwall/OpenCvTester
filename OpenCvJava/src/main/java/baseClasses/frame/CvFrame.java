package baseClasses.frame;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class CvFrame implements Frame
{
	final int NumberOfColorLayer = 3; 
	protected FrameSpecs frameSpecs;
	protected Mat frameMat;
	
	public CvFrame() { 
		frameMat = new Mat(0,0, CvType.CV_8UC3);
		frameSpecs= new FrameSpecs();	
	}
	
	public CvFrame(String fileName) { 
		frameSpecs= new FrameSpecs();
		readFromFile(fileName);
	}
	
	public CvFrame(int rows, int cols, int data) { 
		frameSpecs= new FrameSpecs();
		createPlainGrayFrame(rows, cols, data);
	}
	
	public void readFromFile(final String fileName) {
		frameMat = Imgcodecs.imread(fileName, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		setSpecs();	
	}

	public void copyTo(Frame newFrame) {
		((CvFrame)newFrame).setMat(frameMat.clone());
		newFrame.setSpecs();
	}

	public void createPlainGrayFrame(int rows, int cols, int data){
		frameMat=new Mat(rows, cols, CvType.CV_8UC3);
		
		double[] temp= new double[NumberOfColorLayer];
		
		for (int i=0; i< NumberOfColorLayer ;i++) {
			temp[i]=data;
		} 
		
		for (int i=0 ; i < rows ;i++) {  	
			for (int j=0 ; j < cols ; j++){ 	
				frameMat.put(i, j, temp);
			}
		}
		setSpecs();
	}
	
	public Boolean compareTo(Frame frame) {

		int rowsP= ((CvFrame)frame).getMat().rows();
		int colsP=((CvFrame)frame).getMat().cols();
		
		int rows = frameMat.rows();
		int cols= frameMat.cols();
		
		if (rowsP == rows && colsP == cols){
			
			for (int i=0; i<rows; i++) {
				for (int j=0; j<cols;j ++) {
					if (((CvFrame)frame).getMat().get(i,j) != frameMat.get(i,j)) {
						return false;
					}
				}
			}
			return true ; 
		}
		return false;
	}

	public Mat getMat(){ 
		return frameMat; 
	}

	public void setMat(Mat frame){
		frameMat = frame.clone(); 
		setSpecs();
	}

	public FrameSpecs getSpecs(){ 
		return frameSpecs;
	}
	
	public void setSpecs(){
		frameSpecs.cols = frameMat.cols();
		frameSpecs.rows = frameMat.rows();
		frameSpecs.bitMax = 255; //TODO change and read actual maxbitValue of image	
	}
	
	public BufferedImage toBufferedImage() {
		
		 //Encoding the image
		  MatOfByte matOfByte = new MatOfByte();
		  Imgcodecs.imencode(".jpg", frameMat, matOfByte);
		  //Storing the encoded Mat in a byte array
		  byte[] byteArray = matOfByte.toArray();
		  InputStream in = new ByteArrayInputStream(byteArray);
		  BufferedImage bufImage = null;
		  try {
			  bufImage = ImageIO.read(in);
		  } catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  } 	  
		  return bufImage;
	}

	public double[] getPixelAt(int row, int col) {		
		return frameMat.get(row, col);
	}

	public void setPixelAt(int row, int col, double[] data) {	
		frameMat.put(row, col, data);
	}
}
