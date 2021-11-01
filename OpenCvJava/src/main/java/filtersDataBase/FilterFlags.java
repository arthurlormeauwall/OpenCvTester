package filtersDataBase;

import java.util.HashMap;

public class FilterFlags<T> 
{	
	public String filterName;
	public int numberOfParameters;
	public HashMap<String, T> defaultValues;
	public HashMap<String, T> zeroEffectValues;
}

