package baseClasses.history.historyParameters;

import baseClasses.enums_structs.ChainCommand;
import baseClasses.enums_structs.ItemAndId;
import baseClasses.history.HistoryParameter;

public class ChainHistoryParameter<T> implements HistoryParameter<ItemAndId<T>>
{
	 public ChainHistoryParameter(ItemAndId<T> p) {
		 m_p=p;
	 }
	 
    public ChainHistoryParameter() {
    	
    	m_p=new ItemAndId<T>();
    }
 
    public void set (ItemAndId<T> p) {
    	m_p.s_chainCommand= p.s_chainCommand;
    	m_p.s_control= p.s_control;
    	m_p.s_id= p.s_id;
    }
    
	public HistoryParameter<ItemAndId<T>> getNew() {
		return new ChainHistoryParameter<T>();
	}
	
	public void invert() {

        if (m_p.s_chainCommand == ChainCommand.ADD) { m_p.s_chainCommand = ChainCommand.DELETE; }
        else if (m_p.s_chainCommand == ChainCommand.DELETE) { m_p.s_chainCommand = ChainCommand.ADD; }
	}
	public ItemAndId<T> getParameter() {
		return m_p;
	}
    
    protected ItemAndId<T> m_p;
    
	
};