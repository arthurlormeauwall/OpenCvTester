package com.opencvtester.baseClasses;

import java.util.HashMap;

public class Id
{
	protected HashMap<String, Integer> id;
	
	public Id() {
		id=new HashMap<String, Integer>();
		initNULL();
	
	}
	public Id(HashMap<String, Integer> ids) {
		id=new HashMap<String, Integer>();
		set(ids);
	}
	
	public Id(int layerIndex, int filterIndex) {
		id=new HashMap<String, Integer>();
		id.put("layer", layerIndex);
		id.put("filter", filterIndex);
	}
	public void initNULL(){
		id.put("layer", -1);
		id.put("filter", -1);
	}

	public HashMap<String, Integer> get(){
		return id;
	}
	
	public Integer get(String key){
		return id.get(key);
	}
	
	public int layerIndex(){
		return id.get("layer");
	}

	public int filterIndex(){
		return id.get("filter");
	}

	public void set(Id id){
		this.id.put("layer", id.layerIndex());
		this.id.put("filter", id.filterIndex());
	}
	
	@SuppressWarnings("unchecked")
	public void set(HashMap<String, Integer> id){
		this.id=(HashMap<String, Integer>) id.clone();
	}
	
	public void set(int layerIndex, int filterIndex){
		id.put("layer", layerIndex);
		id.put("filter", filterIndex);
	}
	
	public void setFilterOrLayer(String indexType, int newValue){
		id.put(indexType, newValue);
	}

	public Id clone() {
		Id temp= new Id();
		temp.setLayerId(id.get("layer"));
		temp.setFilterId(id.get("filter"));
	
		return temp;
	}
	
	public void setFilterId(int filterIndex){
		id.put("filter", filterIndex);
	}
	
	public void setLayerId(int layerIndex){
		id.put("layer", layerIndex);
	}
}
