package com.opencvtester.filtersDataBase;
import java.util.LinkedHashMap;


import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.baseClasses.frame.FrameInterface;


public class OpacityFilter extends FilterControlledByFloat 
{
	protected FrameInterface background;
	private Float opacity;	
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public OpacityFilter() {
	}
	
	public void init(FrameInterface background) {
		setBackGround(background);
		setParameterFlags();
		setOpacity(flags.defaultValues.get("Opacity"));
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public void setParameterFlags() {	
		opacity=1f; 
		addParameterFlag("Opacity", 1f,1f, 100); 
	}

	public void setBackGround(FrameInterface background){	
		this.background = background; 
	}
	
	public void setOpacity(Float opacity){
		this.opacity=opacity;
	
		LinkedHashMap<String,Float> newOpacity= new LinkedHashMap<String, Float>();
		newOpacity.put("Opacity", opacity);
		setAllParameters(newOpacity);	
	}
	
	public Float getOpacity() {
		return opacity;
	}
	
	public FilterControlledByFloat createNew() {	
		return new OpacityFilter();
	}

	/*
	 * FEATURES
	 */
	public void execute() {
		if (isBypass) {
			frameIn.copyTo(frameOut);
		}
		else if (!isBypass) {
			
			Float opacity = getParameter("Opacity");
			
			int NBITMAX = frameIn.getSpecs().bitMax;

			int m_row = frameOut.getSpecs().rows;
			int m_column = frameOut.getSpecs().cols;

			for (int row = 0; row < m_row; row++)
			{
				for (int column = 0; column < m_column; column++)
				{
					int[] data= new int[3];
					for (int i = 0; i < 3; i++) {
						float alpha_pixel = opacity;
						float background_pixel = (float)(background.getPixelAt(row, column)[i]);
						float source_pixel = (float)(frameIn.getPixelAt(row, column)[i]);

						int after = Math.round((background_pixel*(1-alpha_pixel)+source_pixel*alpha_pixel));
						if (after > NBITMAX) { after = NBITMAX; }
						
						data[i] = after;
					}
					frameOut.setPixelAt(row, column, data);				
				}				
			}
		}
	}
}
