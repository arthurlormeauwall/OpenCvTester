package algorithmsDataBase;

import java.util.Stack;

import org.opencv.core.Mat;

import baseClasses.Control;
import baseClasses.adjustControl.AdjustControlFloat;
import baseClasses.history.historyParameters.FloatHistoryParameter;

public class GrayScaleControl extends AdjustControlFloat 
{
	public GrayScaleControl() {
		super();
	}
	
	
	public void setFlags() {
		Stack<Float> tempFloat= new Stack<Float>();
		tempFloat.push(0.1f);
		tempFloat.push(0.6f);
		tempFloat.push(0.3f);
		flags.defaultValues = tempFloat;
		tempFloat.clear();
		tempFloat.push(-1f);
		tempFloat.push(-1f);
		tempFloat.push(-1f);
		flags.zeroEffectValues= tempFloat;
		
		Stack<String> tempString = new Stack<String>();
		tempString.push("BlueMult");
		tempString.push("GreenMult");
		tempString.push("BlueMult");
		flags.controlNames = tempString;
		
		flags.numberOfParameters = 3;
		
		history.setState(new FloatHistoryParameter(flags.defaultValues));
		history.store();
	}

	@Override
	public void compute() {
		if (isBypass) {
			dest=source;
		}

		if (!isBypass)
	    {
	        Mat imgSource = source.getFrame();
	        Mat imgDest = dest.getFrame();

	        int m_row = imgSource.rows();
	        int m_column = imgSource.cols();
	        int bitMax = source.getSpecs().bitMax;

	        for (int row = 0; row < m_row; row++)
	        {
	            for (int column = 0; column < m_column; column++)
	            {
	                float blueMultiplier = (history.getState().getParameter()).get(0);
	                float greenMultiplier = (history.getState().getParameter()).get(1);
	                float redMultiplier = (history.getState().getParameter()).get(2);

	                float bluePixel = (float)imgSource.get(row, column)[0];
	                float greenPixel = (float)imgSource.get(row, column)[1];
	                float redPixel = (float)imgSource.get(row, column)[2];

	                float after = blueMultiplier * bluePixel + greenMultiplier * greenPixel + redMultiplier * redPixel;
	                if (after > bitMax) { after = bitMax; }
	                double[] temp= new double[3];
	                temp[0]=after;
	                temp[1]=after;
	                temp[2]=after;
	                imgDest.put(row, column, temp);
	            }
	        }   
	        dest.setFrame(imgDest);	
	    }	
	}

	@Override
	public Control clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
