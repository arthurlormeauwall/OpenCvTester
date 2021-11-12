package filtersDataBase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.Map.Entry;

import baseClasses.filter.FilterControlledByFloat;
import baseClasses.openCvFacade.Frame;

public class FiltersDataBase 
{
	protected HashMap<String, FilterControlledByFloat> filters;
	protected OpacityFilter alphaFilter;
	protected EmptyFilter emptyFilter;

	public FiltersDataBase() {
		filters= new HashMap<String, FilterControlledByFloat>();
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
		return new OpacityFilter();
	}
	
	public FilterFlags<Float> getFlags(String name){
		if (filters.get(name)== null) {
			return emptyFilter.getFlags();
		}
		else {
			return filters.get(name).getFlags();
		}
	}

	public Stack<String> getFiltersName() {
		Stack<String> filtersName= new Stack<String>();
		Iterator<Entry<String, FilterControlledByFloat>> new_Iterator= filters.entrySet().iterator();
		
	    while (new_Iterator.hasNext()) {
	    	HashMap.Entry<String, FilterControlledByFloat> filterItem= (HashMap.Entry<String, FilterControlledByFloat>) new_Iterator.next();
	    	filtersName.push(filterItem.getKey());
	    }
	    
	    return filtersName;
	}
}