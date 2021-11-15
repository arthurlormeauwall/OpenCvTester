package filtersDataBase;
import java.util.LinkedHashMap;

import baseClasses.filter.FilterControlledByFloat;
import baseClasses.frame.Frame;

public class OpacityFilter extends FilterControlledByFloat 
{
	protected Frame background;
	private Float opacity;	
	
	public OpacityFilter() {
	}
	
	public void init(Frame background) {
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

			int row = dest.getSpecs().rows;
			int column =dest.getSpecs().cols;

			for (int rowCount = 0; rowCount < row; rowCount++)
			{
				for (int colCount = 0; colCount < column; colCount++)
				{
					double[] data= new double[3];
					for (int i = 0; i < 3; i++) {
						float alpha_pixel = opacity;
						float background_pixel = (float)(background.getPixelAt(rowCount, colCount)[i]);
						float source_pixel = (float)(source.getPixelAt(rowCount, colCount)[i]);

						int after = (int)   (background_pixel*(1-alpha_pixel)+source_pixel*alpha_pixel);
						if (after > NBITMAX) { after = NBITMAX; }
						
						data[i] = after;
					}
					dest.setPixelAt(row, column, data);				
				}				
			}
		}
	}
		
	
	public void setBackGround(Frame background){	
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
