package baseClasses;

public abstract class Command {

    public abstract Boolean undo();
    public abstract Boolean redo();
    public abstract void store();

};