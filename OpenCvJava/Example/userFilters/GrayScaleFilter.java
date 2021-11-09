package userFilters;

import org.opencv.core.Mat;

import baseClasses.filter.FilterControlledByFloat;

public class GrayScaleFilter extends FilterControlledByFloat 
{
	public GrayScaleFilter() {
	}
	
	public void setParameterFlags() {

		addParameterFlag("BlueMult", 0.1f, -1f);
		addParameterFlag("GreenMult", 0.6f, -1f);
		addParameterFlag("RedMult", 0.3f, -1f);
		
		setFilterName("GrayScaleFilter");
	}
	
	public GrayScaleFilter createNew() {	
		return new GrayScaleFilter();
	}

	public void execute() {
		if (isBypass) {
			source.copyTo(dest);
		}

		if (!isBypass)
	    {
	        Mat imgSource = source.getMat();
	        Mat imgDest = dest.getMat();

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

	                float after = blueMultiplier * bluePixel + greenMultiplier * greenPixel + redMultiplier * redPixel;
	                if (after > bitMax) { after = bitMax; }
	                double[] temp= new double[3];
	                temp[0]=after;
	                temp[1]=after;
	                temp[2]=after;
	                imgDest.put(row, column, temp);
	            }
	        }   
	        dest.setMat(imgDest);	
	    }	
	}
}
