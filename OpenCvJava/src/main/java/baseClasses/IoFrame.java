package baseClasses;

import baseClasses.frame.Frame;


public interface IoFrame 
{
    public void setSource(Frame s);
    public void setDest(Frame d) ;
    public Frame getSource();
    public Frame getDest();
}