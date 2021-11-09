package filtersDataBase;
import java.util.LinkedHashMap;

import org.opencv.core.Mat;

import baseClasses.filter.FilterControlledByFloat;
import baseClasses.openCvFacade.Frame;

public class OpacityFilter extends FilterControlledByFloat 
{
	protected Frame background;
	private int opacity;	
	
	public OpacityFilter() {
	}
	
	public void init(Frame background) {
		setBackGround(background);
		setParameterFlags();
	}
	
	public void setParameterFlags() {	
		opacity=255; //TODO: replace this
		addParameterFlag("Opacity", Float.intBitsToFloat(255), Float.intBitsToFloat(255)); // TODO : replace this
	}

	
	public void execute() {
		if (isBypass) {
			source.copyTo(dest);
		}
		else if (!isBypass) {
			
			Mat imgSource = source.getMat();
			Mat imgDest = dest.getMat();
			Mat background = this.background.getMat();

			Float opacity = getParameter("Opacity");
			
			int NBITMAX = source.getSpecs().bitMax;

			int m_row = imgDest.rows();
			int m_column = imgDest.cols();

			for (int row = 0; row < m_row; row++)
			{
				for (int column = 0; column < m_column; column++)
				{
					double[] data= new double[3];
					for (int i = 0; i < 3; i++) {
						float alpha_pixel = opacity;
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
	
	
	public void setOpacity(Integer opacity){
		this.opacity=opacity;
	
		LinkedHashMap<String,Float> newOpacity= new LinkedHashMap<String, Float>();
		newOpacity.put("Opacity", opacity.floatValue());
		setParameter(newOpacity);
		
	}
	public int getOpacity() {
		return opacity;
	}

	public FilterControlledByFloat createNew() {
		
		return new OpacityFilter();
	}


	
	
}
