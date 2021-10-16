package algorithmsDataBase;

import java.util.Stack;

import org.opencv.core.Mat;

import baseClasses.Control;
import baseClasses.adjustControl.AdjustControlFrame;
import baseClasses.history.historyParameters.FrameHistoryParameter;
import baseClasses.openCvFacade.Frame;

public class AlphaControl extends AdjustControlFrame 
{
	protected Frame background;	
	
	public AlphaControl() {
		super();
	}
	
	public void init() {
		setFlags();
		history.setState(new FrameHistoryParameter(flags.defaultValues));
		history.store();
	}
	
	public void setFlags() {	
		flags.defaultValues = new Frame(background.getFrame().rows(), background.getFrame().cols(), 255);
		flags.zeroEffectValues= new Frame(background.getFrame().rows(), background.getFrame().cols(), 255);
		
		Stack<String> tempString = new Stack<String>();
		tempString.push("Alpha");
		flags.controlNames = tempString;
		
		flags.numberOfParameters = 1;
		
		
	}

	
	public void compute() {
		if (isBypass) {
			dest.setFrame(source.getFrame());
		}
		else if (!isBypass) {
			
			Mat imgSource = source.getFrame();
			Mat imgDest = dest.getFrame();
			Mat background = this.background.getFrame();

			Mat alpha = history.getState().getParameter().getFrame();
			int NBITMAX = source.getSpecs().bitMax;

			int m_row = imgDest.rows();
			int m_column = imgDest.cols();

			for (int row = 0; row < m_row; row++)
			{
				for (int column = 0; column < m_column; column++)
				{
					double[] data= new double[3];
					for (int i = 0; i < 3; i++) {
						float alpha_pixel = (float)(alpha.get(row, column)[0]);
						float background_pixel = (float)(background.get(row, column)[i]);
						float source_pixel = (float)(imgSource.get(row, column)[i]);

						int after = (int)((1 - (alpha_pixel / (float)(NBITMAX))) * background_pixel + alpha_pixel / (float)(NBITMAX) * source_pixel);
						if (after > NBITMAX) { after = NBITMAX; }
						
						data[i] = after;
					}
					imgDest.put(row, column, data);				
				}				
			}
			dest.setFrame(imgDest);
		}
	}
		
	
	public void setBackGround(Frame bg){	
		background = bg; 
	}
	

	public void setAlpha(Frame alpha){
		setParameter(alpha);	
	}
	
	public void setAlpha(int opacity){
		Frame alpha = new Frame();
		alpha.Create1DFrame(source.getFrame().rows(), source.getFrame().cols(), opacity);
		setParameter(alpha);
		
	}
	public Frame getAlpha() {
		return history.getState().getParameter();
	}
	
	@Override
	public Control clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
