package com.sandbox.example.userFilters;

import com.opencvtester.baseClasses.filter.FilterControlledByFloat;

public class BlueGreenRedMultiplierFilter extends FilterControlledByFloat 
{
	public BlueGreenRedMultiplierFilter() {		
	}

	public void setParameterFlags() {	

		addParameterFlag("BlueMult", 1f, 1f, 200);
		addParameterFlag("GreenMult", 1f, 1f, 200);
		addParameterFlag("RedMult", 1f, 1f, 200);
	}
	
	public BlueGreenRedMultiplierFilter createNew() {	
		return new BlueGreenRedMultiplierFilter();
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

	                float afterBlue = blueMultiplier * bluePixel;
	                float afterGreen = greenMultiplier * greenPixel;
	                float afterRed = redMultiplier * redPixel;

	                if (afterBlue > bitMax) { afterBlue = bitMax; }
	                if (afterGreen > bitMax) { afterGreen = bitMax; }
	                if (afterRed > bitMax) { afterRed = bitMax; }

	                int[] temp= new int[3];
	                temp[0]=(int)afterBlue;
	                temp[1]=(int)afterGreen;
	                temp[2]=(int)afterRed;
	                
	          
	               frameOut.setPixelAt(rowCount, columnCount, temp);   
	            }
	        }     
	    }	
	}
}
