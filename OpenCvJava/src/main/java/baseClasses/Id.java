package baseClasses;


public class Id
{
	protected int[] id;
	protected int group;
	
	public Id() {
		id=new int[2];
		initNULL();
		group=0;
	}
	public Id(int[] ids) {
		id=new int[2];
		set(ids);
		group=0;
	}
	
	public Id(int[] indexs, int group) {
		id=indexs;
		this.group=group;
	}
	
	public void initNULL(){
		id[0]=-1;
		id[1]=-1;
	}

	public int[] get(){
		return id;
	}
	
	public void set(Id id){
		this.id[0]=id.get()[0];
		this.id[1]=id.get()[1];
		group= id.getGroupId();
	}
	
	public void set(int[] id){
		this.id=id;
	}
	
	public void set(int layerIndex, int controlIndex, int group){
		id[0]=layerIndex;
		id[1]=controlIndex;
		this.group = group;
	}
	
	public void setControlOrLayer(int groupDeepnessIndex, int newValue){
		id[groupDeepnessIndex]=newValue;
	}

	public Id clone() {
		Id temp= new Id();
		temp.get()[0]=id[0];
		temp.get()[1]=id[1];
		temp.setGroupId(group);
		return temp;
	}
	
	public void setControlId(int controlIndex){
		id[1]=controlIndex;
	}
	
	public void setLayerId(int layerIndex){
		id[0]=layerIndex;
	}
	
	public int  getGroupId(){
		return group;
	}
	
	public void setGroupId(int group){
		this.group=group;
	}
}