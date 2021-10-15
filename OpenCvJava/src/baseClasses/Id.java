package baseClasses;


public class Id
{
	protected int[] m_id;
	protected int m_group;
	
	public Id() {
		m_id=new int[2];
		initNULL();
		m_group=0;
	}
	
	public Id(int[] index, int group) {
		m_id=index;
		m_group=group;
	}

	public int[] get(){
		return m_id;
	}
	
	public Id clone() {
		Id temp= new Id();
		temp.get()[0]=m_id[0];
		temp.get()[1]=m_id[1];
		temp.setGroupId(m_group);
		return temp;
	}
	
	public void set(Id id){
		m_id[0]=id.get()[0];
		m_id[1]=id.get()[1];
		m_group= id.getGroupId();
	}
	
	public void set(int[] id){
		m_id=id;
	}
	
	public void set(int layerIndex, int controlIndex, int group){
		m_id[0]=layerIndex;
		m_id[1]=controlIndex;
		m_group = group;
	}
	
	public void setControlOrLayer(int groupDeepnessIndex, int newValue){
		m_id[groupDeepnessIndex]=newValue;
	}
	
	public void setControlId(int controlIndex){
		m_id[1]=controlIndex;
	}
	
	public void setLayerId(int layerIndex){
		m_id[0]=layerIndex;
	}
	
	public int  getGroupId(){
		return m_group;
	}
	
	public void setGroupId(int group){
		m_group=group;
	}
	
	public void initNULL(){
		m_id[0]=-1;
		m_id[1]=-1;
	}
};
