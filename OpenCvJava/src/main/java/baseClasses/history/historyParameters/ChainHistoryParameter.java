package baseClasses.history.historyParameters;

import baseClasses.chain.ChainControl;
import baseClasses.chain.ChainAction;
import baseClasses.history.HistoryParameter;

public class ChainHistoryParameter<T> implements HistoryParameter<ChainAction<T>> 
{
    protected ChainAction<T> itemAndId;
    
	public ChainHistoryParameter(ChainAction<T> itemAndId) {
		this.itemAndId=itemAndId;
	}
	 
	public ChainHistoryParameter() {
		itemAndId=new ChainAction<T>();
	}
 
    public void set (ChainAction<T> parameter) {
    	itemAndId.control= parameter.control;
    	itemAndId.item= parameter.item;
    	itemAndId.id= parameter.id;
    }
    
	public HistoryParameter<ChainAction<T>> getNew() {
		return new ChainHistoryParameter<T>();
	}
	
	public void invert() {
        if (itemAndId.control == ChainControl.ADD) { itemAndId.control = ChainControl.DELETE; }
        else if (itemAndId.control == ChainControl.DELETE) { itemAndId.control = ChainControl.ADD; }
	}
	
	public ChainAction<T> getParameter() {
		return itemAndId;
	}
	public ChainAction<T> clone() {
		ChainAction<T> newItemAndId= new ChainAction<T>();
		newItemAndId= itemAndId;
		
		return newItemAndId;
	}
	
	public Boolean isEmptyObject() {
		if (itemAndId.id.empty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setToEmptyObject() {
		itemAndId.id.clear();
	}
	
}