package userFilters;

import baseClasses.filter.FilterControlledByFloat;


public class BlueGreenRedMultiplierFilter extends FilterControlledByFloat 
{
	public BlueGreenRedMultiplierFilter() {		
	}

	public void setParameterFlags() {	

		addParameterFlag("BlueMult", 1f, 1f);
		addParameterFlag("GreenMult", 1f, 1f);
		addParameterFlag("RedMult", 1f, 1f);
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
	                
	               dest.setPixelAt(rowCount, columnCount, temp);   
	            }
	        }
	    }	
	}
}
