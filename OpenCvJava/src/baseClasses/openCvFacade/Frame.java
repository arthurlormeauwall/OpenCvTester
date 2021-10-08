package baseClasses.openCvFacade;


import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import baseClasses.enums_structs.FrameSpecs;


public class Frame {


	public Frame() { 
		m_frame = new Mat(0,0, CvType.CV_8UC3);
		m_specs= new FrameSpecs();
	}
	public Frame(String fileName) { 
		m_specs= new FrameSpecs();
		readFromFile(fileName);
	}
	public Frame(int rows, int cols, int data) { 
		m_specs= new FrameSpecs();
		Create1DFrame(rows, cols, data);
	}
	
	public void readFromFile(final String fileName) {

		m_frame = Imgcodecs.imread(fileName, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		setSpecs();	
	}

	public void copyTo(Frame newFrame) {
		newFrame.setFrame(m_frame.clone());
		newFrame.setSpecs();
	}

	public void Create1DFrame(int rows, int cols, int data){
		
		m_frame=new Mat(rows, cols, CvType.CV_8UC3);
		
		
		double[] temp= new double[3];
		
		for (int i=0; i< 3 ;i++) {
			temp[i]=data;
		} 
		for (int i=0 ; i < rows ;i++) {  
			
			for (int j=0 ; j < cols ; j++){ 
				
				m_frame.put(i, j, temp);
			}
		}
		setSpecs();

	}

	public Mat getFrame(){ 
		return m_frame; 
	}

	public void setFrame(Mat frame){
		m_frame = frame; 
		setSpecs();
	}

	public FrameSpecs getSpecs(){ 
		return m_specs;
	}
	
	public void setSpecs(){
		m_specs.s_cols = m_frame.cols();
		m_specs.s_rows = m_frame.rows();
		m_specs.s_bitMax = 255; //TODO change and read actual maxbitValue of image	
	}
	
	public void play() {
		HighGui.imshow("test", m_frame);
		HighGui.waitKey();
	}
	public Boolean compareTo(Frame p) {
		Boolean value=false; 
		
		int rowsP= p.getFrame().rows();
		int colsP=p.getFrame().cols();
		
		int rows = m_frame.rows();
		int cols= m_frame.cols();
		
		if (rowsP == rows && colsP == cols){
			for (int i=0; i<rows; i++) {
				for (int j=0; j<cols;j ++) {
					if (p.getFrame().get(i,j) == m_frame.get(i,j)) {
						value= true;
					}
					else {
						value = false;
					}
				}
			}
			
		}
		
		
		return value;
	}
	
	protected FrameSpecs m_specs;
	protected Mat m_frame;
	
}
