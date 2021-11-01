package userFilters;


import org.opencv.core.Mat;

import baseClasses.filter.FilterControlledByFloat;


public class BlueGreenRedMultiplierFilter extends FilterControlledByFloat 
{
	public BlueGreenRedMultiplierFilter() {		
	}

	public void setParameterFlags() {	

		addParameterFlag("BlueMult", 0.5f, 1f);
		addParameterFlag("GreenMult", 0.5f, 1f);
		addParameterFlag("RedMult", 0.5f, 1f);
	}
	
	public BlueGreenRedMultiplierFilter createNew() {	
		return new BlueGreenRedMultiplierFilter();
	}

	public void execute() {	
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
	                float blueMultiplier = getParameter("BlueMult");
	                float greenMultiplier = getParameter("GreenMult");
	                float redMultiplier = getParameter("RedMult"); 

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
