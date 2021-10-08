package algo.dataBase;

import java.util.Stack;

import org.opencv.core.Mat;

import algo.AdjustControlFrame;
import baseClasses.Control;
import baseClasses.Id;
import baseClasses.history.FrameHistoryParameter;
import baseClasses.history.imp.UndoHistory;
import baseClasses.openCvFacade.Frame;

public class AlphaControl extends AdjustControlFrame {

	public AlphaControl(Id id) {
		super(id);
		m_history.setState(new FrameHistoryParameter());
	}
	
	public AlphaControl(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) {
		super(id, undoIdHistory, renderAtIdHistory);		
	}
	
public void init() {

		setFlags();
	}
	
	public void setFlags() {
		
		m_flags.defaultValues = new Frame(m_background.getFrame().rows(), m_background.getFrame().cols(), 255);
		m_flags.zeroEffectValues= new Frame(m_background.getFrame().rows(), m_background.getFrame().cols(), 255);
		
		Stack<String> tempString = new Stack<String>();
		tempString.push("Alpha");
		m_flags.controlNames = tempString;
		
		m_flags.numberOfControls = 1;
		
		m_history.setLast(new FrameHistoryParameter(m_flags.defaultValues));
		m_history.store();
	}

	
	public void compute() {
		if (bypass) {
			m_dest.setFrame(m_source.getFrame());
		}
		else if (!bypass) {
			
			Mat imgSource = m_source.getFrame();
			Mat imgDest = m_dest.getFrame();
			Mat background = m_background.getFrame();

			Mat alpha = m_history.getLast().getParameter().getFrame();
			int NBITMAX = m_source.getSpecs().s_bitMax;

			int m_row = imgDest.rows();
			int m_column = imgDest.cols();

			for (int row = 0; row < m_row; row++)
			{
				for (int column = 0; column < m_column; column++)
				{
					double[] data= new double[3];
					for (int i = 0; i < 3; i++) {
						float alpha_pixel = (float)(alpha.get(row, column)[0]);
						float background_pixel = (float)(background.get(row, column)[i]);
						float source_pixel = (float)(imgSource.get(row, column)[i]);

						int after = (int)((1 - (alpha_pixel / (float)(NBITMAX))) * background_pixel + alpha_pixel / (float)(NBITMAX) * source_pixel);
						if (after > NBITMAX) { after = NBITMAX; }
						
						data[i] = after;
					}
					imgDest.put(row, column, data);				
				}				
			}
			m_dest.setFrame(imgDest);
		}
	}
		
	
	public void setBackGround(Frame bg){
		
		m_background = bg; 
	}
	

	public void setAlpha(Frame alpha){
		setParameter(alpha);	
	}
	public void setAlpha(int opacity){
		
		Frame alpha = new Frame();
		alpha.Create1DFrame(m_source.getFrame().rows(), m_source.getFrame().cols(), opacity);
		setParameter(alpha);
		
	}
	public Frame getAlpha() {
		return m_history.getLast().getParameter();
	}
	
	@Override
	public Control clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected Frame m_background;	
}
