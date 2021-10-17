package userAlgorithms;

import java.util.Stack;

import org.opencv.core.Mat;

import baseClasses.adjustControl.AdjustControlFloat;

public class MultBgrControl extends AdjustControlFloat 
{
	public MultBgrControl() {	
		
	}

	public void setParameterFlags() {	

		addParameterFlag("BlueMult", 0.5f);
		addParameterFlag("GreenMult", 0.5f);
		addParameterFlag("RedMult", 0.5f);
		
		Stack<Float> zeroEffectValues= new Stack<Float>();
		zeroEffectValues.push(1f);
		zeroEffectValues.push(1f);
		zeroEffectValues.push(1f);
		setZeroEffectValues(zeroEffectValues);
	}


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
	                float blueMultiplier = getParameter(0);
	                float greenMultiplier = getParameter(1);
	                float redMultiplier = getParameter(2);

	                float bluePixel = (float)imgSource.get(row, column)[0];
	                float greenPixel = (float)imgSource.get(row, column)[1];
	                float redPixel = (float)imgSource.get(row, column)[2];

	                float afterBlue = blueMultiplier * bluePixel;
	                float afterGreen = greenMultiplier * greenPixel;
	                float afterRed = redMultiplier * redPixel;

	                if (afterBlue > bitMax) { afterBlue = bitMax; }
	                if (afterGreen > bitMax) { afterGreen = bitMax; }
	                if (afterRed > bitMax) { afterRed = bitMax; }

	                double[] temp= new double[3];
	                temp[0]=afterBlue;
	                temp[1]=afterGreen;
	                temp[2]=afterRed;
	                
	                imgDest.put(row, column, temp);
	            }
	        }
	        dest.setFrame(imgDest);
	    }	
	}
}
