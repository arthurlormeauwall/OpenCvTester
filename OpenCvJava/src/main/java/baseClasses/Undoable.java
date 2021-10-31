package baseClasses;

public interface Undoable 
{
    public abstract Boolean undo();
    public abstract Boolean redo();
    public abstract void store();
}