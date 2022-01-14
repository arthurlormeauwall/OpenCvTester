package com.opencvtester.baseClasses;

import java.util.HashMap;

public class Id
{
	protected HashMap<String, Integer> id;
	
	/*
	 * CONSTRUCTOR & INITS
	 */
	public Id() {
		id=new HashMap<String, Integer>();
		initNULL();
	}
	
	@SuppressWarnings("unchecked")
	public Id(HashMap<String, Integer> ids) {
		id=new HashMap<String, Integer>();
		this.id=(HashMap<String, Integer>) ids.clone();
	}
	
	public Id(int layerIndex, int filterIndex) {
		id=new HashMap<String, Integer>();
		id.put("layer", layerIndex);
		id.put("filter", filterIndex);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
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
	
	public void set(int layerIndex, int filterIndex){
		id.put("layer", layerIndex);
		id.put("filter", filterIndex);
	}
	
	public void set(Command command){
		id.put("layer", command.layerIndex());
		id.put("filter", command.filterIndex());
	}
	
	public void setFilterOrLayer(String indexType, int newValue){
		id.put(indexType, newValue);
	}
	
	public void setFilterIndex(int filterIndex){
		id.put("filter", filterIndex);
	}
	
	public void setLayerIndex(int layerIndex){
		id.put("layer", layerIndex);
	}
		
	/*
	 * FEATURES
	 */
	public void initNULL(){
		id.put("layer", -1);
		id.put("filter", -1);
	}

	public Id clone() {
		Id temp= new Id();
		temp.setLayerIndex(id.get("layer"));
		temp.setFilterIndex(id.get("filter"));
	
		return temp;
	}
}
