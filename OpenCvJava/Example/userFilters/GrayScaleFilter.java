package userFilters;

import java.util.Stack;
import org.opencv.core.Mat;

import baseClasses.filter.FilterControlledByFloat;

public class GrayScaleFilter extends FilterControlledByFloat 
{
	public GrayScaleFilter() {
	}
	
	public void setParameterFlags() {

		addParameterFlag("BlueMult", 0.1f);
		addParameterFlag("GreenMult", 0.6f);
		addParameterFlag("RedMult", 0.3f);
		
		Stack<Float> zeroEffectValues= new Stack<Float>();
		zeroEffectValues.push(-1f);
		zeroEffectValues.push(-1f);
		zeroEffectValues.push(-1f);
		setZeroEffectValues(zeroEffectValues);
	}
	
	public GrayScaleFilter createNew() {	
		return new GrayScaleFilter();
	}

	public void compute() {
		if (isBypass) {
			source.copyTo(dest);
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
	            	float blueMultiplier = getParameter(0);
	            	float greenMultiplier = getParameter(1);
	            	float redMultiplier = getParameter(2);

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
