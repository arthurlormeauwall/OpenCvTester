package com.opencvtester.baseClasses;


public class Id
{
	protected int[] id;
	
	public Id() {
		id=new int[2];
		initNULL();
	
	}
	public Id(int[] ids) {
		id=new int[2];
		set(ids);
	}
	
	public Id(int layerIndex, int filterIndex) {
		id=new int[2];
		id[0]=layerIndex;
		id[1]=filterIndex;
	}
	public void initNULL(){
		id[0]=-1;
		id[1]=-1;
	}

	public int[] get(){
		return id;
	}
	
	public int layerIndex(){
		return id[0];
	}

	public int filterIndex(){
		return id[1];
	}

	public void set(Id id){
		this.id[0]=id.get()[0];
		this.id[1]=id.get()[1];
	}
	
	public void set(int[] id){
		this.id=id;
	}
	
	public void set(int layerIndex, int controlIndex){
		id[0]=layerIndex;
		id[1]=controlIndex;
	
	}
	
	public void setControlOrLayer(int indexType, int newValue){
		id[indexType]=newValue;
	}

	public Id clone() {
		Id temp= new Id();
		temp.get()[0]=id[0];
		temp.get()[1]=id[1];
	
		return temp;
	}
	
	public void setFilterId(int controlIndex){
		id[1]=controlIndex;
	}
	
	public void setLayerId(int layerIndex){
		id[0]=layerIndex;
	}
	
	
}
