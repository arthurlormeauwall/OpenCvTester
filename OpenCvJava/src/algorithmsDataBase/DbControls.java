package algorithmsDataBase;

import java.util.Stack;

import baseClasses.Control;
import baseClasses.adjustControl.AdjustControlFloat;

public class DbControls 
{
	protected Stack<AdjustControlFloat> controls;
	protected GrayScaleControl grayScale;
	protected AlphaControl alpha;
	protected EmptyControl emptyControl;

	public DbControls() {
		controls= new Stack<AdjustControlFloat>();

		alpha = new AlphaControl();
		grayScale = new GrayScaleControl();
		emptyControl= new EmptyControl();

		controls.push(grayScale);
	}
	
	public AlphaControl getAlphaControl(){
		return alpha;
	}
	public Control getControl(int index){
		if (controls.size()-1<index) {
			return emptyControl;
		}
		else {
			return controls.get(index);	
		}	
	}
	public ControlFlags<Stack<Float>> getFlags(int index){
		if (controls.size()-1<index) {
			return emptyControl.getFlags();
		}
		else {
			return controls.get(index).getFlags();
		}
	}
	public void addAlgorithm(AdjustControlFloat control) {
		controls.push(control);
	}
}