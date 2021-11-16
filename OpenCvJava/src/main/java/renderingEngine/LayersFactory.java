package renderingEngine;

import java.util.Stack;

import baseClasses.Id;
import baseClasses.filter.Filter;
import baseClasses.frame.Frame;
import filtersDataBase.FiltersDataBase;

public class LayersFactory {

	private Layer newLayer;
	private FiltersDataBase filtersDataBase;
	private Frame background;
	private Frame source;
	private Frame dest;
	
	public LayersFactory(Frame background, Frame source, Frame dest, FiltersDataBase filtersDataBase) {
		this.background=background;
		this.source=source;
		this.dest=dest;
		this.filtersDataBase=filtersDataBase;
	}
	
	protected Filter createLayer(Stack<Id> id, Stack<String> filterNames){
		newLayer= new Layer(filtersDataBase, id.get(0));
		newLayer.init(background, source, dest);
		
		if (filterNames!=null) {
			int numberOfFilterToAdd = filterNames.size();

			for (int i = 0; i < numberOfFilterToAdd; i++) {		
				newLayer.createAndAdd(filterIds(id, i), filterNames(filterNames,i));
			}
		}
		
		return newLayer;
	}
	
	public Stack<Id> filterIds(Stack<Id> id, int index){
		Stack<Id> temp=new Stack<Id>();
		temp.push(id.get(index + 1));
		return temp;
	}
	public Stack<String> filterNames(Stack<String> names, int index){
		Stack<String> temp=new Stack<String>();
		temp.push(names.get(index));
		return temp;
	}
}
