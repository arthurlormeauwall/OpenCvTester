package filtersDataBase;

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
		addParameterFlag("Opacity",  new Frame(background.getFrame().rows(), background.getFrame().cols(), background.getSpecs().bitMax));
		setZeroEffectValues( new Frame(background.getFrame().rows(), background.getFrame().cols(), background.getSpecs().bitMax));	
	}

	
	public void compute() {
		if (isBypass) {
			source.copyTo(dest);
		}
		else if (!isBypass) {
			
			Mat imgSource = source.getFrame();
			Mat imgDest = dest.getFrame();
			Mat background = this.background.getFrame();

			Mat alpha = getParameter().getFrame();
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
			dest.setFrame(imgDest);
		}
	}
		
	
	public void setBackGround(Frame background){	
		this.background = background; 
	}
	

	public void setAlpha(Frame alpha){
		setParameter(alpha);	
	}
	
	public void setAlpha(int opacity){
		Frame alpha = new Frame();
		alpha.createPlainGrayFrame(source.getFrame().rows(), source.getFrame().cols(), opacity);
		setParameter(alpha);
		
	}
	public Frame getAlpha() {
		return history.getState().getParameter();
	}
}
