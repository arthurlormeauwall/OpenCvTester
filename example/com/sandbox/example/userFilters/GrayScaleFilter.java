package com.sandbox.example.userFilters;

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
			frameIn.copyTo(frameOut);
		}

		if (!isBypass)
	    {
	        int row = frameIn.getSpecs().rows;
	        int column = frameIn.getSpecs().cols;
	        int bitMax = frameIn.getSpecs().bitMax;

	        for (int rowCount = 0; rowCount < row; rowCount++)
	        {
	            for (int columnCount = 0; columnCount < column; columnCount++)
	            {
	            	float blueMultiplier = getParameter("BlueMult");
	                float greenMultiplier = getParameter("GreenMult");
	                float redMultiplier = getParameter("RedMult"); 

	                float bluePixel = (float)frameIn.getPixelAt(rowCount, columnCount)[0];
	                float greenPixel = (float)frameIn.getPixelAt(rowCount, columnCount)[1];
	                float redPixel = (float)frameIn.getPixelAt(rowCount, columnCount)[2];

	                float after = blueMultiplier * bluePixel + greenMultiplier * greenPixel + redMultiplier * redPixel;
	                if (after > bitMax) { after = bitMax; }
	                int[] temp= new int[3];
	                temp[0]=(int)after;
	                temp[1]=(int)after;
	                temp[2]=(int)after;
	                frameOut.setPixelAt(rowCount, columnCount, temp);
	            }
	        }   
	    }	
	}
}
