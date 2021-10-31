package baseClasses;

public abstract class Undoable 
{
    public abstract Boolean undo();
    public abstract Boolean redo();
    public abstract void store();
}