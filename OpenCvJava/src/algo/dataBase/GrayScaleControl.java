package algo.dataBase;

import java.util.Stack;

import org.opencv.core.Mat;


import algo.AdjustControlFloat;
import baseClasses.Control;
import baseClasses.Id;
import baseClasses.history.historyParameters.FloatHistoryParameter;
import baseClasses.history.imp.UndoHistory;

public class GrayScaleControl extends AdjustControlFloat 
{

	public GrayScaleControl(Id id) {
		super(id);
		init();	
	}
	public GrayScaleControl(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) {
		super(id, undoIdHistory, renderAtIdHistory);
		init();
	}
	
	public void init() {
		
		setFlags();
	}
	
	public void setFlags() {
		Stack<Float> tempFloat= new Stack<Float>();
		tempFloat.push(0.1f);
		tempFloat.push(0.6f);
		tempFloat.push(0.3f);
		m_flags.defaultValues = tempFloat;
		tempFloat.clear();
		tempFloat.push(-1f);
		tempFloat.push(-1f);
		tempFloat.push(-1f);
		m_flags.zeroEffectValues= tempFloat;
		
		Stack<String> tempString = new Stack<String>();
		tempString.push("BlueMult");
		tempString.push("GreenMult");
		tempString.push("BlueMult");
		m_flags.controlNames = tempString;
		
		m_flags.numberOfControls = 3;
		
		m_history.setLast(new FloatHistoryParameter(m_flags.defaultValues));
		m_history.store();
	}

	@Override
	public void compute() {
		if (bypass) {
			m_dest=m_source;
		}

		if (!bypass)
	    {
	        Mat imgSource = m_source.getFrame();
	        Mat imgDest = m_dest.getFrame();

	        int m_row = imgSource.rows();
	        int m_column = imgSource.cols();
	        int bitMax = m_source.getSpecs().s_bitMax;

	        for (int row = 0; row < m_row; row++)
	        {
	            for (int column = 0; column < m_column; column++)
	            {
	                float blueMultiplier = (m_history.getLast().getParameter()).get(0);
	                float greenMultiplier = (m_history.getLast().getParameter()).get(1);
	                float redMultiplier = (m_history.getLast().getParameter()).get(2);

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
	        m_dest.setFrame(imgDest);	
	    }
		
	}

	@Override
	public Control clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
