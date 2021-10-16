package algorithmsDataBase;

import org.opencv.core.Mat;

import baseClasses.Control;
import baseClasses.adjustControl.AdjustControlFloat;

public class MultBgrControl extends AdjustControlFloat 
{
	public MultBgrControl() {	
		super();
	}

	public void setFlags() {	

		addParameterToFlags("BlueMult", 0.5f);
		addParameterToFlags("GreenMult", 0.5f);
		addParameterToFlags("RedMult", 0.5f);
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
	                float blueMultiplier = (history.getState().getParameter()).get(0);
	                float greenMultiplier = (history.getState().getParameter()).get(1);
	                float redMultiplier = (history.getState().getParameter()).get(2);

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

	@Override
	public Control clone() {
		// TODO Auto-generated method stub
		return null;
	}
}
