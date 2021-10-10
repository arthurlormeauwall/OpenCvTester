package algo.dataBase;

import java.util.Stack;

import algo.AdjustControlFloat;
import baseClasses.Control;
import baseClasses.Id;
import baseClasses.enums_structs.ControlFlags;

public class DbControls 
{

	public DbControls() {
		m_controls= new Stack<AdjustControlFloat>();
		Id id = new Id();
		id.initNULL();

		c_alpha = new AlphaControl(id);
		c_rgbMult = new MultBgrControl(id);
		c_grayScale = new GrayScaleControl(id);
		// ADD CONTROL HERE 

		m_controls.push(c_grayScale);
		m_controls.push(c_rgbMult);
		//PUSH CONTROL HERE
	}
	
	public AlphaControl getAlphaControl(){
		return c_alpha;
	}
	public Control getControl(int index){
		return m_controls.get(index);
	}
	public ControlFlags<Stack<Float>> getFlags(int index){
		return m_controls.get(index).getFlags();
	}



	protected Stack<AdjustControlFloat> m_controls;
	protected GrayScaleControl c_grayScale;
	protected AlphaControl c_alpha;
	protected MultBgrControl c_rgbMult;
	// ADD CONTROL HERE
};