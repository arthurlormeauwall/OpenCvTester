package baseClasses.history.historyParameters;

import baseClasses.enums_structs.ChainCommand;
import baseClasses.enums_structs.ItemAndId;
import baseClasses.history.HistoryParameter;

public class ChainHistoryParameter<T> implements HistoryParameter<ItemAndId<T>>
{
	
	public ChainHistoryParameter(ItemAndId<T> parameter) {
		m_p=parameter;
	}
	 
	public ChainHistoryParameter() {
		m_p=new ItemAndId<T>();
	}
 
    public void set (ItemAndId<T> parameter) {
    	m_p.m_chainCommand= parameter.m_chainCommand;
    	m_p.m_control= parameter.m_control;
    	m_p.m_id= parameter.m_id;
    }
    
	public HistoryParameter<ItemAndId<T>> getNew() {
		return new ChainHistoryParameter<T>();
	}
	
	public void invert() {
        if (m_p.m_chainCommand == ChainCommand.ADD) { m_p.m_chainCommand = ChainCommand.DELETE; }
        else if (m_p.m_chainCommand == ChainCommand.DELETE) { m_p.m_chainCommand = ChainCommand.ADD; }
	}
	
	public ItemAndId<T> getParameter() {
		return m_p;
	}
    
    protected ItemAndId<T> m_p;
};