package com.opencvtester.filtersDataBase;
import java.util.LinkedHashMap;


import com.opencvtester.baseClasses.filter.FilterControlledByFloat;
import com.opencvtester.baseClasses.frame.FrameInterface;


public class OpacityFilter extends FilterControlledByFloat 
{
	protected FrameInterface background;
	private Float opacity;	
	
	public OpacityFilter() {
	}
	
	public void init(FrameInterface background) {
		setBackGround(background);
		setParameterFlags();
		setOpacity(flags.defaultValues.get("Opacity"));
	}
	
	public void setParameterFlags() {	
		opacity=1f; 
		addParameterFlag("Opacity", 1f,1f); // TODO : replace this
	}

	
	public void execute() {
		if (isBypass) {
			source.copyTo(dest);
		}
		else if (!isBypass) {
			
			Float opacity = getParameter("Opacity");
			
			int NBITMAX = source.getSpecs().bitMax;

			int m_row = dest.getSpecs().rows;
			int m_column = dest.getSpecs().cols;

			for (int row = 0; row < m_row; row++)
			{
				for (int column = 0; column < m_column; column++)
				{
					double[] data= new double[3];
					for (int i = 0; i < 3; i++) {
						float alpha_pixel = opacity;
						float background_pixel = (float)(background.getPixelAt(row, column)[i]);
						float source_pixel = (float)(source.getPixelAt(row, column)[i]);

						int after = (int)   (background_pixel*(1-alpha_pixel)+source_pixel*alpha_pixel);
						if (after > NBITMAX) { after = NBITMAX; }
						
						data[i] = after;
					}
					dest.setPixelAt(row, column, data);				
				}				
			}
		}
	}
		
	
	public void setBackGround(FrameInterface background){	
		this.background = background; 
	}
	
	
	public void setOpacity(Float opacity){
		this.opacity=opacity;
	
		LinkedHashMap<String,Float> newOpacity= new LinkedHashMap<String, Float>();
		newOpacity.put("Opacity", opacity);
		setParameter(newOpacity);	
	}
	public Float getOpacity() {
		return opacity;
	}

	public FilterControlledByFloat createNew() {
		
		return new OpacityFilter();
	}
}
