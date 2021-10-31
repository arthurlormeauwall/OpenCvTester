package baseClasses.history.historyParameters;

import baseClasses.chain.ChainControl;
import baseClasses.chain.ItemAndId;
import baseClasses.history.HistoryParameter;

public class ChainHistoryParameter<T> implements HistoryParameter<ItemAndId<T>> 
{
    protected ItemAndId<T> itemAndId;
    
	public ChainHistoryParameter(ItemAndId<T> itemAndId) {
		this.itemAndId=itemAndId;
	}
	 
	public ChainHistoryParameter() {
		itemAndId=new ItemAndId<T>();
	}
 
    public void set (ItemAndId<T> parameter) {
    	itemAndId.chainCommand= parameter.chainCommand;
    	itemAndId.item= parameter.item;
    	itemAndId.id= parameter.id;
    }
    
	public HistoryParameter<ItemAndId<T>> getNew() {
		return new ChainHistoryParameter<T>();
	}
	
	public void invert() {
        if (itemAndId.chainCommand == ChainControl.ADD) { itemAndId.chainCommand = ChainControl.DELETE; }
        else if (itemAndId.chainCommand == ChainControl.DELETE) { itemAndId.chainCommand = ChainControl.ADD; }
	}
	
	public ItemAndId<T> getParameter() {
		return itemAndId;
	}
	public ItemAndId<T> clone() {
		ItemAndId<T> newItemAndId= new ItemAndId<T>();
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