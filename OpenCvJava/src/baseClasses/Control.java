package baseClasses;

import baseClasses.history.historyParameters.IdHistoryParameter;
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

	    m_isBypass = false;
	    
	    initControl();
    }
    
    public Control(Id id, UndoHistory<Id> undoIdHistory, UndoHistory<Id> renderAtIdHistory) {
	    m_id = new Id();
	    m_id.set(id);
	    
	    m_undoIdHistory = new UndoHistory<Id>();
		m_renderAtIdHistory= new UndoHistory<Id>(); 

	    m_undoIdHistory = undoIdHistory;
	    m_renderAtIdHistory = renderAtIdHistory;
	    m_isBypass = false;
	    
	    initControl();
    }
    
    public void initControl() { 	
    	m_undoIdHistory.initFactory(new IdHistoryParameter());
    	m_renderAtIdHistory.initFactory(new IdHistoryParameter());
      	m_undoIdHistory.initState(new IdHistoryParameter());
    	m_renderAtIdHistory.initState(new IdHistoryParameter());
    }
    
    public abstract void compute();

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
 
    public void updateId(int groupDeepnessIndex, int newValue) {
    	m_id.setControlOrLayer(groupDeepnessIndex, newValue);
    }
    
    public void UpdateRender(){
		IdHistoryParameter tempParameter= new IdHistoryParameter();
		tempParameter.set(m_id);
		m_renderAtIdHistory.setState(tempParameter);
    }
    public void UpdateUndo(){
		IdHistoryParameter temparameter= new IdHistoryParameter();
		temparameter.set(m_id);
		m_undoIdHistory.setState(temparameter);
    }
    public void storeIdHistory(){
		m_undoIdHistory.store();
	    m_renderAtIdHistory.store();	
    }
    public abstract Control clone();
    
    public Boolean getBypassState () {
    	return m_isBypass;
    }
    public void setBypass(Boolean bypassState) {
    	m_isBypass=bypassState;
    }

    protected Id m_id;
    protected UndoHistory<Id> m_undoIdHistory;
    protected UndoHistory<Id> m_renderAtIdHistory;
    protected Boolean m_isBypass;
   
};