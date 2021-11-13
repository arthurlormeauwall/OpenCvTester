package guiManager;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class FrameWindowManager {

	protected JFrame frameWindow;
	
	FrameWindowManager(Mat mat){
		frameWindow= new JFrame("Image");
		frameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//Encoding the image
		  MatOfByte matOfByte = new MatOfByte();
		  Imgcodecs.imencode(".jpg", mat, matOfByte);
		  //Storing the encoded Mat in a byte array
		  byte[] byteArray = matOfByte.toArray();
		  //Preparing the Buffered Image
		  InputStream in = new ByteArrayInputStream(byteArray);
		  BufferedImage bufImage = null;
			try {
				bufImage = ImageIO.read(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  //Instantiate JFrame
		 
		  //Set Content to the JFrame
		   frameWindow.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
		   frameWindow.pack();
		   frameWindow.setVisible(true);	
	}
	
	public void refresh(Mat mat) {
		//Encoding the image
		  MatOfByte matOfByte = new MatOfByte();
		  Imgcodecs.imencode(".jpg", mat, matOfByte);
		  //Storing the encoded Mat in a byte array
		  byte[] byteArray = matOfByte.toArray();
		  //Preparing the Buffered Image
		  InputStream in = new ByteArrayInputStream(byteArray);
		  BufferedImage bufImage = null;
			try {
				bufImage = ImageIO.read(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  //Instantiate JFrame
		 
		  //Set Content to the JFrame
		   frameWindow.getContentPane().removeAll();
		   frameWindow.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
		   frameWindow.pack();
		   frameWindow.setVisible(true);
	}
}
