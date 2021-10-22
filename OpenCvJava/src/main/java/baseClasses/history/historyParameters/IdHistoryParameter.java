package baseClasses.history.historyParameters;

import baseClasses.Id;
import baseClasses.history.HistoryParameter;

public class IdHistoryParameter implements HistoryParameter<Id> 
{
	protected Id id;	
	
    public IdHistoryParameter() {
    	id= new Id();
    	id.initNULL();
    }
    public IdHistoryParameter(Id id) {
    	this.id= id;
    }
  
	public IdHistoryParameter getNew() {
		return new IdHistoryParameter();
	}
	
	public void invert() {   
	}
	
	public Id getParameter() {
		return id;
	}
    
	public void set(Id p) {
		id.get()[0]=p.get()[0];	
		id.get()[1]=p.get()[1];	
		id.setGroupId(p.getGroupId());
	}
	
	public Id clone() {
		Id newId= new Id();
		newId= id;
		return newId;
	}
	
	public Boolean isEmptyObject() {
		if (id.getGroupId()== 0 && id.get()[0]==-1) {return true;}
		else {return false;}
	}
	public void setToEmptyObject() {
		id.set(-1,-1,0);
	}
};