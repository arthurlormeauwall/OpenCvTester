package baseClasses;

import baseClasses.history.IdHistoryParameter;
import baseClasses.history.imp.UndoHistory;


public abstract class Control extends Command
{

    public Control() {
        
		m_id = new Id ();                                 
		m_undoIdHistory = new UndoHistory<Id>();
		m_renderAtIdHistory= new UndoHistory<Id>();  
 	
		 initControl();
    }
    public Control(Id id) {
    	
	    m_id = new Id();
	    m_id.set(id);
	    
	    m_undoIdHistory = new UndoHistory<Id>();
		m_renderAtIdHistory= new UndoHistory<Id>(); 

	    bypass = false;
	    initControl();
}
    public Control(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) {
	
    	    m_id = new Id();
    	    m_id.set(id);
    	    
    	    m_undoIdHistory = new UndoHistory<Id>();
    		m_renderAtIdHistory= new UndoHistory<Id>(); 

    	    m_undoIdHistory = undoIdHistory;
    	    m_renderAtIdHistory = renderAtIdHistory;
    	    bypass = false;
    	    
    	    initControl();
    }
    
    public void initControl() {
    	
    	m_undoIdHistory.setFactory(new IdHistoryParameter());
    	m_renderAtIdHistory.setFactory(new IdHistoryParameter());
      	m_undoIdHistory.setState(new IdHistoryParameter());
    	m_renderAtIdHistory.setState(new IdHistoryParameter());
    }

    public Id getId(){
    	return m_id;
    }
    
    public void setId(Id id) {
    	m_id=id;
    }

    public UndoHistory<Id> getUndoIdHistory(){
    	 return m_undoIdHistory;
    }
    public UndoHistory<Id> getRenderAtIdHistory(){
    	return m_renderAtIdHistory; 
    }
    public void setUndoId(UndoHistory<Id> undoIdHistory){
    	  m_undoIdHistory = undoIdHistory; 
    }
    public void setRenderAtId(UndoHistory<Id> renderAtIdHistory){
    	 m_renderAtIdHistory = renderAtIdHistory;
    }
 
    public abstract void compute();
    
    public void updateId(int groupDeepnessIndex, int newValue) {
    	 m_id.setControlOrLayer(groupDeepnessIndex, newValue);
    }
    
    public void UpdateRender(){
    	 IdHistoryParameter p= new IdHistoryParameter();
    	 p.set(m_id);
    	 m_renderAtIdHistory.setLast(p);
    }
    public void UpdateUndo(){
    	 IdHistoryParameter p= new IdHistoryParameter();
    	 p.set(m_id);
    	 m_undoIdHistory.setLast(p);
    }
    public void storeIdHistory(){
    	m_undoIdHistory.store();
        m_renderAtIdHistory.store();	
    }
    public abstract Control clone();
    
    public Boolean getBypassStatus () {
    	return bypass;
    }
    public void setBypass(Boolean p) {
    	bypass=p;
    }


    protected Id m_id;
    protected UndoHistory<Id> m_undoIdHistory;
    protected UndoHistory<Id> m_renderAtIdHistory;
    protected Boolean bypass;
   
};