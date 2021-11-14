package baseClasses;

import baseClasses.frame.FrameCv;


public interface IoFrame 
{
    public void setSource(FrameCv s);
    public void setDest(FrameCv d) ;
    public FrameCv getSource();
    public FrameCv getDest();
}