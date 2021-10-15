package baseClasses.history;

public interface HistoryParameter<T> 
{
	public void set (T p);
	public HistoryParameter<T> getNew();
	public void invert();
	public T getParameter();	
}
