package baseClasses.openCvFacade;


import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class Frame 
{
	final int NumberOfColorLayer = 3; 
	protected FrameSpecs frameSpecs;
	protected Mat frameMat;
	
	public Frame() { 
		frameMat = new Mat(0,0, CvType.CV_8UC3);
		frameSpecs= new FrameSpecs();
	}
	
	public Frame(String fileName) { 
		frameSpecs= new FrameSpecs();
		readFromFile(fileName);
	}
	
	public Frame(int rows, int cols, int data) { 
		frameSpecs= new FrameSpecs();
		createPlainGrayFrame(rows, cols, data);
	}
	
	public void readFromFile(final String fileName) {
		frameMat = Imgcodecs.imread(fileName, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		setSpecs();	
	}

	public void copyTo(Frame newFrame) {
		newFrame.setMat(frameMat.clone());
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
	
	public void play() {
		HighGui.imshow("test", frameMat);  // TODO : replace "test" by name of app, in global static variable
		HighGui.waitKey();
	}
	
	public Boolean compareTo(Frame p) {
		
		int rowsP= p.getMat().rows();
		int colsP=p.getMat().cols();
		
		int rows = frameMat.rows();
		int cols= frameMat.cols();
		
		if (rowsP == rows && colsP == cols){
			
			for (int i=0; i<rows; i++) {
				for (int j=0; j<cols;j ++) {
					if (p.getMat().get(i,j) != frameMat.get(i,j)) {
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
		frameMat = frame; 
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
}
