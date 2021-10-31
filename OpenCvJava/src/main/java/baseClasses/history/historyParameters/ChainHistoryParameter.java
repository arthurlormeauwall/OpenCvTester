package baseClasses.history.historyParameters;

import baseClasses.chain.ChainControl;
import baseClasses.chain.ChainAction;
import baseClasses.history.HistoryParameter;

public class ChainHistoryParameter<T> implements HistoryParameter<ChainAction<T>> 
{
    protected ChainAction<T> chainAction;
    
	public ChainHistoryParameter(ChainAction<T> itemAndId) {
		this.chainAction=itemAndId;
	}
	 
	public ChainHistoryParameter() {
		chainAction=new ChainAction<T>();
	}
 
    public void set (ChainAction<T> parameter) {
    	chainAction.control= parameter.control;
    	chainAction.item= parameter.item;
    	chainAction.id= parameter.id;
    }
    
	public HistoryParameter<ChainAction<T>> getNew() {
		return new ChainHistoryParameter<T>();
	}
	
	public void invert() {
        if (chainAction.control == ChainControl.ADD) { chainAction.control = ChainControl.DELETE; }
        else if (chainAction.control == ChainControl.DELETE) { chainAction.control = ChainControl.ADD; }
	}
	
	public ChainAction<T> getParameter() {
		return chainAction;
	}
	public ChainAction<T> clone() {
		ChainAction<T> newItemAndId= new ChainAction<T>();
		newItemAndId= chainAction;
		
		return newItemAndId;
	}
	
	public Boolean isEmptyObject() {
		if (chainAction.id.empty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setToEmptyObject() {
		chainAction.id.clear();
	}
	
}