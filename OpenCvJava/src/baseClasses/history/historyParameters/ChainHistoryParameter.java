package baseClasses.history.historyParameters;

import baseClasses.chain.ChainCommand;
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
        if (itemAndId.chainCommand == ChainCommand.ADD) { itemAndId.chainCommand = ChainCommand.DELETE; }
        else if (itemAndId.chainCommand == ChainCommand.DELETE) { itemAndId.chainCommand = ChainCommand.ADD; }
	}
	
	public ItemAndId<T> getParameter() {
		return itemAndId;
	}
}