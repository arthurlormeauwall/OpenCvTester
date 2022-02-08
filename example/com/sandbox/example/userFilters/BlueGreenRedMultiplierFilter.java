package com.sandbox.example.userFilters;

import com.opencvtester.baseClasses.filter.FilterControlledByFloat;

public class BlueGreenRedMultiplierFilter extends FilterControlledByFloat 
{
	public BlueGreenRedMultiplierFilter(String name) {		
		super(name);
	}

	public void setParameterFlags() {		
		addParameterFlag("BlueMult", 1f, 1f, 130);
		addParameterFlag("GreenMult", 1f, 1f, 130);
		addParameterFlag("RedMult", 1f, 1f, 130);
	}
	
	public BlueGreenRedMultiplierFilter createNew() {	
		 BlueGreenRedMultiplierFilter temp= new BlueGreenRedMultiplierFilter(getFilterName());
		 temp.setParameterFlags();
		 return temp;
	}

	public void execute() {	
		if (isBypass) {
			frameIn.copyTo(frameOut);
		}

		else
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
