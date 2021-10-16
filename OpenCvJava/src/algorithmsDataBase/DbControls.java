package algorithmsDataBase;

import java.util.Stack;

import baseClasses.Control;
import baseClasses.Id;
import baseClasses.adjustControl.AdjustControlFloat;

public class DbControls 
{
	protected Stack<AdjustControlFloat> controls;
	protected GrayScaleControl grayScale;
	protected AlphaControl alpha;
	protected MultBgrControl rgbMult;
	// ADD CONTROL HERE

	public DbControls() {
		controls= new Stack<AdjustControlFloat>();
		Id id = new Id();
		id.initNULL();

		alpha = new AlphaControl(id);
		rgbMult = new MultBgrControl(id);
		grayScale = new GrayScaleControl(id);
		// ADD CONTROL HERE 

		controls.push(grayScale);
		controls.push(rgbMult);
		//PUSH CONTROL HERE
	}
	
	public AlphaControl getAlphaControl(){
		return alpha;
	}
	public Control getControl(int index){
		return controls.get(index);
	}
	public ControlFlags<Stack<Float>> getFlags(int index){
		return controls.get(index).getFlags();
	}	
};