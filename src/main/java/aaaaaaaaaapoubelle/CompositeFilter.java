package aaaaaaaaaapoubelle;
//package com.opencvtester.data;
//
//import java.util.Stack;
//
//import com.opencvtester.entity.interfaces.FrameInterface;
//import com.opencvtester.renderer.IOFrame;
//
//public abstract class CompositeFilter
//{
//	protected ChainOfCommands chainOfFilters;
//	protected String indexType;
//	
//	/*
//	 * CONSTRUCTOR & INITS
//	 */
//	public CompositeFilter() {
//		super();
//	}
//	
//	/*
//	 * GETTERS & SETTERS
//	 */
//	public ChainOfCommands getChain() {
//		return chainOfFilters;
//	}
//	
//	public void setChain(ChainOfCommands chain) {
//		chainOfFilters=chain;
//	}	
//
//	public String indexType() {
//		return indexType;
//	}
//	
//	protected void add(IOFrame filter) {	
//		chainOfFilters.add(filter);	
//	}
//	
//	public void delete(IOFrame filter) {
//		chainOfFilters.delete(filter.getIndex(indexType));	
//	}
//	
//	public Boolean isIndexOutOfRange(int index) {
//		if(chainOfFilters.getSize()>= index) {
//			return false;
//		}
//		else {
//			return true;
//		}
//	}
//
//	public IOFrame getFirstFilter() {
//		if (chainOfFilters.getSize()==0) {
//			return this;
//		}
//		else{
//			return (IOFrame)chainOfFilters.getCommand(0);
//		}
//	}
//}
