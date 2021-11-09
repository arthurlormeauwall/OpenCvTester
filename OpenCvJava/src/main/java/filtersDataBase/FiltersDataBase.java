package filtersDataBase;

import java.util.HashMap;

import baseClasses.Command;
import baseClasses.filter.Filter;
import baseClasses.filter.FilterControlledByFloat;

public class FiltersDataBase 
{
	protected HashMap<String, FilterControlledByFloat> filters;
	protected OpacityFilter alphaFilter;
	protected EmptyFilter emptyFilter;

	public FiltersDataBase() {
		filters= new HashMap<String, FilterControlledByFloat>();
		alphaFilter = new OpacityFilter();
		emptyFilter= new EmptyFilter();
	}
	
	public FilterControlledByFloat getFilter(String name){
		if (filters.get(name)== null) {
			return emptyFilter;
		}
		else {
			return filters.get(name).createNew();	
		}	
	}
	
	public void addFilter(String name, FilterControlledByFloat control) {
		filters.put(name, control);
	}
	
	public OpacityFilter getAlphaFilter(){
		return alphaFilter;
	}
	
	public FilterFlags<Float> getFlags(String name){
		if (filters.get(name)== null) {
			return emptyFilter.getFlags();
		}
		else {
			return filters.get(name).getFlags();
		}
	}
}