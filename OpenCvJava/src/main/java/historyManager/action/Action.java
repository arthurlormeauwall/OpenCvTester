package historyManager.action;

public interface Action 
{
	public abstract void execute();
	public abstract void invert();
	public abstract Action clone();
}
