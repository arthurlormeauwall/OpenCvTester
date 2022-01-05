package com.opencvtester.example.userFilters;

import com.opencvtester.baseClasses.filter.FilterControlledByFloat;

public class GrayScaleFilter extends FilterControlledByFloat 
{
	public GrayScaleFilter() {
	}
	
	public void setParameterFlags() {

		addParameterFlag("BlueMult", 0.1f, -1f);
		addParameterFlag("GreenMult", 0.6f, -1f);
		addParameterFlag("RedMult", 0.3f, -1f);
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
	        int row = source.getSpecs().rows;
	        int column = source.getSpecs().cols;
	        int bitMax = source.getSpecs().bitMax;

	        for (int rowCount = 0; rowCount < row; rowCount++)
	        {
	            for (int columnCount = 0; columnCount < column; columnCount++)
	            {
	            	float blueMultiplier = getParameter("BlueMult");
	                float greenMultiplier = getParameter("GreenMult");
	                float redMultiplier = getParameter("RedMult"); 

	                float bluePixel = (float)source.getPixelAt(rowCount, columnCount)[0];
	                float greenPixel = (float)source.getPixelAt(rowCount, columnCount)[1];
	                float redPixel = (float)source.getPixelAt(rowCount, columnCount)[2];

	                float after = blueMultiplier * bluePixel + greenMultiplier * greenPixel + redMultiplier * redPixel;
	                if (after > bitMax) { after = bitMax; }
	                double[] temp= new double[3];
	                temp[0]=after;
	                temp[1]=after;
	                temp[2]=after;
	                dest.setPixelAt(rowCount, columnCount, temp);
	            }
	        }   
	    }	
	}
}
