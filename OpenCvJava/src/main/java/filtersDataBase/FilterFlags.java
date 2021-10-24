package filtersDataBase;

import java.util.Stack;

public class FilterFlags<T> 
{	
	public Stack<String> controlNames;
	public int numberOfParameters;
	public T defaultValues;
	public T zeroEffectValues;
}

