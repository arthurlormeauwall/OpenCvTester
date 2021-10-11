package baseClasses.history.historyParameters;

import baseClasses.Id;
import baseClasses.history.HistoryParameter;


public class IdHistoryParameter implements HistoryParameter<Id>
{

    public IdHistoryParameter() {
    	m_p= new Id();
    }
    public IdHistoryParameter(Id id) {
    	m_p= id;
    }
 
    
	public IdHistoryParameter getNew() {
		return new IdHistoryParameter();
	}
	
	public void invert() {   
	}
	
	public Id getParameter() {
		return m_p;
	}
    
	public void set(Id p) {
		m_p.get()[0]=p.get()[0];	
		m_p.get()[1]=p.get()[1];	
		m_p.setGroupId(p.getGroupId());
	}
	
	 protected Id m_p;	
};