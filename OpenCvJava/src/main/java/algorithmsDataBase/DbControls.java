package algorithmsDataBase;

import java.util.Stack;

import baseClasses.Control;
import baseClasses.adjustControl.AdjustControlFloat;

public class DbControls 
{
	protected Stack<AdjustControlFloat> controls;
	protected AlphaControl alpha;
	protected EmptyControl emptyControl;

	public DbControls() {
		controls= new Stack<AdjustControlFloat>();
		alpha = new AlphaControl();
		emptyControl= new EmptyControl();
	}
	
	public Control getControl(int index){
		if (controls.size()-1<index) {
			return emptyControl;
		}
		else {
			return controls.get(index).createNew();	
		}	
	}
	
	public void addAlgorithm(AdjustControlFloat control) {
		controls.push(control);
	}
	
	public AlphaControl getAlphaControl(){
		return alpha;
	}
	
	public ControlFlags<Stack<Float>> getFlags(int index){
		if (controls.size()-1<index) {
			return emptyControl.getFlags();
		}
		else {
			return controls.get(index).getFlags();
		}
	}
}