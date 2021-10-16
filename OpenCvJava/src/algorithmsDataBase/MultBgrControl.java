package algorithmsDataBase;

import java.util.Stack;

import org.opencv.core.Mat;

import baseClasses.Control;
import baseClasses.Id;
import baseClasses.adjustControl.AdjustControlFloat;
import baseClasses.history.historyParameters.FloatHistoryParameter;
import baseClasses.history.imp.UndoHistory;

public class MultBgrControl extends AdjustControlFloat 
{
	public MultBgrControl(Id id) {	
		super(id);
		init();
	}
	
	public MultBgrControl(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) {	
		super(id, undoIdHistory, renderAtIdHistory);
		init();
	}
	
	public void init() {	
		setFlags();	
	}
	
	public void setFlags() {	
		Stack<Float> tempFloat= new Stack<Float>();
		tempFloat.push(0.5f);
		tempFloat.push(0.5f);
		tempFloat.push(0.5f);
		flags.defaultValues = tempFloat;
		flags.zeroEffectValues= tempFloat;
		
		Stack<String> tempString = new Stack<String>();
		tempString.push("BlueMult");
		tempString.push("GreenMult");
		tempString.push("BlueMult");
		flags.controlNames = tempString;
		
		flags.numberOfParameters = 3;
		
		history.setState(new FloatHistoryParameter(flags.defaultValues));
		history.store();
	}


	@Override
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
