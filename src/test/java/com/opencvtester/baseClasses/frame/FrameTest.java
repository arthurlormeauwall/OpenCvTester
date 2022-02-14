package com.opencvtester.baseClasses.frame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.opencvtester.entity.Frame;

class FrameTest {

	Frame frame;

	@BeforeAll
	public static void setAll() {
		nu.pattern.OpenCV.loadLocally();
	}
	
	@BeforeEach
	public void setup() {
		frame=new Frame();
	}
	
	@Test
	void testCreatePlainGrayFrame() {
		int result=0;
		int a=-10, b=127, c=300;
		frame.createPlainGrayFrame(10, 10, a);
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				result+=frame.getPixelAt(j, i)[0];
				result+=frame.getPixelAt(j, i)[1];
				result+=frame.getPixelAt(j, i)[2];
			}
		}	
		assertEquals(0, result);
		
		result=0;
		frame.createPlainGrayFrame(10, 10, b);
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				result+=frame.getPixelAt(j, i)[0];
				result+=frame.getPixelAt(j, i)[1];
				result+=frame.getPixelAt(j, i)[2];
			}
		}
		assertEquals(300*b, result);
		
		result=0;
		frame.createPlainGrayFrame(10, 10, c);
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				result+=frame.getPixelAt(j, i)[0];
				result+=frame.getPixelAt(j, i)[1];
				result+=frame.getPixelAt(j, i)[2];
			}
		}	
		assertEquals(300*255, result);
	}

	@Test
	void testDeepCopy() {
		frame.createPlainGrayFrame(10, 10, 127);
		
		Frame frame2= new Frame(Frame.deepCopy(frame.getBufferedImage()));
		Frame frame3= new Frame(Frame.deepCopy(frame.getBufferedImage()));
		
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				assertEquals(frame.getPixelAt(j, i)[0], frame2.getPixelAt(j, i)[0]);
				assertEquals(frame.getPixelAt(j, i)[1], frame2.getPixelAt(j, i)[1]);
				assertEquals(frame.getPixelAt(j, i)[2], frame2.getPixelAt(j, i)[2]);
			}
		}
		
		frame.createPlainGrayFrame(10, 10, 0);
		
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				assertEquals(frame3.getPixelAt(j, i)[0], frame2.getPixelAt(j, i)[0]);
				assertEquals(frame3.getPixelAt(j, i)[1], frame2.getPixelAt(j, i)[1]);
				assertEquals(frame3.getPixelAt(j, i)[2], frame2.getPixelAt(j, i)[2]);
			}
		}	
	}

	@Test
	void testCompareTo() {
		frame.createPlainGrayFrame(10, 10, 127);
		Frame frame2= new Frame(10,10,127);
		
		assertTrue(frame.compareTo(frame2)==0);
	}

	@Test
	void testSetGetMat() {
		frame.createPlainGrayFrame(10, 10, 127);
		
		Mat mat = new Mat(frame.getBufferedImage().getHeight(), frame.getBufferedImage().getWidth(), CvType.CV_8UC3);
		frame.set(mat);
		
		Frame frame2= new Frame(Frame.deepCopy(frame.getBufferedImage()));
		
		assertTrue(frame2.compareTo(frame)==0);
	}


}
