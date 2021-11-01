package filtersDataBase;

import java.util.HashMap;

import org.opencv.core.Mat;

import baseClasses.filter.FilterControlledByFrame;
import baseClasses.history.historyParameters.FrameHistoryParameter;
import baseClasses.openCvFacade.Frame;

public class OpacityFilter extends FilterControlledByFrame 
{
	protected Frame background;	
	
	public OpacityFilter() {
	}
	
	public void init(Frame background) {
		setBackGround(background);
		setFlags();
		history.setState(new FrameHistoryParameter(flags.defaultValues));
	}
	
	private void setFlags() {		
		addParameterFlag("Opacity",  new Frame(background.getMat().rows(), background.getMat().cols(), background.getSpecs().bitMax), new Frame(background.getMat().rows(), background.getMat().cols(), background.getSpecs().bitMax));
	
	}

	
	public void execute() {
		if (isBypass) {
			source.copyTo(dest);
		}
		else if (!isBypass) {
			
			Mat imgSource = source.getMat();
			Mat imgDest = dest.getMat();
			Mat background = this.background.getMat();

			Mat alpha = getParameter().get("Opacity").getMat();
			int NBITMAX = source.getSpecs().bitMax;

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
			dest.setMat(imgDest);
		}
	}
		
	
	public void setBackGround(Frame background){	
		this.background = background; 
	}
	

	public void setAlpha(Frame alpha){
		HashMap<String,Frame> newAlpha= new HashMap<String, Frame>();
		newAlpha.put("Opacity", alpha);
		setParameter(newAlpha);	
	}
	
	public void setAlpha(int opacity){
		Frame alpha = new Frame();
		alpha.createPlainGrayFrame(source.getMat().rows(), source.getMat().cols(), opacity);
		HashMap<String,Frame> newAlpha= new HashMap<String, Frame>();
		newAlpha.put("Opacity", alpha);
		setParameter(newAlpha);
		
	}
	public Frame getAlpha() {
		return history.getState().getParameter().get("Opacity");
	}
}
