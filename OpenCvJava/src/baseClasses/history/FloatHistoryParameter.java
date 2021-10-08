package baseClasses.history;


import java.util.Stack;




public class FloatHistoryParameter implements HistoryParameter<Stack<Float>>
{

    public FloatHistoryParameter() {
   
    }
    public FloatHistoryParameter(Stack<Float> p) {
		 m_p=p;
	 }
	 
 
    
	public FloatHistoryParameter getNew() {
		return new FloatHistoryParameter();
	}
	public void invert() {

      
	}
	public Stack<Float> getParameter() {
		return m_p;
	}

	public void set(Stack<Float> p) {
		m_p=p;		
	}
    
	protected Stack<Float> m_p;
};