package guiManager;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FrameWindowManager {

	protected JFrame frameWindow;
	protected BufferedImage bufImage;
	
	FrameWindowManager(){
		frameWindow= new JFrame("Image");
		frameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
	}
	
	public void refresh(byte[] byteArray) {
		
		  InputStream in = new ByteArrayInputStream(byteArray);
		  BufferedImage bufImage = null;
		  try {
			  bufImage = ImageIO.read(in);
		  } catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		 
		  //Set Content to the JFrame
		   frameWindow.getContentPane().removeAll();
		   frameWindow.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
		   frameWindow.pack();
		   frameWindow.setVisible(true);
	}
}
