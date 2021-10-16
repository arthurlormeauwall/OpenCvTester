package algorithmsDataBase;

import java.util.Stack;
import org.opencv.core.Mat;
import baseClasses.adjustControl.AdjustControlFloat;

public class GrayScaleControl extends AdjustControlFloat 
{
	public GrayScaleControl() {
	}
	
	
	public void setParameterFlags() {

		addParameter("BlueMult", 0.1f);
		addParameter("GreenMult", 0.6f);
		addParameter("RedMult", 0.3f);
		
		Stack<Float> zeroEffectValues= new Stack<Float>();
		zeroEffectValues.push(-1f);
		zeroEffectValues.push(-1f);
		zeroEffectValues.push(-1f);
		setZeroEffectValues(zeroEffectValues);
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
}
